package member;

public class Member {
	private int memNumber ;
	private String memName;
	private String memBirthday;
	private String memId;
	private String memPw;
	private String memNickname;
	private int manager;
	public int getManager() {
		return manager;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}
	@Override
	public String toString() {
		return "Member [회원번호 : " + memNumber + ", 이름 : " + memName + ", 생년월일 : " + memBirthday + ", 아이디 : "
				+ memId + ", 비밀번호 : =" + memPw + ", 별명 : " + memNickname +"]";
	}
	public int getMemNumber() {
		return memNumber;
	}
	public void setMemNumber(int memNumber) {
		this.memNumber = memNumber;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemBirthday() {
		return memBirthday;
	}
	public void setMemBirthday(String memBirthday) {
		this.memBirthday = memBirthday;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
	public String getMemNickname() {
		return memNickname;
	}
	public void setMemNickname(String memNickname) {
		this.memNickname = memNickname;
	}
}
