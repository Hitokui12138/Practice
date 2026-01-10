class Person:
    #1.构造函数,注意这个构造函数是预定义好的,必须是这样的格式
    '''
    def __init__(self) -> None:
        pass
    '''
    def __init__(self,name,height,weight):
        self.name = name
        self.height = height
        self.weight = weight
        #删除缩进表示函数的结束
    
    #2.函数
    def say_name(self):
        print("我的名字叫: " + self.name)


#删除缩进表示class定义的结束

person1 = Person("Hitokui",182,70)
person2 = Person("Jason",192,115)

person1.say_name() #self表示person1,不需要明确写出来
person2.say_name()

print(person1.height)

