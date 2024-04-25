package member;

import java.util.Scanner;

import co.yedam.Manager;
import post.PostDao;
import post.PostProc;

public class MemberProc {
	public static int logNum;
	Scanner sc = new Scanner(System.in);
	MemberDao mDao = new MemberDao();
	PostDao pDao = new PostDao();
	Manager ma = new Manager();

	public void exe() {
		boolean run = true;
		while (run) {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("1.로그인 2.회원가입 3.ID찾기 4.PW찾기 5.회원정보 수정 6.종료및로그아웃 7.관리자계정");
			System.out.println("---------------------------------------------------------------------");
			System.out.print("입력 > ");
			int choose = 0;
			try {
			choose = Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			switch (choose) {
			case 1:
				login2();
				break;
			case 2:
				addMem2();
				break;
			case 3:
				findId();
				break;
			case 4:
				findPw();
				break;
			case 5:
				reMem2();
				break;
			case 6:
				System.out.println("프로그램을 종료합니다");
				run = false;
				break;
			case 7:
				ma.sys();
				break;

			}
		}
	} // end of while.

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
		logNum = logMem.getMemNumber();
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

	// Id 찾기
	void findId() {
		while (true) {
			System.out.print("이름 > ");
			String name = sc.nextLine();
			System.out.print("생년월일 > ");
			String day = sc.nextLine();
			String id = mDao.findId(name, day);
			if (id != null) {
				System.out.println("아이디는 " + id + "입니다.");
				break;
			} else {
				System.out.println("등록된 아이디가 없습니다.");
				continue;
			}
		}

	}

	// PW 찾기
	void findPw() {
		while (true) {
			System.out.print("아이디 > ");
			String id = sc.nextLine();
			System.out.print("이름 > ");
			String name = sc.nextLine();
			System.out.print("생년월일 >");
			String day = sc.nextLine();
			String pw = mDao.findPw(id, name, day);
			if (pw != null) {
				System.out.println("비밀번호는 " + pw + "입니다.");
				break;
			} else {
				System.out.println("회원정보를 확인해주세요.");
				continue;
			}
		}
	}

	// 정보수정
	public void reMem2() {
		while (true) {
			MemberDao mDao = new MemberDao();
			System.out.println("회원정보 수정 메뉴입니다");
			System.out.print("아이디 > ");
			String id = sc.nextLine();
			System.out.print("변경된이름 > ");
			String name = sc.nextLine();
			System.out.print("변경된 비밀번호 > ");
			String pw = sc.nextLine();
			System.out.print("변경된 별명 > ");
			String nickName = sc.nextLine();
			Member member = new Member();
			member.setMemId(id);
			member.setMemPw(pw);
			member.setMemName(name);
			member.setMemNickname(nickName);
			if (mDao.reMem(member)) {
				System.out.println("수정이 완료되었습니다");
				break;
			} else {
				System.out.println("계정이 존재 하지 않습니다.");
				continue;
			}
		}
	}
}
