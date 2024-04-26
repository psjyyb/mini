package reply;

import java.util.*;

import post.PostDao;

public class ReplyProc {
	Scanner sc = new Scanner(System.in);
	
	ReplyDao rDao = new ReplyDao();
	
	// 댓글 남기기
	public boolean addReply2() {
		Reply reply = new Reply();
		System.out.print("댓글 입력 > ");
		String re = sc.nextLine();
		reply.setReplyWrite(re);
		if (rDao.addReply(reply.getReplyWrite())) {
			return true;
		}
		return false;
	}
	// 댓글 삭제
	public boolean deleteReply2() {
		List<Reply> replys = rDao.viewReply(PostDao.writeNo);
		for (Reply rp : replys) {
			System.out.println(rp.toString());
		}
		ReplyDao rDao = new ReplyDao();
		System.out.print("댓글번호 >>");
		int set = 0;
		try {
		set = Integer.parseInt(sc.nextLine());
		if (rDao.deleteReply(set)) {
			return true;
		}
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
