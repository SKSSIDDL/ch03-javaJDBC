package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteTestMain {
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
			conn= DriverManager.getConnection(db_url,db_id,db_password);
			//SQL
			sql = "DELETE FROM test1 WHERE id='test'"; //sky란 id를 가진 모든 행이 삭제 , 없는 정보를 입력해도 0으로 출력
			//3
			stmt = conn.createStatement();
			//4 - SQL문을 실행해서 테이블의 행을 삭제, 행을 삭제한 후 삭제한 행의 개수 반환
			int count = stmt.executeUpdate(sql);
			System.out.println(count+"개의 행을 삭제했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt!=null)try {stmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
}
