package TR0811_IO框架2.zhoukao.T2;

import java.io.*;
import java.util.ArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T2.java
 * @Description TODO
 * @createTime 2023年08月11日 14:17:00
 */
public class T2 {
    public static void main(String[] args) {
        File f1 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0811\\zhoukao\\T2", "chinese.csv");
        File f2 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0811\\zhoukao\\T2", "foreign.csv");
        //汇总文件
        File f3 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0811\\zhoukao\\T2", "Target.txt");
        ArrayList<Stuff> list = new ArrayList<>();
        list.addAll(Stuff.initStuffList(f1));
        list.addAll(Stuff.initStuffList(f2));
        System.out.println(list);
        Stuff.createNewFile(list, f3);
    }
}

class Stuff {
    String fullName;
    int age;
    int salary;

    //中国人外国人两种构造方法
    public Stuff(String firstName, String lastName, int age, int salary) {
        this.fullName = lastName + " " + firstName;
        this.age = age;
        this.salary = salary;
    }

    public Stuff(String fullName, int age, int salary) {
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return fullName + ',' + age + ',' + salary;
    }

    public static ArrayList<Stuff> initStuffList(File srcFile) {
        ArrayList<Stuff> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(srcFile));
            String content = null;
            int i = 1;
            while ((content = br.readLine()) != null) {
                //header不要
                if (i == 1) {
                    i++;
                    continue;
                } else {
                    //以逗号分割
                    String[] s = content.split(",");
                    if (s.length == 3) {//中国人
                        list.add(new Stuff(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                    } else if (s.length == 4) {//外国人
                        list.add(new Stuff(s[0], s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void createNewFile(ArrayList<Stuff> list, File descFile) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(descFile));
            //先写个header
            String content = "姓名,年龄,薪水";
            bw.write(content);
            bw.newLine();
            //遍历打印并计算汇总信息
            int StuffNum = 0;
            int SalaryNum = 0;
            for (Stuff s : list) {
                content = s.toString();
                bw.write(content);
                bw.newLine();
                StuffNum++;
                SalaryNum += s.salary;
            }
            //最后两行写汇总信息
            content = "----------------------------------";
            bw.write(content);
            bw.newLine();
            content = "共" + StuffNum + "个员工,工资总额是" + SalaryNum + "元。";
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
