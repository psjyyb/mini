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

	// 게시글 삭제
	public boolean postDelete(int seq) {
		getConn();
		String sql = "delete post where post_number = ? and mem_number = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			psmt.setInt(2, member.getMemNumber());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 게시글 목록(게시글 번호 선택시 게시글 내용 확인)
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

	public void post2() {
		PostDao pDao = new PostDao();
		List<Post> posts = pDao.post();
		System.out.println("게시글 번호   게시글 제목");
		System.out.println("-------------------");
		for (Post ps : posts) {
			System.out.println(ps.toString());
		}
		System.out.println("게시물을 열람 하실려면 1 나가기는 2 번을 눌러주세요.");
		System.out.print("입력 > ");
		int choose = Integer.parseInt(sc.nextLine());
		if (choose == 1) {
			pDao.post3();
		} else {
			return;
		}

	}

	List<Post> conten() {
		getConn();
		List<Post> list = new ArrayList<Post>();

		System.out.print("열람 하실 게시물의 번호를 입력해주세요 > ");
		int num = Integer.parseInt(sc.nextLine());
		String sql = "select post_title,post_kind,post_content from post where post_number = ?";
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

	public void post3() {
		PostDao pDao = new PostDao();
		ReplyDao rDao = new ReplyDao();
		ReportDao tDao = new ReportDao();
		List<Post> posts = pDao.conten();
		for (Post ps : posts) {
			System.out.println(ps.toAll() + "댓글 ");
		}
		List<Reply> replys = rDao.viewReply(writeNo);
		for (Reply rp : replys) {
			System.out.println(rp.toString());
		}
		System.out.println("좋아요 수 : " + rDao.goodNum());
		System.out.println("게시물을 신고하시려면 report 를 입력해주세요.");
		String report = sc.nextLine();
		if (report.equals("report")) {
			if (tDao.report()) {
				System.out.println("신고 완료");
			} else {
				System.out.println("신고가 되지않습니다.");
			}
		}
		boolean run = true;

		while (run) {
			System.out.println("1.댓글 2.댓글삭제 3.좋아요 4.나가기");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
			switch (choose) {
			case 1:
				if (rDao.addReply2()) {
					System.out.println("댓글이 저장되었습니다.");
				} else {
					System.out.println("댓글이 저장하지 않았습니다.");
				}
				break;
			case 2:
				if (rDao.deleteReply2()) {
					System.out.println("댓글이 삭제되었습니다.");
				} else {
					System.out.println("댓글이 삭제 되지 않았습니다");
				}
				break;
			case 3:
				if (rDao.good()) {
					System.out.println("좋아요 !");
				}
				break;
			case 4:
				run = false;
				break;
			}
		}
	}

	List<Post> mypost() {
		getConn();
		List<Post> list1 = new ArrayList<Post>();
		String sql = "select post_number, post_title from post where mem_number = ? order by post_number";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, MemberDao.loginNo);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setPostNumber(rs.getInt("post_number"));
				post.setPostTitle(rs.getString("post_title"));

				list1.add(post);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list1;
	}

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