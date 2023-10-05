package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTestMain {
	public static void main(String[] args) {
		
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {
			//1
			Class.forName(db_driver);
			//2
			conn = DriverManager.getConnection(db_url,db_id,db_password);
			//SQL문
			sql = "UPDATE test1 SET age=90 WHERE id='sky'"; //아이디 sky인 것 중 age를 90으로 변경, 없는 정보 => 0으로 출력(에러 x)
			//3
			stmt = conn.createStatement();
			//4-SQL문 실행해서 ㅎ나의 행의 정보를 수정함, 수정한 행의 개수를 반환
			int count = stmt.executeUpdate(sql);
			System.out.println(count +"개 행의 정보를 수정했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt!=null)try {stmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
}
