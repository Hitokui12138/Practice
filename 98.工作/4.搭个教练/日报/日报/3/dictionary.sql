-- 性别
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('GENDER', '1', '男', '男', 10, 1),
    ('GENDER', '2', '女', '女', 20, 1);

-- 学历认证类型
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('EDUCATION_CERT_SUB_TYPE', 'MAINLAND', '大陆', 'MAINLAND', 10, 1),
    ('EDUCATION_CERT_SUB_TYPE', 'OVERSEAS', '海外', 'OVERSEAS', 20, 1),
    ('EDUCATION_CERT_SUB_TYPE', 'STUDENT', '学生', 'STUDENT', 30, 1);

-- 学历层次
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('EDUCATION_LEVEL', '1', '大专', '1', 10, 1),
    ('EDUCATION_LEVEL', '2', '本科', '2', 20, 1),
    ('EDUCATION_LEVEL', '3', '硕士', '3', 30, 1),
    ('EDUCATION_LEVEL', '4', '博士', '4', 40, 1);

-- 荣誉等级
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('HONOR_LEVEL', 'INTERNATIONAL', '国际级', 'INTERNATIONAL', 10, 1),
    ('HONOR_LEVEL', 'NATIONAL', '国家级', 'NATIONAL', 20, 1),
    ('HONOR_LEVEL', 'PROVINCIAL', '省级', 'PROVINCIAL', 30, 1),
    ('HONOR_LEVEL', 'CITY', '市级', 'CITY', 40, 1),
    ('HONOR_LEVEL', 'DISTRICT', '区域级', 'DISTRICT', 50, 1),
    ('HONOR_LEVEL', 'COMMUNITY', '社区级', 'COMMUNITY', 60, 1);

-- 资质类型
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('QUALIFICATION_TYPE', 'COACH', '教练资格证', 'COACH', 10, 1),
    ('QUALIFICATION_TYPE', 'TEACHER', '教师资格证', 'TEACHER', 20, 1),
    ('QUALIFICATION_TYPE', 'ATHLETE', '运动员资格证', 'ATHLETE', 30, 1),
    ('QUALIFICATION_TYPE', 'OTHER', '其他资格证', 'OTHER', 40, 1);

-- 固定文本
INSERT INTO sys_dictionary (type_code, item_code, item_label, item_value, sort_order, is_active)
VALUES
    ('COURSE_PURCHASE_NOTICE', 'HOW_TO_BUY', '如何购买', '点击页面右下角红色按钮，一次性支付即购入全部课程内容，课程内容可长期学习。', 10, 1),
    ('COURSE_PURCHASE_NOTICE', 'HOW_TO_ATTEND', '如何上课', '购买此课程后，可在搭个教练 app 长期学习，您可在 app – 点击左上头像进入个人主页 – 上划至底部点击“已购买课程”继续完成内容学习；也可以点击 app 底部“学习”– 点击右上角“我的课程”找到已成功购买的课程。', 20, 1),
    ('COURSE_PURCHASE_NOTICE', 'REFUND_POLICY', '退课说明', '该课程为虚拟商品，购买后不支持退课转让。', 30, 1);
