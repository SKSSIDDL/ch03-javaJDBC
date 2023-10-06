package kr.s04.preparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectTestMain {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql = null;
		
		try {
			//1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문(num DESC)
			sql = "SELECT * FROM test2 ORDER BY num DESC";
			
			//3단계
			pstmt = conn.prepareStatement(sql);
			
			//4단계
			rs = pstmt.executeQuery();
			System.out.println("번호\t제목\t작성자\t등록일");
			
			while(rs.next()) {
				System.out.print(rs.getInt("num")); //getString도 가능
				System.out.print("\t");
				System.out.print(rs.getString("title"));
				System.out.print("\t");
				System.out.print(rs.getString("name"));
				System.out.print("\t");
				System.out.println(rs.getDate("reg_date")); //getString도 가능(연,월,일,시,분,초)
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
}
