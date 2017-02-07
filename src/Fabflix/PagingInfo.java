package Fabflix;

public class PagingInfo {
	int currentPage;
	int pages;
	int[] helper;
	
	public PagingInfo(int pages, int currentPage) {
		this.currentPage = currentPage;
		this.pages = pages;
		this.helper = new int[pages];
	}
	
	public int[] getHelper() {
		return this.helper;
	}
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	public int getPages() {
		return this.pages;
	}
}
