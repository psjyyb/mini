package co.yedam;

import java.util.*;
import member.*;
import post.Post;
import post.PostDao;
import report.*;

public class Manager {
	Scanner sc = new Scanner(System.in);
	Member member = new Member();
	MemberDao mDao = new MemberDao();
	ReportDao tDao = new ReportDao();
	ReportProc pr = new ReportProc();

	public void sys() {
		System.out.println("관리자 전용 계정입니다.");
		System.out.print("아이디  > ");
		String id = sc.nextLine();
		System.out.print("비밀번호  > ");
		String pw = sc.nextLine();
		System.out.print("관리자 코드 > ");
		int manager = 0;
		try {
		manager = Integer.parseInt(sc.nextLine());
		if (mDao.system(id, pw, manager)) {
			boolean run = true;
			System.out.println("관리자 계정에 로그인 되었습니다.");
			while (run) {
				System.out.println("1.특정회원정보 2.신고된게시글목록 3.관리자 로그아웃");
				System.out.print("입력 > ");
				int choose = Integer.parseInt(sc.nextLine());
				switch (choose) {
				case 1:
					System.out.print("조회하실 회원번호 > ");
					int num = Integer.parseInt(sc.nextLine());
					List<Member> members = mDao.check(num);
					member.setMemNumber(num);
					for (Member m : members) {
						System.out.println(m.toString());
					}
					break;
				case 2:
					pr.rps();
					break;
				case 3:
					run = false;
					break;
					
				}
			}
		} else {
			System.out.println("관리자가 아닙니다");
		}
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	
	}
}