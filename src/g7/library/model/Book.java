package g7.library.model;

public abstract class Book {
	protected int	 id;
    protected String bookName;	// 书名
    protected String authorName;// 作者名
    protected String version;	// 第几版
    protected int pages;		// 页数
    protected int stock;		// 存货
    protected double price;		// 价格

    //构造器
    public Book(int id, String bookName, String authorName, String version, int pages, int stock, double price) {
        this.id = id;
    	this.bookName = bookName;
        this.authorName = authorName;
        this.version = version;
        this.pages = pages;
        this.stock = stock;
        this.price = price;
    }
    public Book() {}
    // 需要在子类中实现的方法
    public abstract void sell();
    public abstract void read();

    //setter and getter
    
    public String getBookName() {
        return bookName;
    }
    public int getId() {
		return id;
	}
    public String getAuthorName() {
        return authorName;
    }
    public String getVersion() {
        return version;
    }
    public int getPages() {
        return pages;
    }
    public int getStock() {
        return stock;
    }
    public double getPrice() {
        return price;
    }
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", authorName=" + authorName + ", version=" + version
				+ ", pages=" + pages + ", stock=" + stock + ", price=" + price + "]";
	}



}
