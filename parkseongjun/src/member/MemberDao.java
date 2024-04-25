package member;

import java.sql.*;
import java.util.*;

import post.PostDao;

public class MemberDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Scanner sc = new Scanner(System.in);

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
	// 로그인 기능
	public Member login(String id, String pw) {
		getConn();
		String sql = "select mem_id,mem_pw,mem_number from member where mem_id = ? and mem_pw = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			rs = psmt.executeQuery();
			if (rs.next()) {
				Member member = new Member();
				member.setMemId(rs.getString("mem_id"));
				member.setMemPw(rs.getString("mem_pw"));
				member.setMemNumber(rs.getInt("mem_number"));
				return member;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
				Member member = new Member();
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
	// 아이디 찾기
	public String findId(String name, String day) {
		getConn();
		String sql = "select mem_id from member where mem_name = ? and mem_birthday = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			psmt.setString(2, day);
			rs = psmt.executeQuery();
			String id = "";
			if (rs.next()) {
				id = rs.getString("mem_id");
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// 비밀번호 찾기
	public String findPw(String id, String name, String day) {
		getConn();
		String sql = "select mem_pw from member where mem_id = ? and mem_name = ? and mem_birthday = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, name);
			psmt.setString(3, day);
			rs = psmt.executeQuery();
			String pw = "";
			if (rs.next()) {
				pw = rs.getString("mem_pw");
				return pw;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}