package g7.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import g7.library.jdbc.EBookDAO;
import g7.library.jdbc.PaperBookDAO;
import g7.library.model.EBook;
import g7.library.model.EBook.FileFormat;
import g7.library.model.PaperBook;
import g7.library.model.PaperBook.PaperType;

/**************************************************
        ______ _      _ _                          
      |____  | |    (_) |                         
   __ _   / /| |     _| |__  _ __ __ _ _ __ _   _ 
  / _` | / / | |    | | '_ \| '__/ _` | '__| | | |
 | (_| |/ /  | |____| | |_) | | | (_| | |  | |_| |
  \__, /_/   |______|_|_.__/|_|  \__,_|_|   \__, |
   __/ |                                     __/ |
  |___/                                     |___/ 
 **************************************************/
/*** 第七组 图书管理系统 ***/
public class LibrarySystem {
	private Scanner input = new Scanner(System.in);//全局 input 输入
	private static final String EBOOK 		= "1";
	private static final String PAPERBOOK 	= "2";
	
	private static final String[] INPUT_LIST = {"书名", "作者名", "版本号", "页数", "存货", "价格"};
	
	private final String LOGO = 
			  "      ____ _    _ _                      	\r\n"
			+ "  __ |__  | |  (_) |__ _ _ __ _ _ _ _  _ 	\r\n"
			+ " / _` |/ /| |__| | '_ \\ '_/ _` | '_| || |	\r\n"
			+ " \\__,/_/ |____|_|_.__/_| \\__,_|_| \\_,|	\r\n"
			+ " |___/                              |__/			";
	
	
	public void showMenu() {
		// 打印选项
		System.out.printf("┌──────────────┐\n");
		System.out.printf("│ 1-%-10s \n", "添加");
		System.out.printf("│ 2-%-10s \n", "删除");
		System.out.printf("│ 3-%-10s \n", "更新");
		System.out.printf("│ 4-%-10s \n", "查找");
		System.out.printf("│ 5-%-10s \n", "列出所有");
		System.out.printf("└──────────────┘\n");
		System.out.print("请输入指令: ");
	}
	
	public LibrarySystem() {

		System.out.println(LOGO);
		
		while (true) {
			showMenu();

			int choice = input.nextInt();
			input.nextLine();
			switch (choice) {
			case 0:
				System.exit(0);
			case 1:
				this.addBook();
				break;
			case 2:
				this.deleteBook();
				break;
			case 3:
				this.updateBook();
				break;
			case 4:
				this.searchBook();
				break;
			case 5:
				this.listAllBooks();
				break;
			default:
				System.out.println("无效指令!");
			}
		}
	}
	
	private void addBook() {
		System.out.print("请选择所添加书的种类(1:电子书  2.纸质书): ");
		String type = input.nextLine();
		type = type.equals("电子书") || type.equals("1") ? EBOOK 
				: type.equals("纸质书") || type.equals("2") ? PAPERBOOK : "ERROR";
		if (type.equals("ERROR")) {
			System.err.println("未检测到正确的输入!");
			return;
		}
		
		List<String> bookInfo = inputBookInfo("新增", type);
		
		if(bookInfo == null) { return;}
		try {
			switch (type) {
			case EBOOK:
				EBookDAO.insertBook(bookInfo.get(0), bookInfo.get(1), bookInfo.get(2), 
						Integer.parseInt(bookInfo.get(3)), Integer.parseInt(bookInfo.get(4)), 
						Double.parseDouble(bookInfo.get(5)), 
						bookInfo.get(6));
				break;
			case PAPERBOOK:
				PaperBookDAO.insertBook(bookInfo.get(0), bookInfo.get(1), bookInfo.get(2), 
						Integer.parseInt(bookInfo.get(3)), Integer.parseInt(bookInfo.get(4)), 
						Double.parseDouble(bookInfo.get(5)), 
						bookInfo.get(6));
				break;
			}
		} catch(NumberFormatException e) {
			System.err.println("数据输入有误!终止新增操作!");
			return;
		} catch(Exception e) {
			System.err.println("捕获到未知异常!");
			e.printStackTrace();
			return;
		}

		System.out.println("新增操作成功");
	}

	private void deleteBook() {
		System.out.print("请选择所删除书的种类(1:电子书  2.纸质书): ");
		String type = input.nextLine();
		type = type.equals("电子书") || type.equals("1") ? EBOOK 
				: type.equals("纸质书") || type.equals("2") ? PAPERBOOK : "ERROR";
		if (type.equals("ERROR")) {
			System.err.println("未检测到正确的输入!");
			return;
		}
		
		System.out.println("查询中...请稍候");
		
		switch(type) {
		case EBOOK:
		{
			Vector<EBook> bookList = EBookDAO.getAllBooks();
			List<Integer> allBookIDList = new ArrayList<>();
			
			bookList.forEach(ebook -> {
				System.out.println(ebook.toString());
				allBookIDList.add(ebook.getId());
			});
					
			System.out.print("请输入要删除书本的ID(批量删除则在ID间以空格为间隔): ");
			String inputIDs = input.nextLine();
			String[] idStrList = inputIDs.split(" ");
			List<Integer> idList = new ArrayList<>();
			try {
				for (String idStr : idStrList) {
					int id = Integer.parseInt(idStr);
					if (allBookIDList.contains(id)) {
						idList.add(id);
					} 
					else System.err.println("无id为" + idStr + "的书，已去掉该单独操作!");
				}
			} catch(NumberFormatException e) {
				System.err.println("ID内容必须为数字!终止删除操作!");
				return;
			}
			
			for(int id : idList) {
				EBookDAO.deleteBook(id);
			}
		}
			
			break;
			
		case PAPERBOOK:
		{
			Vector<PaperBook> bookList = PaperBookDAO.getAllBooks();
			List<Integer> allBookIDList = new ArrayList<>();
			
			bookList.forEach(pbook -> {
				System.out.println(pbook.toString());
				allBookIDList.add(pbook.getId());
			});
					
			System.out.print("请输入要删除书本的ID(批量删除则在ID间以空格为间隔): ");
			String inputIDs = input.nextLine();
			String[] idStrList = inputIDs.split(" ");
			List<Integer> idList = new ArrayList<>();
			try {
				for (String idStr : idStrList) {
					int id = Integer.parseInt(idStr);
					if (allBookIDList.contains(id)) {
						idList.add(id);
					} 
					else System.err.println("无id为" + idStr + "的书，已去掉该单独操作!");
				}
			} catch(NumberFormatException e) {
				System.err.println("ID内容必须为数字!终止删除操作!");
				return;
			}
			
			for(int id : idList) {
				PaperBookDAO.deleteBook(id);
			}
		}
			break;
		}
		System.out.println("删除操作成功!");
	}
	
	private void updateBook() {
		System.out.print("请选择所更新书的种类(1:电子书  2.纸质书): ");
		String type = input.nextLine();
		type = type.equals("电子书") || type.equals("1") ? EBOOK 
				: type.equals("纸质书") || type.equals("2") ? PAPERBOOK : "ERROR";
		if (type.equals("ERROR")) {
			System.err.println("未检测到正确的输入!");
			return;
		}
		
		int id = -1;
		
		switch(type) {
		case EBOOK:
		{
			Vector<EBook> bookList = EBookDAO.getAllBooks();
			List<Integer> allBookIDList = new ArrayList<>();
			
			bookList.forEach(ebook -> {
				System.out.println(ebook.toString());
				allBookIDList.add(ebook.getId());
			});
					
			System.out.print("请输入要修改书本的ID: ");
			String idStr = input.nextLine();
			try {
				int temp = Integer.parseInt(idStr);
				if (allBookIDList.contains(temp)) {
					id = temp;
				} else System.err.println("无法找到该ID的书籍!终止修改操作!");
			} catch(NumberFormatException e) {
				System.err.println("ID内容必须为中间无空格的数字!终止修改操作!");
				return;
			}
		}
			break;
			
		case PAPERBOOK:
		{
			Vector<PaperBook> bookList = PaperBookDAO.getAllBooks();
			List<Integer> allBookIDList = new ArrayList<>();
			
			bookList.forEach(pbook -> {
				System.out.println(pbook.toString());
				allBookIDList.add(pbook.getId());
			});
					
			System.out.print("请输入要修改书本的ID: ");
			String idStr = input.nextLine();
			try {
				int temp = Integer.parseInt(idStr);
				if (allBookIDList.contains(temp)) {
					id = temp;
				} else System.err.println("无法找到该ID的书籍!终止修改操作!");
			} catch(NumberFormatException e) {
				System.err.println("ID内容必须为中间无空格的数字!终止修改操作!");
				return;
			}
		}
			break;
		}
		
		List<String> inputInfoList = inputBookInfo("修改后", type);
		
		if(inputInfoList == null) { return;}
		try {
			switch (type) {
			case EBOOK:
				EBook ebook = new EBook(id, inputInfoList.get(0), inputInfoList.get(1), inputInfoList.get(2), 
						Integer.parseInt(inputInfoList.get(3)), Integer.parseInt(inputInfoList.get(4)), 
						Double.parseDouble(inputInfoList.get(5)), 
						inputInfoList.get(6));
				EBookDAO.updateBook(id, ebook);
				break;
			case PAPERBOOK:
				PaperBook pbook = new PaperBook(id, inputInfoList.get(0), inputInfoList.get(1), inputInfoList.get(2), 
						Integer.parseInt(inputInfoList.get(3)), Integer.parseInt(inputInfoList.get(4)), 
						Double.parseDouble(inputInfoList.get(5)), 
						inputInfoList.get(6));
				PaperBookDAO.updateBook(id, pbook);
				break;
			}
		} catch(NumberFormatException e) {
			System.err.println("数据输入有误!终止修改操作!");
			return;
		} catch(Exception e) {
			System.err.println("捕获到未知异常!");
			e.printStackTrace();
			return;
		}

		System.out.println("修改操作成功");
		
	}


	
	private void listAllBooks() {
		int totalNum = EBookDAO.getAllBooks().size() + PaperBookDAO.getAllBooks().size();
		if (totalNum < 1) {
			System.out.println("无收录书籍!");
			return;
		}
		System.out.println("现在一共有" + totalNum + "本书：");
		
		List<EBook> elist = EBookDAO.getAllBooks();
		if(elist != null && !elist.isEmpty()) {
			System.out.println("电子书：");
			elist.forEach(ebook -> System.out.println(ebook.toString()));			
		}
		
		List<PaperBook> plist = PaperBookDAO.getAllBooks();
		if(plist != null && !plist.isEmpty()) {
			System.out.println("纸质书：");
			plist.forEach(pbook -> System.out.println(pbook.toString()));			
		}
	}

	private void searchBook() {
		System.out.print("请输入要查找的内容(书名 / 作者名)(支持模糊查询): ");
		String searchName = input.nextLine();
		List<EBook> 	elist = EBookDAO.getEBooksByNameOrAuthor(searchName);
		List<PaperBook> plist = PaperBookDAO.getPaperBooksByNameOrAuthor(searchName);
		
		if ((elist == null || elist.isEmpty()) && (plist == null || plist.isEmpty())) System.out.println("查无此书!");
		
		if (elist != null && !elist.isEmpty()) {
			elist.forEach(System.out::println);
		}
		if (plist != null && !plist.isEmpty()) {
			plist.forEach(System.out::println);
		}
	}

	private List<String> inputBookInfo(String tipType, String bookType) {
		List<String> inputInfo = new ArrayList<>();
		for (int x = 0; x < INPUT_LIST.length; x++) {
			System.out.print("请输入所" + tipType + "书本的" + INPUT_LIST[x] + ": ");
			inputInfo.add(input.nextLine());
		}
		if (bookType.equals(EBOOK)) {
			System.out.print("请输入所" + tipType + "书本的格式(1.epub  2.pdf  3.exe  4.txt): ");
			String formatName = input.nextLine();
			formatName = formatName.equals("1") || formatName.toLowerCase().equals("epub") ? "epub"
					: formatName.equals("2") || formatName.toLowerCase().equals("pdf") ? "pdf"
							: formatName.equals("3") || formatName.toLowerCase().equals("exe") ? "exe"
									: formatName.equals("4") || formatName.toLowerCase().equals("txt") ? "txt" : "ERROR";
			EBook.FileFormat fileFormat = FileFormat.getFormatFromName(formatName);
			if (fileFormat == null) {
				System.err.println("格式输入错误!");
				return null;
			}
			inputInfo.add(formatName);
		}
		if (bookType.equals(PAPERBOOK)) {
			System.out.print("请输入所" + tipType + "书本的纸质类型(1.牛皮纸  2.铜版纸): ");
			String paperTypeName = input.nextLine();
			paperTypeName = paperTypeName.equals("1") || paperTypeName.equals("牛皮纸") ? "牛皮纸" 
					: paperTypeName.equals("2") || paperTypeName.equals("铜版纸") ? "铜版纸" : "ERROR";
			PaperBook.PaperType paperType = PaperType.getTypeFromName(paperTypeName);
			if (paperType == null) {
				System.err.println("纸质类型输入错误!");
				return null;
			}
			inputInfo.add(paperTypeName);
		}
		return inputInfo;
	}
	
}


