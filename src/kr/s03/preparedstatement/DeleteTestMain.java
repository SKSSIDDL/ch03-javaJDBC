package kr.s03.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class DeleteTestMain {
	public static void main(String[] args) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //1,2단계
			 conn = DBUtil.getConnection();
			 
			 //SQL문 작성
			 sql = "DELETE FROM test1 WHERE id=?";
			 
			 //3단계
			 pstmt = conn.prepareStatement(sql);
			 
			 //?에 데이터 바인딩
			 pstmt.setString(1, "star");
			 
			 //4단계
			 int count = pstmt.executeUpdate();
			 System.out.println(count +"개 행을 삭제했습니다."); //없으면 0으로 출력
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	}
}
