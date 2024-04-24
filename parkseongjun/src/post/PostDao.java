package post;

import java.sql.*;
import member.Member;

public class PostDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Member member = new Member();
	Post post = new Post();
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
	public boolean postWrite(Post post) {
		getConn();
		String sql = "insert into post (post_number,post_title,post_kind,post_content)"
				+  " values (post_seq.nextval,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,post.getPostTitle());
			psmt.setString(2,post.getPostKind());
			psmt.setString(3,post.getPostContent());
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} return false;
	}
	// 게시글 삭제
	public void postDelete() {
		getConn();
		String sql = "delete post where = ?";
	}
	
	// 게시글 목록(게시글 번호 선택시 게시글 내용 확인)
}
