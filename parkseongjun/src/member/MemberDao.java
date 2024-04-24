package member;

import java.sql.*;
import java.util.*;

import post.PostDao;

public class MemberDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Member member = new Member();
	Scanner sc = new Scanner(System.in);
	public static String loginId;
	public static int loginNo;

	private void getConn() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "psj", "yyb");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public void login2() {
		PostDao pDao = new PostDao();
		MemberDao mDao = new MemberDao();
		System.out.print("아이디를 입력해주세요 > ");
		String id = sc.nextLine();
		System.out.print("비밀번호를 입력해주세요 > ");
		String pw = sc.nextLine();
		boolean run = mDao.login(id, pw);
		if (run) {
			System.out.println("로그인 되었습니다.");
			while(run) {
			System.out.println("----------------------------------------");
			System.out.println("1.게시글쓰기 2.게시글삭제 3.게시글목록 4.로그아웃");
			System.out.println("----------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
			switch (choose) {
			case 1:
				pDao.postWrite2();
				break;
			case 2:
				pDao.PostDelete2();
				break;
			case 3:
				pDao.post2();
				break;
			case 4 : 
				System.out.println("로그아웃합니다");
				run =false;
				break;
			}
		}
		}
			
		
	}

	// 로그인 기능
	public boolean login(String id, String pw) {
		getConn();
		String sql = "select mem_id,mem_pw,mem_number from member where mem_id = ? and mem_pw = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			rs = psmt.executeQuery();
			while (rs.next()) {
				member.setMemId(rs.getString("mem_id"));
				member.setMemPw(rs.getString("mem_pw"));
				member.setMemNumber(rs.getInt("mem_number"));
				if (member.getMemId().equals(id) && member.getMemPw().equals(pw)) {
					loginId = id;
					loginNo = rs.getInt("mem_number");
					return true;
				} else {
					System.out.println("계정을 확인해 주세요.");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addMem2() {
		MemberDao mDao = new MemberDao();
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
		System.out.print("관리자 > ");
		int manager = Integer.parseInt(sc.nextLine());
		member.setMemName(name);
		member.setMemBirthday(birthday);
		member.setMemId(id);
		member.setMemPw(pw);
		member.setMemNickname(nickName);
		member.setManager(manager);
		if (mDao.addMem(member)) {
			System.out.println("회원가입이 완료되었습니다");
		} else {
			System.out.println("회원가입이 정상적이지 않습니다.");
		}
	}

	// 회원가입 기능
	public boolean addMem(Member member) {

		getConn();
		String sql = "insert into member (mem_number,mem_name,mem_birthday,mem_id,mem_pw,mem_nickname,manager)\r\n"
				+ " values (member_seq.nextval,?,?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMemName());
			psmt.setString(2, member.getMemBirthday());
			psmt.setString(3, member.getMemId());
			psmt.setString(4, member.getMemPw());
			psmt.setString(5, member.getMemNickname());
			psmt.setInt(6, member.getManager());
			int r = psmt.executeUpdate();	
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void reMem2() {
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
		member.setMemId(id);
		member.setMemPw(pw);
		member.setMemName(name);
		member.setMemNickname(nickName);
		if (mDao.reMem(member)) {
			System.out.println("수정이 완료되었습니다");
		} else {
			System.out.println("올바르지 않은 수정입니다.");
		}
	}

	// 회원정보 수정
	public boolean reMem(Member member) {
		getConn();
		String sql = "update member set mem_name = ? ,mem_pw = ?" + " ,mem_nickname = ? where mem_id = ? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(4, member.getMemId());
			psmt.setString(1, member.getMemName());
			psmt.setString(2, member.getMemPw());
			psmt.setString(3, member.getMemNickname());
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 관리자 계정
	public boolean system(String id, String pw, int manager) {
		getConn();
		String sql = "select mem_id,mem_pw,manager from member where mem_id = ? and mem_pw = ? and manager = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			psmt.setInt(3, manager);
			rs = psmt.executeQuery();
			while (rs.next()) {
				member.setMemId(rs.getString("mem_id"));
				member.setMemPw(rs.getString("mem_pw"));
				member.setManager(rs.getInt("manager"));
				if (member.getMemId().equals(id) && member.getMemPw().equals(pw) && member.getManager() == manager) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 관리자 -> 특정회원 조회
	public List<Member> check(int num) {
		getConn();
		List<Member> list = new ArrayList<Member>();
		String sql = "select mem_number, mem_name, mem_birthday, mem_id, mem_pw, mem_nickname"
				+ " from member where mem_number = ? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Member member = new Member();
				member.setMemNumber(rs.getInt("mem_number"));
				member.setMemName(rs.getString("mem_name"));
				member.setMemName(rs.getString("mem_birthday"));
				member.setMemName(rs.getString("mem_id"));
				member.setMemName(rs.getString("mem_pw"));
				member.setMemName(rs.getString("mem_nickname"));

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
}