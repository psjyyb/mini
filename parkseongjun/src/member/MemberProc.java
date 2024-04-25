package member;

import java.util.*;
import post.*;

public class MemberProc {
	Scanner sc = new Scanner(System.in);
	MemberDao mDao = new MemberDao();
	PostDao pDao = new PostDao();

	public void exe() {
		boolean run = true;
		while (run) {
			System.out.println("-------------------------------------------------");
			System.out.println("1.로그인 2.회원가입 3.ID/PW찾기 4.회원정보 수정 5.종료및로그아웃 6.관리자계정");
			System.out.println("-------------------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());

			switch (choose) {
			case 1:
				login2();
				break;
			case 2:
				addMem2();
				break;
			case 3:
				findIdPw();
				break;
			case 4:
				reMem2();
				break;
			case 5:
				System.out.println("프로그램을 종료합니다");
				run = false;
				break;
			case 6:

			}
		} // end of while.
	}

	// 로그인
	public void login2() {
		Member logMem = null;
		while (true) {
			System.out.print("아이디를 입력해주세요 > ");
			String id = sc.nextLine();
			System.out.print("비밀번호를 입력해주세요 > ");
			String pw = sc.nextLine();
			logMem = mDao.login(id, pw);
			if (logMem == null) {
				System.out.println("계정정보를 확인해주세요.");
				continue;
			}
			break;
		} //

		System.out.println("로그인 되었습니다.");
		PostProc proc = new PostProc(logMem.getMemNumber());
		proc.exe();
	}

	// 회원가입
	public void addMem2() {

		System.out.println("회원가입 메뉴입니다");
		System.out.print("이름 > ");
		String name = sc.nextLine();
		System.out.print("생년월일 > ");
		String birthday = sc.nextLine();
		System.out.print("아이디 > ");
		String id = sc.nextLine();
		System.out.print("비밀번호 > ");
		String pw = sc.nextLine();
		System.out.print("별명 > ");
		String nickName = sc.nextLine();

		Member member = new Member();
		member.setMemName(name);
		member.setMemBirthday(birthday);
		member.setMemId(id);
		member.setMemPw(pw);
		member.setMemNickname(nickName);

		if (mDao.addMem(member)) {
			System.out.println("회원가입이 완료되었습니다");
		} else {
			System.out.println("회원가입이 정상적이지 않습니다.");
		}
	}

	// id, pw 찾기
	void findIdPw() {

	}

	// 정보수정
	void reMem2() {

	}

}
