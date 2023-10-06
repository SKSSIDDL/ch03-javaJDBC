package kr.s04.preparedStatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class InsertTestMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("제목 : ");
			String title = br.readLine();
			System.out.print("이름 : ");
			String name = br.readLine();
			System.out.print("메모 : ");
			String memo = br.readLine();
			System.out.print("이메일 : ");
			String email = br.readLine();
			
			//JDBC 실행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성(num <- test2_seq.nextval, reg_date <- SYSDATE)
			sql = "INSERT INTO test2 (num,title,name,memo,email,reg_date) VALUES (test2_seq.nextval,?,?,?,?,SYSDATE)";
			//JDBC 실행 3단계 : PreparedStatement
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1,title);
			pstmt.setString(2,name);
			pstmt.setString(3,memo);
			pstmt.setString(4,email);
			
			//JDBC 실행 4단계
			int count = pstmt.executeUpdate();
			System.out.println(count+"개의 행이 입력되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
			if(br!=null)try {br.close();}catch(IOException e) {}
		}
		
	}
}
