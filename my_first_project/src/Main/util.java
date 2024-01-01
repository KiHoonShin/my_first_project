package Main;

import java.util.Scanner;

public class util {
	private static Scanner sc = new Scanner(System.in);
	
	public static int getValue(int start, int end) {
		while(true) {
			System.out.printf("[%d-%d] 선택 >> " , start , end);
			try {
			int sel = sc.nextInt();
			sc.nextLine();
			if(sel < start || sel > end) {
				System.err.printf("[ %d ~ %d]의 값만 입력하세요 %n" , start , end);
				continue;
			}
			return sel;
			} catch (Exception e) {
				System.err.println("숫자만 입력하세요");
				sc.nextLine();
			}
		}
	}
	
	public static String getValue(String msg) {
		String input = sc.nextLine();
		return input;
	}
	
}
