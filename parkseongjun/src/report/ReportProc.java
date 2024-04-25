package report;

import java.util.*;

public class ReportProc {
	Scanner sc = new Scanner(System.in);
	// 신고받은 게시물 목록
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
}
