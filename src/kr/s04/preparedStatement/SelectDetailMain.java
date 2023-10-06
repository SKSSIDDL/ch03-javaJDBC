package kr.s04.preparedStatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectDetailMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			br= new BufferedReader(new InputStreamReader(System.in));
			System.out.print("번호 : ");
			int num = Integer.parseInt(br.readLine());
			
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM test2 WHERE num = ?";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			
			//JDBC 수행 4단계: SQL문을 실행해서 결과행 하나를 ResultSET에 담음
			rs = pstmt.executeQuery(); //if문을 사용해 행이 있을 경우와 없을 경우를 모두 처리(행이 1개일 경우 if~else로)
			
			if(rs.next()) {
				System.out.println("번호 : " +rs.getInt("num"));
				System.out.println("제목 : " +rs.getString("title"));
				System.out.println("작성자 : " +rs.getString("name"));
				System.out.println("내용 : " +rs.getString("memo"));
				
				String email = rs.getString("email");
				if(email==null) email=""; //null일 경우 빈 문자열
				System.out.println("이메일 : " +email);
				
				System.out.println("작성일 : " +rs.getDate("reg_date"));
			}else {
				System.out.println("검색된 데이터가 없습니다.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
			if(br!=null)try {br.close();}catch(IOException e) {}
		}
	}
}
