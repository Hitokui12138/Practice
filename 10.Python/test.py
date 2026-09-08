CONTENT_MAX_LEN = 5000  # 条款内容超过此字数则单独保存

XLSX_FILE = "条款内容0601.xlsx"
PREFIX = "中国太平洋财产保险股份有限公司"

PRODUCT_LINE_MAP = {
    "A": "企业财产保险",
    "B": "责任保险",
    "C": "货物运输保险",
    "D": "船舶保险",
    "E": "工程保险",
    "F": "特殊风险保险",
    "G": "保证保险",
    "H": "信用保险",
    "J": "家庭财产保险",
    "N": "涉车",
}

CATEGORY_LABEL = {
    "youze": "有责",
    "wuze": "无责",
    "orphan": "未关联",
}

REG_PATTERN = re.compile(r'[（(](?:产品)?注册号[：:]\s*([^）)]+)\s*[）)]')


def strip_prefix(name):
    if name and name.startswith(PREFIX):
        return name[len(PREFIX):]
    return name


def clean_content(content, clause_name=""):
    """清理正文：去除首行公司名、重复的条款标题行、注册号行、[NULL] 占位行。返回 (注册号, 清理后正文)。"""
    # 去除 [NULL] 行（数据库导出占位符）
    content = "\n".join(line for line in content.split("\n") if line.strip() != "[NULL]")

    # 去除首行的公司名称
    if content.startswith(PREFIX):
        idx = content.find('\n')
        content = content[idx + 1:] if idx != -1 else ""

    # 去除与条款名称重复的首行
    if clause_name and content.startswith(clause_name):
        idx = content.find('\n')
        content = content[idx + 1:] if idx != -1 else ""

    # 提取并去除注册号
    m = REG_PATTERN.search(content)
    if m:
        reg = m.group(1).strip()
        # 去除注册号片段，保留后续正文
        content = content[:m.start()] + content[m.end():]
        content = content.strip()
        return reg, content
    return "", content


def _row_quality(register_code, content):
    """评估数据行质量：注册号非空 + 正文非空 的计数，用于去重时择优。"""
    return (1 if register_code else 0) + (1 if content else 0)


def build_content_map():
    """读取 条款内容 sheet，返回 {clause_code: dict}。重复代码保留数据更全的行。"""
    print("读取条款内容...")
    wb = openpyxl.load_workbook(XLSX_FILE, read_only=True)
    ws = wb["条款内容"]
    content_map = {}
    reg_extracted = 0
    dup_kept = 0
    dup_discarded = 0
    skipped_main_clause = 0
    for row in ws.iter_rows(min_row=2, values_only=True):
        code = row[2]  # C: CLAUSE_CODE
        if not code or str(code).strip() == '[NULL]':
            continue
        # 跳过主条款
        if row[6] == "主条款":
            skipped_main_clause += 1
            continue
        # 拼接 CONTENT1 ~ CONTENT10 (列索引 11~20, 即 L~U)
        parts = []
        for i in range(11, 21):
            val = row[i]
            if val:
                v = str(val).strip()
                if v and v != "[NULL]":
                    parts.append(v)
        content = "\n".join(parts)

        register_code = (row[4] or "").strip()  # E: REGISTER_CODE
        if register_code == "[NULL]":
            register_code = ""
        clause_name = strip_prefix(row[3]) or ""  # D: CLAUSE_NAME
        if clause_name == "[NULL]":
            clause_name = ""

        # 清理正文（去公司名、去重复标题、去 [NULL] 行、提取/去注册号）
        if not register_code:
            extracted, content = clean_content(content, clause_name)
            if extracted:
                register_code = extracted
                reg_extracted += 1
        else:
            _, content = clean_content(content, clause_name)

        new_entry = {
            "name": strip_prefix(row[3]),  # D: CLAUSE_NAME
            "register_code": register_code,
            "product_line": row[1] or "",   # B: PRODUCT_LINE
            "content": content,
        }

        if code in content_map:
            old = content_map[code]
            old_q = _row_quality(old["register_code"], old["content"])
            new_q = _row_quality(register_code, content)
            if new_q > old_q:
                content_map[code] = new_entry
                dup_kept += 1
            else:
                dup_discarded += 1
        else:
            content_map[code] = new_entry

    wb.close()
    print(f"  已加载 {len(content_map)} 条条款（跳过 {skipped_main_clause} 条主条款）")
    print(f"  从正文提取注册号: {reg_extracted} 条")
    if dup_kept or dup_discarded:
        print(f"  重复代码处理: 保留较优行 {dup_kept} 条, 丢弃较差行 {dup_discarded} 条")
    return content_map


def parse_relationships(sheet_name, clause_code_col, clause_name_col, filter_col=None, filter_exclude=None):
    """
    读取关系表，返回 {product_line: [(product_name, clause_code, clause_name), ...]}
    自动去重。filter_exclude: 排除 filter_col 列等于该值的行。
    """
    print(f"读取{sheet_name}...")
    wb = openpyxl.load_workbook(XLSX_FILE, read_only=True)
    ws = wb[sheet_name]
    result = defaultdict(list)
    seen = set()
    skipped = 0
    for row in ws.iter_rows(min_row=2, values_only=True):
        product_line = row[2]        # C: PRODUCT_LINE
        product_name = strip_prefix(row[1])  # B: PRODUCT_NAME
        clause_code = row[clause_code_col]
        clause_name = row[clause_name_col]
        # 过滤指定类型的行
        if filter_col is not None and filter_exclude is not None:
            if row[filter_col] == filter_exclude:
                skipped += 1
                continue
        if not product_line or not clause_code:
            continue
        key = (product_line, product_name, clause_code)
        if key in seen:
            continue
        seen.add(key)
        result[product_line].append({
            "product_name": product_name,
            "clause_code": clause_code,
            "clause_name": strip_prefix(clause_name),
        })
    wb.close()
    total = sum(len(v) for v in result.values())
    print(f"  已加载 {total} 条关系（去重后）" + (f"，已跳过 {skipped} 条主条款" if skipped else ""))
    return dict(result)



def find_orphans(content_map, all_referenced_codes):
    """找出未被引用的条款，按产品线分组。"""
    orphans = defaultdict(list)
    for code, info in content_map.items():
        if code not in all_referenced_codes:
            pl = info["product_line"]
            orphans[pl].append({
                "clause_code": code,
                "clause_name": info["name"],
                "register_code": info["register_code"],
                "content": info["content"],
            })
    return dict(orphans)


def sanitize_filename(name):
    """将条款名称转为安全的文件名（不含扩展名）。"""
    return re.sub(r'[/\\:*?"<>|]', '', name).strip()


def save_long_clause(product_line, category_label, clause_name, register_code, code, product_names, content):
    """保存超长条款正文到独立文件（同险种目录下）。返回文件名供主文件引用。"""
    pl_name = PRODUCT_LINE_MAP.get(product_line, product_line)
    os.makedirs(pl_name, exist_ok=True)
    safe_name = sanitize_filename(clause_name)
    filepath = os.path.join(pl_name, f"{safe_name}.md")

    # 处理重名：追加序号
    base = filepath
    n = 1
    while os.path.exists(filepath):
        filepath = f"{base[:-3]}_{n}.md"
        n += 1

    with open(filepath, "w", encoding="utf-8") as f:
        if category_label == "未关联":
            f.write(f"# **条款名称:** {clause_name}\n")
            f.write(f"**条款注册号:** {register_code}\n")
            f.write(f"**条款代码:** {code}\n")
            f.write(f"**险种大类**: {pl_name}\n")
        else:
            f.write(f"# **附加条款名称:** {clause_name}\n")
            f.write(f"**附加条款注册号:** {register_code}\n")
            f.write(f"**附加条款代码:** {code}\n")
            f.write(f"**主险种**: \n{product_names}\n")
            f.write(f"**险种大类**: {pl_name}（{category_label}）\n")
        f.write(f"\n**条款内容:**\n{content}\n")
        f.write(f"\n@@@\n")

    print(f"    -> 长条款已单独保存: {filepath} ({len(content)} 字)")
    return os.path.basename(filepath)


def write_md_file(product_line, category, entries, content_map=None):
    """写单个 md 文件到对应险种目录。"""
    pl_name = PRODUCT_LINE_MAP.get(product_line, product_line)
    label = CATEGORY_LABEL[category]
    filename = f"{pl_name}-{label}.md"
    os.makedirs(pl_name, exist_ok=True)
    filepath = os.path.join(pl_name, filename)

    with open(filepath, "w", encoding="utf-8") as f:
        f.write(f"# 险种大类: {pl_name}（{label}）\n\n")

        if category == "orphan":
            written = 0
            for entry in entries:
                name = entry["clause_name"]
                reg = entry["register_code"]
                code = entry["clause_code"]
                content = entry["content"]
                if len(content) > CONTENT_MAX_LEN:
                    save_long_clause(product_line, label, name, reg, code, "", content)
                    continue
                f.write(f"### **条款名称:** {name}\n")
                f.write(f"**条款注册号:** {reg}\n")
                f.write(f"**条款代码:** {code}\n")
                f.write(f"**险种大类**: {pl_name}\n")
                f.write(f"\n**条款内容:**\n{content}\n")
                f.write(f"\n@@@\n\n")
                written += 1
        else:
            # 按 clause_code 分组，合并同一附加险对应的多个主险名称
            grouped = {}
            for entry in entries:
                code = entry["clause_code"]
                if code not in grouped:
                    grouped[code] = {
                        "product_names": [],
                        "clause_name": entry["clause_name"],
                    }
                grouped[code]["product_names"].append(entry["product_name"])

            written = 0
            for code, group in grouped.items():
                product_names = "通用" if len(group["product_names"]) > 100 else "|".join(group["product_names"])
                clause_name = group["clause_name"]
                info = content_map.get(code) if content_map else None
                if info is None:
                    register_code = ""
                    content = ""
                else:
                    register_code = info.get("register_code", "")
                    content = info.get("content", "")
                if not content:
                    print(f"  [警告] 条款 {code} ({clause_name}) 无正文内容")

                if len(content) > CONTENT_MAX_LEN:
                    save_long_clause(product_line, label, clause_name, register_code, code, product_names, content)
                    continue

                f.write(f"### **附加条款名称:** {clause_name}\n")
                f.write(f"**附加条款注册号:** {register_code}\n")
                f.write(f"**附加条款代码:** {code}\n")
                f.write(f"**主险种**: \n{product_names}\n")
                f.write(f"**险种大类**: {pl_name}（{label}）\n")
                f.write(f"\n**条款内容:**\n{content}\n")
                f.write(f"\n@@@\n\n")
                written += 1

    print(f"  已生成: {filepath} ({written} 条，{len(entries) - written} 条超长已单独保存)")


def main():
    # 1. 构建内容字典
    content_map = build_content_map()

    # 2. 读取关系表（有责表排除 CLAUSE_TYPE_NAME="主条款" 的行）
    youze = parse_relationships("有责主附险", clause_code_col=8, clause_name_col=9,
                                filter_col=7, filter_exclude="主条款")
    wuze = parse_relationships("无责主附险", clause_code_col=6, clause_name_col=7)

    # 3. 收集所有被引用的条款代码
    referenced = set()
    for entries in youze.values():
        for e in entries:
            referenced.add(e["clause_code"])
    for entries in wuze.values():
        for e in entries:
            referenced.add(e["clause_code"])

    # 4. 孤儿条款
    orphans = find_orphans(content_map, referenced)

    # 5. 写出文件
    print("\n生成 markdown 文件...")
    all_product_lines = set()
    for pl in youze:
        all_product_lines.add(pl)
    for pl in wuze:
        all_product_lines.add(pl)
    for pl in orphans:
        all_product_lines.add(pl)

    file_count = 0
    for pl in sorted(all_product_lines):
        if pl in youze and youze[pl]:
            write_md_file(pl, "youze", youze[pl], content_map)
            file_count += 1
        if pl in wuze and wuze[pl]:
            write_md_file(pl, "wuze", wuze[pl], content_map)
            file_count += 1
        if pl in orphans and orphans[pl]:
            write_md_file(pl, "orphan", orphans[pl])
            file_count += 1

    print(f"\n完成! 共生成 {file_count} 个文件。")


if __name__ == "__main__":
    main()