package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestMain {
	public static void main(String[] args) {
		
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String db_id = "c##user001";
		String db_password = "1234";
		
		//변수 선언
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1단계 - 드라이버 로드
			Class.forName(db_driver);
			
			//JDBC 수행 2단계 - Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			
			//SQL문장(4단계 전에만 명시) -insert가 여러개일 경우 수작업 commit,rollback으로 명시해야 오류가 나지 않음
			sql = "INSERT INTO test1 (id,age) VALUES ('wave',10)";
			
			//JDBC 수행 3단계 - Statement 객체 생성
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계 - SQL문 실행해서 하나의 행을 삽입, 작업 후 삽입한 행의 개수 반환
			int count = stmt.executeUpdate(sql);
			System.out.println(count + "개의 행을 추가했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt!=null) try {stmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
	}
}