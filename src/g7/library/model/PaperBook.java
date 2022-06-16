package g7.library.model;

public class PaperBook extends Book{
    //纸质
    private PaperType paperType;
    public enum PaperType {
        ART_PAPER("铜版纸"),KRAFT_PAPER("牛皮纸");

        private String m_TypeName;
        private PaperType(String typeName) {
            m_TypeName = typeName;
        }
        public String getTypeName() {
            return m_TypeName;
        }
        public static PaperType getTypeFromName(String typeName) {
            for (PaperType type : values()) {
                if (type.m_TypeName.equals(typeName)) return type;
            }
            System.err.println("无法找到名为" + typeName + "的纸张类型");
            return null;
        }
    }

    //构造器
    public PaperBook(int id,String bookName, String authorName, String version, int pages, int stock, double price, String paperTypeName) {
        super(id,bookName, authorName, version, pages, stock, price);
        this.paperType = PaperType.getTypeFromName(paperTypeName);
    }
    //setter and getter 
    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    //实现的方法
    @Override
    public void sell() {
        System.out.println("销售纸质书");
    }

    @Override
    public void read() {
        System.out.println("阅读纸质书");
    }

	@Override
	public String toString() {
		return String.format("纸质书[ ID: %d, 书名: %s, 作者名: %s, 版号: %s, 页数: %d, 存货: %d本, 价格: %.1f元, 纸质类型: %s ]",
				this.id, this.bookName, this.authorName, this.version, this.pages, this.stock, this.price, this.paperType.m_TypeName);
	}

}
