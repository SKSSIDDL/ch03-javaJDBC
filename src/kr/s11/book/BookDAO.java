package kr.s11.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class BookDAO {
	//관리자 도서 등록
	public void insertBook(String bk_name,String bk_category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO book (bk_num,bk_name,bk_category) "
					+ "VALUES (book_seq.nextval,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bk_name);
			pstmt.setString(2, bk_category);
			
			int count = pstmt.executeUpdate();
			System.out.println(count+"권의 도서가 등록되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//관리자 도서 목록
	public void selectBook() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT bk_num,bk_category,bk_name,re_status,bk_regdate FROM book "
					+ "left outer JOIN (SELECT * FROM reservation WHERE re_status = 1) "
					+ "USING(bk_num) ORDER BY bk_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("도서번호\t카테고리\t도서명\t대출여부\t등록일");
				do {
					System.out.print(rs.getInt("bk_num")+"\t");
					System.out.print(rs.getString("bk_category")+"\t");
					System.out.print(rs.getString("bk_name")+"\t");
//					System.out.print(rs.getInt("re_status")+"\t");
					if(rs.getInt("re_status")==0) {
						System.out.print("대출가능"+"\t");
					}else {
						System.out.print("대출중"+"\t");
					}
					System.out.println(rs.getDate("bk_regdate"));
				}while(rs.next());
			}else {
				System.out.println("등록된 도서 목록이 없습니다.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//관리자 회원 목록
	public void selectMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT me_id,me_name,me_phone,me_regdate "
					+ "FROM member ORDER BY me_regdate DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			System.out.println("==========================================");
			if(rs.next()) {
				System.out.println("회원ID\t이름\t전화번호\t\t가입일");
				System.out.println("==========================================");
				do {
					System.out.print(rs.getString("me_id")+"\t");
					System.out.print(rs.getString("me_name")+"\t");
					System.out.print(rs.getString("me_phone")+"\t");
					System.out.println(rs.getDate("me_regdate"));
				}while(rs.next());
			}else {
				System.out.println("등록된 회원 정보가 없습니다.");
			}
			System.out.println("==========================================");

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//관리자 대출 목록(대출 및 반납 - 모든 데이터 표시)
	public void selectReservation() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT re_num,re_status,me_id,bk_name,re_regdate,re_modifydate FROM reservation "
					+ "JOIN book USING(bk_num) ORDER BY re_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			System.out.println("============================================================");
			if(rs.next()) {
				System.out.println("도서번호\t대출여부\t대출자ID\t대출도서명\t대출일\t\t반납일");
				System.out.println("============================================================");
				do {
					System.out.print(rs.getInt("re_num")+"\t");
					if(rs.getInt("re_status")==0) {
						System.out.print("반납"+"\t");
					}else {
						System.out.print("대출"+"\t");
					}
//					System.out.print(rs.getInt("re_status")+"\t");
					System.out.print(rs.getString("me_id")+"\t");
					System.out.print(rs.getString("bk_name")+"\t");
					System.out.print(rs.getDate("re_regdate")+"\t");
					if(rs.getDate("re_modifydate")==null) {
						System.out.println("");
					}else {
						System.out.println(rs.getDate("re_modifydate"));
					}
				}while(rs.next());
			}else {
				System.out.println("등록된 대출 정보가 없습니다.");
			}
			System.out.println("============================================================");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//사용자 아이디 중복 체크(ID 중복시 1, 미중복시 0)
	public int checkId(String me_id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT me_id FROM member WHERE me_id=?"; //행이 있으면 중복, 없으면 미중복
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,me_id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = 1;
			}//else일 경우 count = 0
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//사용자 회원 가입
	public void insertMember(String me_id, String me_passwd, String me_name, String me_phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO member (me_id,me_passwd,me_name,me_phone) "
					+ "VALUES (?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, me_id);
			pstmt.setString(2, me_passwd);
			pstmt.setString(3, me_name);
			pstmt.setString(4, me_phone);
			
			int count = pstmt.executeUpdate();
			System.out.println(me_name+"님의 회원가입이 완료되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//사용자 로그인 체크(로그인 성공시 true, 실패시 false 반환)
	public boolean loginCheck(String me_id,String me_passwd) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT me_id FROM member WHERE me_id=? AND me_passwd=?"; //일치하면 행 반환 = true, 불일치시 false
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, me_id);
			pstmt.setString(2, me_passwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) { //행이 존재한다면 로그인 성공
				flag = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return flag;
	}
	
	//사용자 도서 대출 여부 확인(도서번호(bk_num)로 검색해서 re_status의 값이 0이면 대출 가능,1이면 불가능-아이디 중복체크
	public int checkRent(int bk_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT re_status FROM book left outer JOIN "
					+ "(SELECT * FROM reservation WHERE re_status=1) "
					+ "USING(bk_num) WHERE bk_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bk_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("re_status");
			}else {
				count = 0;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//사용자 도서 대출 등록(중복 대출x)
	public void insertBookReservation(int bk_num,String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO reservation (re_num,bk_num,me_id,re_status) "
					+ "VALUES (reservation_seq.nextval,?,?,1)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,bk_num);
			pstmt.setString(2, me_id);
	
			int count = pstmt.executeUpdate();
			System.out.println(count+"권이 대출되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//사용자 MY 대출 목록 보기(현재 대출한 목록만 표시)
	public void selectmyReservation(String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT re_num,bk_name,re_status,re_regdate"
					+ " FROM reservation JOIN book USING(bk_num) WHERE me_id=? AND re_status=1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, me_id);
			
			rs = pstmt.executeQuery();
			System.out.println("==========================================");
			if(rs.next()) {
				System.out.println("대출번호\t대출도서명\t대출여부\t등록일");
				System.out.println("==========================================");
				do {
					System.out.print(rs.getInt("re_num")+"\t");
					System.out.print(rs.getString("bk_name")+"\t");
					if(rs.getInt("re_status")==1) {
						System.out.print("대출중"+"\t"); //1
					}
//					System.out.print(rs.getInt("re_status")+"\t");
					System.out.println(rs.getDate("re_regdate"));
				}while(rs.next());
			}else {
				System.out.println("등록된 MY 대출 목록이 없습니다.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//사용자 도서 반납 가능 여부(대출번호(re_num)과 회원ID(me_id)를 함께 조회해서 re_status가 1은 반납 가능, 0은 반납 불가능
	public int returnCheck(int re_num, String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT re_status FROM book left outer JOIN "
					+ "(SELECT * FROM reservation WHERE re_status=1) "
					+ "USING(bk_num) WHERE re_num=? AND me_id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, re_num);
			pstmt.setString(2,me_id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//사용자 반납 처리
	public void updateReservation(String me_id,int re_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql="UPDATE reservation SET re_status=0,re_modifydate=SYSDATE WHERE me_id=? AND re_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, me_id);
			pstmt.setInt(2, re_num);
			
			int count = pstmt.executeUpdate();
			System.out.println(count+"권의 도서가 반납되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}