package kr.s11.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BookAdminMain {
	
	private BufferedReader br;
	private BookDAO dao;
	
	public BookAdminMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new BookDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null)try {br.close();}catch(IOException e) {}
		}
	}
	
	public void callMenu() throws IOException{
		while(true) {
			System.out.print("1.도서 등록 | 2.도서 목록 | 3.대출 목록 | 4.회원 목록 | 5.종료> ");
			try {									//상품구매참고
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//도서 등록
					System.out.print("도서명 : ");
					String bk_name = br.readLine();
					System.out.print("카테고리 : ");
					String bk_category = br.readLine();
					
					dao.insertBook(bk_name, bk_category);
					
				}else if(no==2) {//도서 목록 (book정보) 대출여부!
					/*
					 * -------------------------------------
					 * 번호  카테고리  도서명  대출여부  등록일
					 *  42   천문   별이야기 대출가능  2023-10-11
					 *  41   IT     자바    대출중  2023-10-11
					 * -------------------------------------
					 *
					 */
					dao.selectBook();
				}else if(no==3) {//대출 목록(reservation)
					/*
					 * -----------------------------------------------------
					 * 번호  대출여부  대출자ID  대출도서명    대출일   반납일(UPDATE)
					 * -----------------------------------------------------
					 * 20   대출     blue      자바   2023-10-11
					 * 19   반납     sky     별이야기  2023-10-09  2023-10-10
					 * -----------------------------------------------------
					 */
					dao.selectReservation();
				}else if(no==4) {//회원 목록(member)
					/*
					 * ------------------------------------------
					 * ID    이름     전화번호           가입일
					 * sky   홍길동  010-1234-5678   2023-09-09
					 * blue  장영실  010-5678-1234   2023-09-08
					 * ------------------------------------------
					 */
					dao.selectMember();
				}else if(no==5) {//종료
					System.out.println("프로그램 종료");
					break;
				}else {
					System.out.println("잘못 입력했습니다.");
				}
				
			}catch(NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}
	
	
	public static void main(String[] args) {
		new BookAdminMain();
	}
}