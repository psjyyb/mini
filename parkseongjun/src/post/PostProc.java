package post;

import java.util.List;
import java.util.Scanner;

import member.MemberProc;
import reply.Reply;
import reply.ReplyDao;
import reply.ReplyProc;
import report.Report;
import report.ReportDao;

public class PostProc {
	int logid;
	Scanner sc = new Scanner(System.in);
	PostDao pDao = new PostDao();
	

	public PostProc(int logid) {
		this.logid = logid;
	}

	public void exe() {
		boolean run = true;
		while (run) {
			System.out.println("---------------------------------------------------------------");
			System.out.println("1.게시글쓰기 2.게시글삭제 3.나의 게시글 4.게시글목록 5.회원정보 수정6.로그아웃");
			System.out.println("---------------------------------------------------------------");
			System.out.print("입력 > ");
			int choose = 0;
			try {
			choose = Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			switch (choose) {
			case 1:
				postWrite2();
				break;
			case 2:
				postDelete2();
				break;
			case 3:
				myPost1();
				break;
			case 4:
				post2();
				break;
			case 5 :
				MemberProc mp = new MemberProc();
				mp.reMem2();
				break;
			case 6:
				System.out.println("로그아웃합니다");
				run = false;
				break;
			}
		}
		
	}// end

	public void postWrite2() {
		System.out.println("글쓰기 메뉴 입니다.");
		System.out.print("제목(10자이내) > ");
		String title = sc.nextLine();
		System.out.print("카테고리(10자이내) >");
		String kind = sc.nextLine();
		System.out.print("내용(500자이내) > ");
		String content = sc.nextLine();

		Post post = new Post();
		post.setPostTitle(title);
		post.setPostKind(kind);
		post.setPostContent(content);
		post.setMemNumber(logid);

		int set = pDao.postWrite(post);
		System.out.println("게시글이 저장되었습니다. 게시글 번호 :" + set);
	} // end
	// 나의 게시글 삭제
	public void postDelete2() {
		System.out.print("게시글번호 >> ");
		int set = 0;
		try {
		set = Integer.parseInt(sc.nextLine());
		if (pDao.postDelete(set)) {
			System.out.println("정상 삭제");
			return;
		} else {
			System.out.println("다른 회원의 게시글을 삭제할수 없습니다.");
		}
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	} // end
   // 나의 게시글 목록
	public void myPost1() {
		List<Post> posts = pDao.mypost();
		System.out.println("게시글 번호   게시글 제목");
		System.out.println("---------------------");
		for (Post ps : posts) {
			System.out.println(ps.toString());
		}
		System.out.println("게시물을 수정 하실려면 1 나가기는 2 번을 눌러주세요.");
		System.out.print("입력 > ");
		int choose = 0;
		try {
		choose = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		if (choose == 1) {
			postUpdate();
		} else {
			return;
		}
	} // end
	//게시글 수정
	public void postUpdate() {
		System.out.print("수정하실 게시글 번호 > ");
		int set = 0;
		try {
		set = Integer.parseInt(sc.nextLine());
		System.out.print("내용 입력 > ");
		String content = sc.nextLine();
		
		Post post = new Post();
		post.setPostNumber(set);
		post.setPostContent(content);
		post.setMemNumber(set);
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
//		pDao.postUpdate(null)
	}
	// 게시물 목록
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
		int choose = 0;
		try {
		choose = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		if (choose == 1) {
			post3();
		} else {
			return;
		}
	}
	// 게시물 상세보기 , 댓글 , 좋아요 ,신고 
	public void post3() {
		PostDao pDao = new PostDao();
		ReplyDao rDao = new ReplyDao();
		ReportDao tDao = new ReportDao();
		ReplyProc pr = new ReplyProc();
		boolean run =false;
		System.out.print("열람 하실 게시물의 번호를 입력해주세요 > ");
		int num = 0;
		try {
		num = Integer.parseInt(sc.nextLine());
		List<Post> posts = pDao.conten(num);
		for (Post ps : posts) {
			System.out.println(ps.toAll() + "댓글 ");
		}
		List<Reply> replys = rDao.viewReply(PostDao.writeNo);
		for (Reply rp : replys) {
			System.out.println(rp.toString());
		}
		System.out.println("좋아요 수 : " + rDao.goodNum());
		System.out.println("게시물을 신고하시려면 report 를 입력해주세요.");
		String report = sc.nextLine();
		if (report.equals("report")) {
			Post post = new Post();
			System.out.print("신고 사유(30자 이내) > ");
			PostDao.reason = sc.nextLine();
			post.setReportWrite(PostDao.reason);
			if (tDao.report(PostDao.reason)) {
				System.out.println("신고 완료");
			} else {
				System.out.println("신고가 되지않습니다.");
			}
		}
		run = true;
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		while (run) {
			System.out.println("1.댓글 2.댓글삭제 3.좋아요 4.나가기");
			System.out.print("입력 > ");
			int choose = 0;
			try {
			choose = Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			switch (choose) {
			case 1:
				if (pr.addReply2()) {
					System.out.println("댓글이 저장되었습니다.");
				} else {
					System.out.println("댓글이 저장하지 않았습니다.");
				}
				break;
			case 2:
				if (pr.deleteReply2()) {
					System.out.println("댓글이 삭제되었습니다.");
				} else {
					System.out.println("자신의 댓글만 삭제 가능합니다.");
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
}
