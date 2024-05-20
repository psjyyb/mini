package report;

import java.util.*;

import post.Post;

public class ReportProc {
	Scanner sc = new Scanner(System.in);
	static int num;

	// 신고받은 게시물 목록,상세보기,처리
	public void rps() {
		ReportDao tDao = new ReportDao();
		List<Report> lists = tDao.ReportList();
		for (Report rpt : lists) {
			System.out.println(rpt.toReport());
		}
		System.out.println("게시물을 열람 하실려면 1 나가기는 2 번을 눌러주세요.");
		System.out.print("입력 > ");
		int choose = Integer.parseInt(sc.nextLine());
		if (choose == 1) {
			List<Post> posts = tDao.recon();
			for (Post ps : posts) {
				System.out.println(ps.toReport());
			}
			System.out.print("열람하실 게시글 번호를 입력해주세요 : ");
			num = Integer.parseInt(sc.nextLine());
			List<Post> list = tDao.recon();
			for (Post prt : list) {
				System.out.println(prt.toReport());
			}
			System.out.println("신고 받은 게시물을 삭제하시려면 1 번 유지하시려면 2번을 눌러주세요.");
			int rnum = Integer.parseInt(sc.nextLine());
			if (rnum == 1) {
				if (tDao.recon2()) {
					System.out.println("삭제 되었습니다.");
				} else {
					System.out.println("삭제가 불가능 합니다.");
				}
			} else if (rnum == 2) {
				if (tDao.recon3()) {
					System.out.println("게시글을 유지 합니다.");
				} else {
					System.out.println("예외발생");
				}
			}
		} else {
			return;
		}
		
	}
}
