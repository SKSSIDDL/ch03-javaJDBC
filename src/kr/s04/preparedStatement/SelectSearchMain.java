package kr.s04.preparedStatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectSearchMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("제목 검색어 : ");
			String keyword = br.readLine();
			
			//JDBC 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			
//1			sql = "SELECT * FROM test2 WHERE title LIKE '%' || ? || '%'";
/*2*/		sql = "SELECT * FROM test2 WHERE title LIKE ?";
			
			//JDBC 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
//1			pstmt.setString(1, keyword);
/*2*/		pstmt.setString(1, "%"+keyword+"%");
			
			//JDBC 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //while문을 사용할경우 rs.next()가 중복으로 작동되기 때문에 do~while문을 사용한다.
				System.out.println("번호\t제목\t작성자\t날짜");
				do{
					System.out.print(rs.getInt("num")+"\t");
					System.out.print(rs.getString("title")+"\t");
					System.out.print(rs.getString("name")+"\t");
					System.out.println(rs.getDate("reg_date"));
				}while(rs.next());
				
			}else {
				System.out.println("검색된 데이터가 없습니다.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
			if(br!=null)try {br.close();}catch(IOException e) {}
		}
	}
}
