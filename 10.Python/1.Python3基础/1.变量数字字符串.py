# 单行注释

'''
多行注释
三个单引号
'''

# 1. 数字变量(不带双引号)
num1 = 1
print(num1)
num1 = 3 #修改
print(num1)

num2 = 5
print(num1 + num2)
'''
数字的分类
Number
    int(integer)
    float
    complex 实数虚数
'''
num3 = 1.0 #浮点数
print(num1 == num3) #False
print(num1 + num3) #自动转形成浮点数

# 一般数学运算需要 import math
#整除
print(7 / 3) #2.3333333333333335
print(7 // 3) #2
print(int(7 / 3)) #2
print(round(2.5)) #2  五舍六入
print(pow(2.6,2)) #power 取幂




# 2. 字符串变量(带双引号)
string1 = "这是一个:\n\"字符串\""
print(string1)
# 运算符对不同类型的变量功能也不通
string2 = "\t第二个字符串"
print(string1 + string2)
#substring()
print(string1[0])
print(string2[-1])
print(string2[2:4])#也是左闭右开

# 字符串方法
print(len(string1))