package kr.s06.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.util.DBUtil;

public class TransactionMain {
	public static void main(String[] args) {
		//SQL문장이 여러 개일 경우
		Connection conn = null;
		PreparedStatement pstmt1 = null; //SQL문장을 하나밖에 실어나르지 못함, 문장 개수만큼 생성해야함
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			//1,2단계
			conn = DBUtil.getConnection();
			
			//pstmt가 잘못 작성된 경우 이클립스에서는 오류가 나지만 오라클에선 오류난 부분을 제외한 pstmt가 등록됨 
			//트랜잭션을 수동 처리하기 위해 auto commit 해제 - 모든 pstmt가 정상 실행될 경우에만 commit or rollaback
			conn.setAutoCommit(false);

			sql = "INSERT INTO test1 VALUES ('Seoul',10)";
			pstmt1 = conn.prepareStatement(sql);
			pstmt1.executeUpdate();
			
			sql = "INSERT INTO test1 VALUES ('Busan',20)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO test1 VALUES ('Jeju',30";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.executeUpdate();
			
			//정상적으로 작업이 완료되면 commit
			conn.commit();
			
			System.out.println("작업 완료!!");
			
		}catch(Exception e) {
			e.printStackTrace();
			//예외가 발생했을 경우 rollback - rollback도 exception이 발생할 수 있음->try~catch
			try {
				conn.rollback();				
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}finally {
			//자원정리, pstmt의 순서는 상관없음 마지막에만 conn
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt1, conn);
		}
		
	}
}

