package TR0807_String_Date_比较.zuoye4;

import java.util.Comparator;

public class T2 {
    Comparator com = new Comparator(){
        public int compare(Object obj1,Object obj2){
            if(obj1 instanceof Person && obj2 instanceof Person){
                Person p1 = (Person) obj1;
                Person p2 = (Person) obj2;
                return Integer.valueOf(p1.getAge()).compareTo(p2.getAge());
            }
            throw new RuntimeException("类型错误");
        }
    };
}

class Person implements Comparable{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int compareTo(Object obj){
        if(obj instanceof  Person){
            Person p = (Person)obj;
            return this.name.compareTo(p.name);
        }
        throw new RuntimeException("类型错误");
    }
}

