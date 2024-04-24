package post;

import java.sql.*;
import java.util.*;

import member.Member;

public class PostDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Member member = new Member();
	Post post = new Post();
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
	public void postWrite2() {
		PostDao pDao = new PostDao();
		Post post = new Post();
		System.out.println("글쓰기 메뉴 입니다.");
		System.out.print("제목 > ");
		String title = sc.nextLine();
		System.out.print("카테고리 >");
		String kind = sc.nextLine();
		System.out.print("내용 > ");
		String content = sc.nextLine();
		post.setPostTitle(title);
		post.setPostKind(kind);
		post.setPostContent(content);
		int set = pDao.postWrite(post);
		System.out.println("게시글이 저장되었습니다. 게시글 번호 :" + set);
	}
	// 게시글 쓰기 
	public int postWrite(Post post) {
		getConn();
		String sql1 = "select post_seq.nextval from dual";
		String sql = "insert into post (post_number,post_title,post_kind,post_content)"
				+  " values (?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql1);
			rs= psmt.executeQuery();
			int seq = -1;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			psmt.setString(2,post.getPostTitle());
			psmt.setString(3,post.getPostKind());
			psmt.setString(4,post.getPostContent());
			int r = psmt.executeUpdate();
			return seq;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; 
	}
	public void PostDelete2() {
		PostDao pDao = new PostDao();
		System.out.print("게시글번호 >> ");
		 int set = Integer.parseInt(sc.nextLine());
		if (pDao.postDelete(set)) {
			System.out.println("정상 삭제");
			 return;
		} else {
			System.out.println("예외 발생");
		} return;
		}
	// 게시글 삭제
	public boolean postDelete(int seq) {
		getConn();
		String sql = "delete post where post_number = ?";
		try {
		psmt = conn.prepareStatement(sql);
		psmt.setInt(1, seq);
		
		int r = psmt.executeUpdate();
		if(r>0) {
			return true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}	  return false;
	}
	
	
	// 게시글 목록(게시글 번호 선택시 게시글 내용 확인)
}
