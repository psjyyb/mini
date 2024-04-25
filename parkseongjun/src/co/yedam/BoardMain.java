package co.yedam;

import java.util.*;
import member.*;
import post.*;

public class BoardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		MemberDao mDao = new MemberDao();
		PostDao pDao = new PostDao();
		Manager ma = new Manager();

		while (run) {
			System.out.println("-------------------------------------------------");
			System.out.println("1.로그인 2.회원가입 3.ID/PW찾기 4.회원정보 수정 5.종료및로그아웃 6.관리자계정");
			System.out.println("-------------------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
			switch (choose) {
			case 1:
				mDao.login2();	
				run=true;
				break;
			case 2:
				mDao.addMem2(); 
				break;
			case 3 :
				mDao.findIdPw();
				break;
			case 4:
				mDao.reMem2();
				break;
			case 5:
				System.out.println("프로그램을 종료합니다");
				run = false;
				break;
			case 6:
				ma.sys();
			}
			break;
		}
		
	}
}
