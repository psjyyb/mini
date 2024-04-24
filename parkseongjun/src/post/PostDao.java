package post;

import java.sql.*;
import java.util.*;

import member.*;
import reply.*;

public class PostDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Member member = new Member();
	Post post = new Post();
	Scanner sc = new Scanner(System.in);
	public static int writeNo ;
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
		Post post = new Post();
		PostDao pDao = new PostDao();
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
		String sql = "insert into post (mem_number,post_number,post_title,post_kind,post_content)" + " values (?,?,?,?,?)";
		String sql2 = "select mem_number from member where mem_id = ?";
		try {
			psmt = conn.prepareStatement(sql1);
			rs = psmt.executeQuery();			
			int seq = -1;
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, MemberDao.loginId);
			ResultSet rs2 = psmt.executeQuery();
	        int memNumber = -1;
	        if (rs2.next()) {
	            memNumber = rs2.getInt("mem_number");
	        }
			psmt = conn.prepareStatement(sql);	
			psmt.setInt(1, memNumber);
			psmt.setInt(2, seq);
			psmt.setString(3, post.getPostTitle());
			psmt.setString(4, post.getPostKind());
			psmt.setString(5, post.getPostContent());
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
			System.out.println("다른 회원의 게시글을 삭제할수 없습니다.");
		}
		return;
	}

	// 게시글 삭제
	public boolean postDelete(int seq) {
		getConn();
		String sql = "delete post where post_number = ? and mem_number = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
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

	// 게시글 목록(게시글 번호 선택시 게시글 내용 확인)
	List<Post> post(){
		getConn();
	List<Post> list = new ArrayList<Post>();
	String sql = "select post_number, post_title from post order by post_number";
	try {
		psmt = conn.prepareStatement(sql);
		rs = psmt.executeQuery();
		while(rs.next()) {
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
		for(Post ps : posts) {
			System.out.println(ps.toString());
		}
		System.out.println("게시물을 열람 하실려면 1 나가기는 2 번을 눌러주세요.");
		System.out.print("입력 > ");
		int choose = Integer.parseInt(sc.nextLine());
		if(choose==1) {
			pDao.post3();
		}else {
			return;
		}
		
		
	}
	List<Post> conten(){
		getConn();
		List<Post> list = new ArrayList<Post>();
		
		System.out.print("열람 하실 게시물의 번호를 입력해주세요 > ");
		int num = Integer.parseInt(sc.nextLine());
		String sql = "select post_title,post_kind,post_content from post where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			while(rs.next()) {
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
			List<Post> posts = pDao.conten();		
			 for(Post ps : posts) {
				System.out.println(ps.toAll()+"댓글 ");
		}		
			 List<Reply> replys = rDao.viewReply(writeNo);
				for(Reply rp : replys) {
					System.out.println(rp.toString());
				}
			boolean run = true;
			
			 while(run) {	
			 System.out.println("1.댓글 2.댓글삭제 3.좋아요 4.나가기");
			 System.out.print("입력 > ");
			 int choose = Integer.parseInt(sc.nextLine());
			 switch(choose) {
			 case 1 :
				 if(rDao.addReply2()) {
					 System.out.println("댓글이 저장되었습니다.");
				 }else {
					 System.out.println("댓글이 저장하지 않았습니다.");
				 }
				 break;
			 case 2 :
				 if(rDao.deleteReply2()){
					 System.out.println("댓글이 삭제되었습니다.");
				 }else {
					 System.out.println("댓글이 삭제 되지 않았습니다");
				 }
				 break;
			 case 3 :
				 break;
			 case 4 :
				 run = false;
				 break;
			 }
			 }
	
}
}