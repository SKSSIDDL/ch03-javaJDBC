package kr.s03.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class InsertTestMain {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작석
			sql = "INSERT INTO test1 (id,age) VALUES (?,?)"; //? : 실제 데이터가 아닌 특수문자, 순서값이 있음을 인식
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터를 바인딩
			pstmt.setString(1, "dragon"); //1번(id) ?에 데이터를 전달
			pstmt.setInt(2, 90); //2번(age) ?에 데이터 전달
			
			//JDBC 수행 4단계 : SQL문을 실행해서 테이블에 행을 추가
			int count = pstmt.executeUpdate();
			System.out.println(count + "개 행을 추가했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}

//데이터를 직접 명시하지 않고 ?처리
