package kr.s07.note;
//기본적인 메뉴를 만들고 연동할 수 있게 만들기

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class NoteMain {
	private BufferedReader br;
	private NoteDAO note;
	
	public NoteMain() {
		try {
			note = new NoteDAO();
			br = new BufferedReader(new InputStreamReader(System.in));
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null)try {br.close();}catch(IOException e) {}
		}
		
	}
	//메뉴
	public void callMenu() throws IOException {
		while(true) {
			System.out.print("1.글쓰기 | 2.목록보기 | 3.상세글보기 | 4.글수정 | 5.글삭제 | 6.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//글쓰기
					note.insertInfo("홍길동", "1234", "가을", "서늘한 날씨", "test@test.com");
				}else if(no==2){//목록보기
					note.selectInfo();
				}else if(no==3) {//상세글보기
					note.selectDetailInfo(10);
				}else if(no==4) {//글수정
					note.updateInfo(10, "홍길동", "1234", "가을", "서늘한 날씨", "test@test.com");
				}else if(no==5) {//글삭제
					note.deleteInfo(10);
				}else if(no==6) {//종료
					System.out.println("프로그램을 종료합니다.");
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
		new NoteMain();
	}
}
