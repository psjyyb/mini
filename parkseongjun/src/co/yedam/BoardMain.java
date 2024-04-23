package co.yedam;

import java.util.*;
import member.*;

public class BoardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		MemberDao mDao = new MemberDao();
		Member member = new Member();
		while(run) {
			System.out.println("-----------------------------------------");
			System.out.println("1.로그인 2.회원가입 3.회원정보 수정 4.종료및로그아웃 5.관리자계정");
			System.out.println("-----------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
			switch(choose) {
			case 1 :
				System.out.print("아이디를 입력해주세요 > ");
				String id = sc.nextLine();
				System.out.print("비밀번호를 입력해주세요 > ");
				String pw = sc.nextLine();
				if(mDao.login(id, pw)) {
					System.out.println("로그인 되었습니다.");
				}
				break;
			case 2 :
				System.out.println("회원가입 메뉴입니다");
				System.out.print("이름 > ");
				String name = sc.nextLine();
				System.out.print("생년월일 > ");
				String birthday = sc.nextLine();
				System.out.print("아이디 > ");
				id = sc.nextLine();
				System.out.print("비밀번호 > ");
				pw = sc.nextLine();
				System.out.print("별명 > ");
				String nickName = sc.nextLine();
				System.out.print("관리자 > ");
				int manager = Integer.parseInt(sc.nextLine());
				
				member.setMemName(name);
				member.setMemBirthday(birthday);
				member.setMemId(id);
				member.setMemPw(pw);
				member.setMemNickname(nickName);
				member.setManager(manager);
				if(mDao.addMem(member)) {
					System.out.println("회원가입이 완료되었습니다");
				}else {
					System.out.println("회원가입이 정상적이지 않습니다.");
				}			
				break;
			case 3 :
				System.out.println("회원정보 수정 메뉴입니다");
				System.out.print("아이디 > ");
				id = sc.nextLine();
				System.out.println("변경된이름 > ");
				name = sc.nextLine();
				System.out.println("변경된 비밀번호 > ");
				pw =sc.nextLine();
				System.out.println("변경된 별명 > ");
				nickName = sc.nextLine();
				member.setMemId(id);
				member.setMemPw(pw);
				member.setMemName(name);
				member.setMemNickname(nickName);
				
				if(mDao.reMem(member)) {
					System.out.println("수정이 완료되었습니다");
				}else {
					System.out.println("올바르지 않은 수정입니다.");
				}
				break;
			case 4 :
				run=false;
				break;
			case 5 :
				System.out.println("관리자 전용 계정입니다.");
				System.out.print("아이디  > ");
				id=sc.nextLine();
				System.out.print("비밀번호  > ");
				pw=sc.nextLine();
				System.out.print("관리자 코드 > ");
				manager = Integer.parseInt(sc.nextLine());
				if(mDao.system(id, pw, manager)) {
					System.out.println("관리자 계정에 로그인 되었습니다.");
					System.out.println("1.특정회원정보 2.신고된게시글 3.게시글 삭제");
					System.out.print("입력 > ");
					choose = Integer.parseInt(sc.nextLine());
					switch(choose) {
					case 1 :
						List<Member> members = mDao.check();
						for(Member m : members) {
							System.out.println(m.toString());
						}
						break;
					case 2 :
						break;
					case 3 :
						break;
					case 4 :
						break;
					}
				}else {
					System.out.println("관리자가 아닙니다");
				}
				break;
			}
		}
	}

}
