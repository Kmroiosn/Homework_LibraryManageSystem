package g7.library.model;

public class EBook extends Book{
    //格式
    private FileFormat fileFormat;
    public enum FileFormat {
        EPUB("epub"), PDF("pdf"), EXE("exe"), TXT("txt");

        private String m_FormatName;
        private FileFormat(String formatName) {
            m_FormatName = formatName;
        }
        public String getFormatName() {
            return m_FormatName;
        }
        public static FileFormat getFormatFromName(String formatName) {
            for (FileFormat format : values()) {
                if (format.m_FormatName.equals(formatName)) return format;
            }
            System.err.println("无法找到名为" + formatName + "的文件格式");
            return null;
        }
    }

    //构造器
    public EBook(int id,String bookName, String authorName, String version, int pages, int stock, double price, String fileFormatName) {
        super(id,bookName, authorName, version, pages, stock, price);
        this.fileFormat = FileFormat.getFormatFromName(fileFormatName);
    }

    //setter and getter
    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }

    //实现的方法
    @Override
    public void sell() {
        System.out.println("销售电子书");
    }

    @Override
    public void read() {
        System.out.println("阅读电子书");
    }

	@Override
	public String toString() {
		return String.format("电子书[ ID: %d, 书名: %s, 作者名: %s, 版号: %s, 页数: %d, 存货: %d本, 价格: %.1f元, 文件格式: %s ]",
				this.id, this.bookName, this.authorName, this.version, this.pages, this.stock, this.price, this.fileFormat.m_FormatName);
	}

}
