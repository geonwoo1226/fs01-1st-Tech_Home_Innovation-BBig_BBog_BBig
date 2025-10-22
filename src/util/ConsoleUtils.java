package util;

/**
 * 콘솔을 "지운 것처럼" 보이게 하는 소프트 클리어.
 * IDE 콘솔에서도 항상 동작합니다.
 */


	public final class ConsoleUtils {
	  

	    public static void clearConsole() {
	        // 50~80줄 정도의 빈 줄을 출력해서 이전 내용이 안 보이게 합니다.
	        for (int i = 0; i < 60; i++) {
	            System.out.println("");
	        }
	    }
	}


