package kr.s11.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BookUserMain {
	
	private BufferedReader br;
	private BookDAO dao;
	private String me_id; //로그인한 아이디 저장
	private boolean flag; //로그인 여부, 초기값 false
	
	public BookUserMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new BookDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null)try {br.close();}catch(IOException e){}
		}
	}
	
	public void callMenu() throws IOException{
		while(true) {
			System.out.print("1.로그인 | 2.회원가입 | 3.종료> ");
			try {
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//로그인
					System.out.print("아이디 : ");
					me_id = br.readLine();
					System.out.print("비밀번호 : ");
					String me_passwd = br.readLine();
					
					flag = dao.loginCheck(me_id, me_passwd);
					if(flag) {
						System.out.println(me_id +"님 로그인 되었습니다.");
						break; //현재 while문 빠져나가 while(flag)로 이동
					}
					System.out.println("아이디 또는 비밀번호 불일치합니다.");
					
				}else if(no==2) {//회원가입
					System.out.print("아이디 : ");
					String me_id = br.readLine();
					//아이디 중복체크
					int check = dao.checkId(me_id);
					if(check==1) {
						System.out.println("아이디가 중복되었습니다.");
					}else {
						System.out.println("아이디를 사용할 수 있습니다."); //회원가입으로 이동
						System.out.print("비밀번호 : ");
						String me_passwd = br.readLine();
						System.out.print("이름 : ");
						String me_name = br.readLine();
						System.out.print("전화번호 : ");
						String me_phone = br.readLine();
						
						dao.insertMember(me_id, me_passwd, me_name, me_phone);
					}
				}else if(no==3) {//종료
					System.out.println("프로그램 종료");
					break;
				}else {
					System.out.println("잘못 입력했습니다.");
				}
			}catch(NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
		
		while(flag) { //로그인이 되었을 때 진입(true)
			System.out.println("==============================================");
			System.out.print("1.도서대출 | 2.MY대출목록 | 3.대출도서 반납 | 4.종료> "); //MY대출목록 읽어올 때 위에 있는 me_id 변수 사용

			try {
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//도서 대출
					/*
					 * ========================================
					 * 번호   카테고리   도서명   대출여부   등록일
					 * ========================================
					 * 43     IT      자바    대출가능  2023-10-10
					 * 42     천문    별이야기  대출가능  2023-10-10
					 * ========================================
					 *
					 * [도서 대출하기]
					 * 도서 번호 : 43
					 * 도서 1건이 대출되었습니다.
					 */
					dao.selectBook();
					System.out.print("도서 번호 : ");
					int bk_num = Integer.parseInt(br.readLine());
					System.out.print("아이디 : ");
					me_id = br.readLine();
					
					dao.insertBookReservation(bk_num, me_id);	
					
				}else if(no==2) {//MY대출목록
					/*
					 * ===================================
					 * 번호   도서명   대출여부   등록일
					 * 41    자바     대출중   2023-10-10
					 * ===================================
					 */
					System.out.print("아이디 : ");
					me_id = br.readLine();
					
					dao.selectmyReservation(me_id);
					
				}else if(no==3) {//대출도서 반납
					/*
					 * ========================================
					 *  번호   도서명   대출여부   등록일
					 *  41    자바     대출중   2023-10-10
					 * ========================================
					 * [도서 반납하기]
					 * 대출번호 : 41
					 * 1건의 도서가 반납되었습니다.
					 */
					System.out.print("아아디 : ");
					me_id = br.readLine();
					
					dao.selectmyReservation(me_id);
					
					System.out.print("번호 : ");
					int re_num = Integer.parseInt(br.readLine());
					
					dao.updateReservation(me_id,re_num);
					
				}else if(no==4) {
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
		new BookUserMain();
	}
}