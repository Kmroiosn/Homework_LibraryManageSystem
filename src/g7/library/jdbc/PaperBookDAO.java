package g7.library.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import g7.library.model.PaperBook;


public class PaperBookDAO {
	public static Connection conn = DBConnectionFactory.getInstance();
	
	protected PaperBookDAO() {}
	
	public static int insertBook(String bookName, String authorName, String version, int pages, int stock, double price, String paperType) {
		String sql = "insert into paper_book values(NULL, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bookName);
			pst.setString(2, authorName);
			pst.setString(3, version);
			pst.setInt(4, pages);
			pst.setInt(5, stock);
			pst.setDouble(6, price);
			pst.setString(7, paperType);
			
			int count = pst.executeUpdate();
			conn.commit();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return -1;
		}
	}
	
	public static int deleteBook(int id) {
		String sql = "delete from paper_book where id = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			
			int count = pst.executeUpdate();
			conn.commit();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return -1;
		}
	}
	
	public static int updateBook(int id, PaperBook book) {
		if (!(book instanceof PaperBook)) {
			return -1;
		}
		String sql = "update paper_book set bookName = ?, authorName = ?, version = ?, pages = ?, stock = ?, price = ?, paperType = ? where id = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			
			pst.setString(1, book.getBookName());
			pst.setString(2, book.getAuthorName());
			pst.setString(3, book.getVersion());
			pst.setInt(4, book.getPages());
			pst.setInt(5, book.getStock());
			pst.setDouble(6, book.getPrice());
			pst.setString(7, book.getPaperType().getTypeName());
			pst.setInt(8, id);
			
			int count = pst.executeUpdate();
			conn.commit();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return -1;
		}
	}
	
	public static Vector<PaperBook> getPaperBooksByNameOrAuthor(String name) {
		String sql = "select id, bookName, authorName, version, pages, stock, price, paperType from paper_book where bookName like ? or authorName like ?;";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setString(2, "%" + name + "%");
			ResultSet rs = pst.executeQuery();
			Vector<PaperBook> list = new Vector<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName = rs.getString("bookName");
				String authorName = rs.getString("authorName");
				String version = rs.getString("version");
				int pages = rs.getInt("pages");
				int stock = rs.getInt("stock");
				double price = rs.getDouble("price");
				String paperType = rs.getString("paperType");
				list.add(new PaperBook(id, bookName, authorName, version, pages, stock, price, paperType));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Vector<PaperBook> getAllBooks() {
		String sql = "select * from paper_book";
		
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			Vector<PaperBook> list = new Vector<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName = rs.getString("bookName");
				String authorName = rs.getString("authorName");
				String version = rs.getString("version");
				int pages = rs.getInt("pages");
				int stock = rs.getInt("stock");
				double price = rs.getDouble("price");
				String paperType = rs.getString("paperType");
				list.add(new PaperBook(id, bookName, authorName, version, pages, stock, price, paperType));
			} 
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;		
	}
}

