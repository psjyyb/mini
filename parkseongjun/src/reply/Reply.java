package reply;

public class Reply {
	private int postNumber;
	private int replyNumber;
	private String replyWrite;
	private int good;
	@Override
	public String toString() {
		return "댓글번호 : " + replyNumber + " 댓글 :" + replyWrite;
	}
	public int getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}
	public int getReplyNumber() {
		return replyNumber;
	}
	public void setReplyNumber(int replyNumber) {
		this.replyNumber = replyNumber;
	}
	public String getReplyWrite() {
		return replyWrite;
	}
	public void setReplyWrite(String replyWrite) {
		this.replyWrite = replyWrite;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	
}
