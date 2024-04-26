package report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import post.Post;
import post.PostDao;
import post.PostProc;

public class ReportDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	Scanner sc = new Scanner(System.in);
	Post post = new Post();
	Report report = new Report();

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
	// 신고
	public boolean report(String post) {
		getConn();
		String sql = "update post set report_hand = 1,report_write = ? where post_number = ?";
		try {
			
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(2, PostDao.writeNo);
			psmt.setString(1,post);
			
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 신고받은 게시글 목록
	public List<Report> ReportList() {
		getConn();
		List<Report> list = new ArrayList<Report>();
		String sql = "select post_number,post_title,report_hand from post where report_hand = 1";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
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

	// 신고 받은 게시물 상세보기
	List<Post> recon() {
		getConn();
		List<Post> list = new ArrayList<Post>();
		ReportProc rp = new ReportProc();
		String sql = "select post_title,post_content,post_kind ,report_write from post where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, rp.num);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setPostTitle(rs.getString("post_title"));
				post.setPostContent(rs.getString("post_content"));
				post.setPostKind(rs.getString("post_kind"));
				post.setReportWrite(rs.getString("report_write"));
				
				
				list.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean recon2() {
		ReportProc rp = new ReportProc();

		getConn();
		String sql = "delete post where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, rp.num);
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean recon3() {
		ReportProc rp = new ReportProc();
		getConn();
		String sql = "update post set report_hand = 0 where post_number = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, rp.num);
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}