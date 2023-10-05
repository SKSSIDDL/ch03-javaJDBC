package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DropTableMain {
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
			System.out.println("test1 테이블 제거를 시작합니다.");
			//SQL
			sql = "DROP TABLE test1";
			//3
			stmt = conn.createStatement();
			//4
			stmt.executeUpdate(sql);
			System.out.println("테이블이 정상적으로 삭제되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt!=null)try {stmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
}
