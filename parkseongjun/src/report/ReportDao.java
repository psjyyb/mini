package report;

import java.sql.*;
import java.util.*;

import post.Post;
import post.PostDao;


public class ReportDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Scanner sc = new Scanner(System.in);
	Post post =new Post();
	Report report = new Report();
	static int num ;
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

	public boolean report() {
		getConn();
		String sql = "update post set report_hand = 1 where report_hand = 0 and post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, PostDao.writeNo);
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<Report> ReportList(){
		getConn();
		List<Report>list = new ArrayList<Report>();
		String sql = "select post_number,post_title,report_hand from post where report_hand = 1";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				Report report = new Report();
				report.setPostNumber(rs.getInt("post_number"));
				report.setReportTitle(rs.getString("post_title"));
				
				list.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void rps (){
		ReportDao tDao = new ReportDao();
		List<Report> lists = tDao.ReportList();
				for(Report rpt : lists) {
					System.out.println(rpt.toReport());
				}
				System.out.println("게시물을 열람 하실려면 1 나가기는 2 번을 눌러주세요.");
				System.out.print("입력 > ");
				int choose = Integer.parseInt(sc.nextLine());
				if(choose==1) {
					tDao.recon2();
				}else {
					return;
				}
	}
	List<Post> recon(){
		getConn();
		List<Post> list = new ArrayList<Post>();
		System.out.print("열람하실 게시글 번호를 입력해주세요 : ");
		 num = Integer.parseInt(sc.nextLine());
		String sql = "select post_title,post_content from post where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPostTitle(rs.getString("post_title"));
				post.setPostContent(rs.getString("post_content"));
				list.add(post);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return list;	
	}
	public void recon2() {
		 PostDao pDao = new PostDao();
		 ReportDao tDao = new ReportDao();
		List<Post> posts = tDao.recon();
		 for(Post ps : posts) {
				System.out.println(ps.toReport());
		}
		 System.out.println("신고 받은 게시물을 삭제하시려면 1 번 유지하시려면 2번을 눌러주세요.");
		 int rnum = Integer.parseInt(sc.nextLine());
		 if(rnum==1) {
			 getConn();
				String sql = "delete post where post_number = ?";
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, num);
					int r = psmt.executeUpdate();
					if (r > 0) {
						System.out.println("삭제 되었습니다.");
					}else {
						System.out.println("삭제가 불가능 합니다.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		 }else {
			 getConn();
			 String sql = "update post set report_hand = 0 where post_number = ?";
			 try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, num);
				int r = psmt.executeUpdate();
				if (r > 0) {
					System.out.println("게시글을 유지 합니다.");
				}else {
					System.out.println("오류발생");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
	}
}