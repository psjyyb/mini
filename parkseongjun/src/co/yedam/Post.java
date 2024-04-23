package co.yedam;

public class Post {
	private int memNumber;
	public int getMemNumber() {
		return memNumber;
	}
	public void setMemNumber(int memNumber) {
		this.memNumber = memNumber;
	}
	public int getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostKind() {
		return postKind;
	}
	public void setPostKind(String postKind) {
		this.postKind = postKind;
	}
	private int postNumber;
	private String postTitle;
	private String postContent;
	private String postKind;
	
}

