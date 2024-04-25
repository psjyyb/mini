package post;

import java.util.List;
import java.util.Scanner;

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
			System.out.println("----------------------------------------");
			System.out.println("1.게시글쓰기 2.게시글삭제 3.나의 게시글 4.게시글목록 5.로그아웃");
			System.out.println("----------------------------------------");
			System.out.print("입력 > ");
			int choose = Integer.parseInt(sc.nextLine());
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
			case 5:
				System.out.println("로그아웃합니다");
				run = false;
				break;
			}
		}
	}// end

	public void postWrite2() {
		System.out.println("글쓰기 메뉴 입니다.");
		System.out.print("제목 > ");
		String title = sc.nextLine();
		System.out.print("카테고리 >");
		String kind = sc.nextLine();
		System.out.print("내용 > ");
		String content = sc.nextLine();

		Post post = new Post();
		post.setPostTitle(title);
		post.setPostKind(kind);
		post.setPostContent(content);
		post.setMemNumber(logid);

		int set = pDao.postWrite(post);
		System.out.println("게시글이 저장되었습니다. 게시글 번호 :" + set);
	} // end

	public void postDelete2() {
		System.out.print("게시글번호 >> ");
		int set = Integer.parseInt(sc.nextLine());
		if (pDao.postDelete(set)) {
			System.out.println("정상 삭제");
			return;
		} else {
			System.out.println("다른 회원의 게시글을 삭제할수 없습니다.");
		}
	} // end

	public void myPost1() {
		List<Post> posts = pDao.mypost();
		System.out.println("게시글 번호   게시글 제목");
		System.out.println("-------------------");
		for (Post ps : posts) {
			System.out.println(ps.toString());
		}
		System.out.println("게시물을 수정 하실려면 1 나가기는 2 번을 눌러주세요.");
		System.out.print("입력 > ");
		int choose = Integer.parseInt(sc.nextLine());
		if (choose == 1) {
			postUpdate();
		} else {
			return;
		}
	} // end

	public void postUpdate() {
		System.out.print("수정하실 게시글 번호 > ");
		int set = Integer.parseInt(sc.nextLine());
		System.out.print("내용 입력 > ");
		String content = sc.nextLine();

		Post post = new Post();
		post.setPostNumber(set);
		post.setPostContent(content);
		post.setMemNumber(set);
//		pDao.postUpdate(null)
	}

	void post2() {

	}
}
