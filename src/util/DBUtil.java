package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
	static {
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnect() {
		Connection con = null;
		
		String url = "jdbc:mysql://127.0.0.1:3306/smart_home_system?serverTimezone=UTC";
		String user = "sample";
		String password = "1234";
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//자원반납
	
	
	/*
	 * 제시된 코드의 문제점
	 * 
예외 처리 누락: close(Connection con) 메서드는 비어 있어 아무런 동작을 하지 않습니다. 
그래서 삭제함요


또한 close(ResultSet rs, Statement stmt, Connection con) 메서드는 try-catch 블록으로 예외를 처리하고 있지만, close() 메서드 자체에서 발생하는 예외를 무시하고 단순히 스택 추적만 출력합니다. 
따라서 자원 반납 과정에서 예외가 발생했을 때 적절한 후속 조치를 취하기 어렵습니다.

자원 반납 순서: rs, stmt, con의 반납 순서가 정해져 있지만, 
실제로는 ResultSet → Statement → Connection의 순서로 반납하는 것이 권장됩니다. 
만약 반납 순서가 바뀌면 예외가 발생할 수 있습니다. 예를 들어, Statement를 먼저 닫으면 해당 Statement에 연결된 ResultSet에 접근할 수 없게 됩니다.
자원 누수 가능성: close() 메서드 내에서 close()를 호출하다가 예외가 발생하면 이후의 close() 호출은 실행되지 않아 자원 누수가 발생할 수 있습니다.
긴 코드와 복잡성: 기존의 try-catch-finally 패턴은 코드가 길고 복잡해질 수 있습니다. 

개선된 자원 반납 방법: 'try-with-resources'
자바 7부터 도입된 'try-with-resources'는 위와 같은 문제를 해결하고 자원을 자동으로 닫아주는 문법입니다. 


장점
자동 자원 반납: try() 블록 안에 선언된 자원은 해당 블록을 벗어나는 즉시 자동으로 close() 메서드가 호출됩니다.
간결한 코드: finally 블록에서 일일이 null 체크와 close()를 호출할 필요가 없어 코드가 훨씬 간결해지고 가독성이 높아집니다.
안전한 예외 처리: try 블록 내에서 예외가 발생하더라도 close() 메서드는 호출이 보장됩니다. 만약 try 블록과 close() 메서드 모두에서 예외가 발생하면, try 블록의 예외가 우선시되며 close() 메서드의 예외는 '억제된 예외(Suppressed Exception)'로 처리되어 원래 예외에 대한 정보를 잃지 않습니다.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		
		try {
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			if(con!=null)con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}









