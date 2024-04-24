package co.yedam;

import java.util.*;
import member.*;
import post.*;

public class BoardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		MemberDao mDao = new MemberDao();
		Member member = new Member();
		PostDao pDao = new PostDao();
		Manager ma = new Manager();

		while (run) {
			System.out.println("-----------------------------------------------------");
			System.out.println("1.로그인 2.회원가입 3.회원정보 수정 4.종료및로그아웃 5.관리자계정");
			System.out.println("-----------------------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
			switch (choose) {
			case 1:
				mDao.login2();
				choose = Integer.parseInt(sc.nextLine());
				switch (choose) {
				case 1:
					pDao.postWrite2();
					break;
				case 2:
					pDao.PostDelete2();
					break;
				case 3:
					break;
				}
				break;
			case 2:
				mDao.addMem2();
				break;
			case 3:
				mDao.reMem2();
				break;
			case 4:
				run = false;
				break;
			case 5:
					ma.sys();
				} 
				break;
			}
		System.out.println("프로그램을 종료합니다");
		}
	}

