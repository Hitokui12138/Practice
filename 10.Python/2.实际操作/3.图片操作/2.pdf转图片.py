# pdf2image 依赖于 Poppler 来处理 PDF 文件
# 从pdf2image模组中导入这个方法
from pdf2image import convert_from_path

pdf_path = r'/Users/peihanggu/Documents/漫画/漫画/禁断岛/禁断島.pdf'
output_directory = "/Users/peihanggu/Documents/漫画/漫画/禁断岛/禁断岛"
# 返回一个图片列表
images = convert_from_path(pdf_path)
# 遍历和保存图片
for i, image in enumerate(images):
    if i < 9: #不包括9
        image_path = f"{output_directory}page_0{i+1}.png"
    else:
        image_path = f"{output_directory}page_{i+1}.png"
    
    image.save(image_path, 'PNG')
    print(f"正在生成第{i+1}页,路径为{image_path}")