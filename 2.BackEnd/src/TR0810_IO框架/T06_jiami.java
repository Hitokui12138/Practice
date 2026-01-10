package TR0810_IO框架;

import java.io.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T06_jiami.java
 * @Description TODO
 * @createTime 2023年08月10日 16:17:00
 * <p>
 * 图片加密
 * 对字符进行异或处理
 * 解密:再异或
 * 一个数异或两次还等于原来的数
 */
public class T06_jiami {
    public static void main(String[] args) {
        //encryption(String srcFile, String descFile);做一遍是加密
        //encryption(String srcFile, String descFile);对加密文件做一遍是解密
    }

    public static void encryption(String srcFile, String descFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(srcFile));
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = bis.read(b)) != -1) {
                //加密
                for (int i = 0; i < len; i++) {
                    b[i] = (byte) (b[i] ^ 5);//做与运算就变成整数了
                }
                //这一步只是copy
                bos.write(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
