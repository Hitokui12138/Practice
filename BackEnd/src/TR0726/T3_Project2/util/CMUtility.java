package TR0726.T3_Project2.util;

import java.util.Scanner;

/**
 * 封装一些工具类
 */
public class CMUtility {
    private static Scanner scanner = new Scanner(System.in);


    public static char readMenuSelection() {
        return readKeyBoard(1, false).charAt(0);
    }

    public static String readString(int limit){
        return readKeyBoard(limit,false);
    }

    public static char readConfirmSelection() {
        for(;;){
            char confirm = readKeyBoard(1, false).charAt(0);
            if (confirm == 'Y' || confirm == 'y') {
                return 'Y';
            } else if (confirm == 'N' || confirm == 'n') {
                return 'N';
            } else {
                System.out.println("请输入Y/N");
            }
        }
    }

    public static String readKeyBoard(int limit, boolean blankReturn) {
        String line = "";
        while (scanner.hasNextLine()) {
            //1.等待输入
            line = scanner.nextLine();
            //2.特殊情况：输入为空
            if (line.length() == 0) {
                if (blankReturn) {
                    return line;
                } else {
                    continue;
                }
            }
            //3.长度check
            if (line.length() < 1 || line.length() > limit) {
                System.out.println("Input Error(less than" + limit + "),Please input again");
                continue;
            }
            break;
        }
        return line;
    }
}
