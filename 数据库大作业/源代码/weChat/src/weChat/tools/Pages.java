package weChat.tools;

public class Pages<T> {
	private Integer pageSize;
	private Integer currentPage;
//	private Integer firstPage;
//	private Integer previousPage;
//	private Integer nextPage;
//	private Integer lastPage;
//	private Integer pages;
	private Integer items;//数据的数量
	private T object;	
	
	public Pages(){
		pageSize = 10;
	}
	
	public void setList(T object) {
		this.object = object;
	}
	
	public T getList(){
		return this.object;
	}
	
	public Integer getItems() {
		return items;
	}
	public void setItems(Integer items) {
		this.items = items;
	}
	public Integer getPages() {
		return items%pageSize==0?items/pageSize:items/pageSize+1;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		//return currentPage;
		return currentPage<=1?1:currentPage>=this.getLastPage()?this.getLastPage():currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getFirstPage() {
		return 1;
	}
	public Integer getPreviousPage() {
		return currentPage<=1?1:currentPage-1;
	}
	public Integer getNextPage() {
		//System.out.println(currentPage>=this.getLastPage()?this.getLastPage():currentPage+1);
		return currentPage>=this.getLastPage()?this.getLastPage():currentPage+1;
	}
	public Integer getLastPage() {
		//System.out.println(items + "," + pageSize);
		int mod = items%pageSize;
		int result = items/pageSize;
		return mod==0?result:result+1;
	}
	public int getStartNumber(){
		return (this.getCurrentPage()-1)*pageSize;
	}
}
