package post;

public class Post {
	@Override
	public String toString() {
		return String.format("%-9d %5s", postNumber,postTitle);
	}

	public String toAll() {
		return String.format("제목   : %4s \n카테고리 : %5s\n내용   : %4s\n",postTitle,postKind,postContent);
	}
	private int memNumber;
	private int postNumber;
	private String postTitle;
	private String postContent;
	private String postKind;

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
}
