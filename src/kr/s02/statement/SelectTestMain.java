package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SelectTestMain {
	public static void main(String[] args) {
		
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//1 드라이버 로드
			Class.forName(db_driver);
			//2 Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			//SQL문 작성
			sql = "SELECT * FROM test1";
			//3 Statement 객체 생성
			stmt = conn.createStatement();
			//4 - sql문을 실행해서 테이블로부터 레코드(행)을 전달받아서 결과집합을 만들고 ResultSet 객체에 담기, *커서 역할*
			rs = stmt.executeQuery(sql);
			
			System.out.println("ID\t나이");
			
			while(rs.next()) { //next() *커서를 다음 행으로 이동시킴* ->행이 존재하면 true, 없으면 false
				//컬럼명을 통해서 데이터를 반환
//				System.out.print(rs.getString("id"));
//				System.out.print("\t");
//				System.out.println(rs.getInt("age"));
				
				//컬럼 인덱스를 통해서 데이터를 반환(컬럼 인덱스가 작은 경우 사용)
				System.out.print(rs.getString(1)); //1번째 매핑되어있는 데이터를 string타입으로 반환
				System.out.print("\t");
				System.out.println(rs.getInt(2)); //2번째 매핑되어있는 데이터를 int 타입으로 반환(getString으로 사용 가능-모를 떄)
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(stmt!=null)try {stmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		
	}
}
