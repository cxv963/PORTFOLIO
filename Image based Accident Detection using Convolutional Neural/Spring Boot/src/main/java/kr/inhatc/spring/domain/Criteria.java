package kr.inhatc.spring.domain;

public class Criteria {
	
	private int page;
	private int perPageNum;
	
	public Criteria() {
		// TODO Auto-generated constructor stub
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public int getDataStart() {
		return (this.page - 1) * perPageNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page < 0) {
			this.page = 1;
		}else {
			this.page = page;
		}
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
	
}
