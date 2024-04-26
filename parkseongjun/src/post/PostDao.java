package post;

import java.sql.*;
import java.util.*;

import member.*;
import reply.*;
import report.ReportDao;

public class PostDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Member member = new Member();
	Post post = new Post();
	Scanner sc = new Scanner(System.in);
	public static int writeNo;
	public static String reason ;
	private void getConn() {
		String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "psj", "yyb");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	// 게시글 쓰기
	public int postWrite(Post post) {
		getConn();
		String sql1 = "select post_seq.nextval from dual";
		String sql = "insert into post (mem_number,post_number,post_title,post_kind,post_content)"
				+ " values (?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql1);
			rs = psmt.executeQuery();
			int seq = -1;
			if (rs.next()) {
				seq = rs.getInt(1);
			}

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, post.getMemNumber());
			psmt.setInt(2, seq);
			psmt.setString(3, post.getPostTitle());
			psmt.setString(4, post.getPostKind());
			psmt.setString(5, post.getPostContent());
			int r = psmt.executeUpdate();
			if (r > 0)
				return seq;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 게시글 삭제o
	public boolean postDelete(int seq) {
		getConn();
		String sql = "delete post where post_number = ? and mem_number = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			psmt.setInt(2, MemberProc.logNum);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 게시글 목록(게시글 번호 선택시 게시글 내용 확인)o
	List<Post> post() {
		getConn();
		List<Post> list = new ArrayList<Post>();
		String sql = "select post_number, post_title from post order by post_number";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setPostNumber(rs.getInt("post_number"));
				post.setPostTitle(rs.getString("post_title"));

				list.add(post);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 게시물 상세보기
	List<Post> conten(int num) {
		getConn();
		List<Post> list = new ArrayList<Post>();
		String sql = "select post_title,post_kind,post_content, report_write from post where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setPostTitle(rs.getString("post_title"));
				post.setPostKind(rs.getString("post_kind"));
				post.setPostContent(rs.getString("post_content"));
				list.add(post);
				writeNo = num;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	// 나의 게시글 목록 o
	List<Post> mypost() {
		getConn();
		List<Post> list = new ArrayList<Post>();
		String sql = "select post_number, post_title from post where mem_number = ? order by post_number";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1,MemberProc.logNum);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setPostNumber(rs.getInt("post_number"));
				post.setPostTitle(rs.getString("post_title"));

				list.add(post);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	// 나의 게시글 수정o
	public boolean postUpdate(Post post) {
		getConn();

		String sql = "update post set post_content = ? where mem_number = ? and post_number = ? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, post.getPostContent());
			psmt.setInt(2, post.getMemNumber());
			psmt.setInt(3, post.getPostNumber());
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}