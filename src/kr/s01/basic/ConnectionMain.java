package kr.s01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMain {
	public static void main(String[] args) {
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe"; //라이브러리-드라이버 종류-@ip주소-포트번호-오라클 식별자
		String db_id = "c##user001";
		String db_password = "1234";
		
		try {
			//JDBC 수행 1단계-드라이버 로드
			Class.forName(db_driver);
			
			//JDBC 수행 2단계-Connection 객체 생성
			Connection conn = DriverManager.getConnection(db_url, db_id, db_password);
			System.out.println("Connection 객체가 생성되었습니다.");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

//ddl, dml문장은 자바에서도 생성가능