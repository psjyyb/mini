package reply;

import java.sql.*;
import java.util.*;

import member.Member;
import member.MemberDao;
import post.*;

public class ReplyDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Scanner sc = new Scanner(System.in);
	Reply reply = new Reply();

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

	// 댓글 남기기
	public boolean addReply2() {
		System.out.print("댓글 입력 > ");
		String re = sc.nextLine();
		reply.setReplyWrite(re);
		ReplyDao rDao = new ReplyDao();
		if (addReply()) {
			return true;
		}
		return false;
	}

	public boolean addReply() {
		getConn();
		String sql = "insert into reply (reply_number,post_number,reply_write,mem_number)"
				+ " values(reply_seq.nextval,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, PostDao.writeNo);
			psmt.setString(2, reply.getReplyWrite());
			psmt.setInt(3, MemberDao.loginNo);
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Reply> viewReply(int pNo) {
		getConn();
		List<Reply> list = new ArrayList<Reply>();
		String sql = "select reply_write,reply_number from reply where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, pNo);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Reply reply2 = new Reply();
				reply2.setReplyNumber(rs.getInt("reply_number"));
				reply2.setReplyWrite(rs.getString("reply_write"));
				list.add(reply2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean deleteReply2() {
		ReplyDao rDao = new ReplyDao();
		System.out.print("댓글번호 >>");
		int set = Integer.parseInt(sc.nextLine());
		if (rDao.deleteReply(set)) {
			return true;
		}
		return false;
	}

	// 자신이 남긴 댓글 삭제
	public boolean deleteReply(int delNum) {
		getConn();
		String sql = "delete reply where reply_number = ? and mem_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, delNum);
			psmt.setInt(2, MemberDao.loginNo);
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 좋아요
	public boolean good() {
		getConn();
		String sql2 = "select *\r\n" + " from good\r\n" + " where mem_number = ? and post_number = ?";
		try {
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, MemberDao.loginNo);
			psmt.setInt(2, PostDao.writeNo);
			rs = psmt.executeQuery();
			if (!rs.next()) {
				String sql1 = "insert into good (mem_number,post_number,good_count)" + "values (?,?,?)";
				try {
					psmt = conn.prepareStatement(sql1);
					psmt.setInt(1, MemberDao.loginNo);
					psmt.setInt(2, PostDao.writeNo);
					psmt.setInt(3, 1);
					int r = psmt.executeUpdate();
					if (r > 0) {
						return true;
					}System.out.println(123);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}else {
				String sql ="delete good where mem_number = ? and post_number = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, MemberDao.loginNo);
				psmt.setInt(2, PostDao.writeNo);
				int r = psmt.executeUpdate();
				if(r>0) {
				System.out.println("좋아요 취소");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	// 좋아요 수 
	public int goodNum() {
		getConn();
		String sql = "SELECT sum(good_count)\r\n"
				+ "	from good\r\n"
				+ " where post_number = ?\r\n";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, PostDao.writeNo);
			rs = psmt.executeQuery();
			int gn = 0;
			if(rs.next()) {
				gn = rs.getInt(1);
			}
			return gn;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
			return 0;
	}
}
