package TR0728;

import java.io.IOException;

public class T7_Singleton2 {
	public static void main(String[] args) throws IOException, InterruptedException{
		//相当于CMD
		Runtime r = Runtime.getRuntime();
		Process p = r.exec("notepad");
		Thread.sleep(3000);//线程暂停3秒
		p.destroy();//关闭进程
	}
}
