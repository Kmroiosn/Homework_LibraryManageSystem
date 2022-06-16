package g7.library.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnectionFactory {
	private static Connection m_Connection;
	
	public static Connection getInstance() {
		if (m_Connection == null) {
			try {
				FileReader fr = new FileReader(new File("DatabaseSettings.txt"));
				BufferedReader br = new BufferedReader(fr);
				if (!fr.ready() || !br.ready()) System.err.println("无法从DatabaseSettings.txt中获取数据库访问信息");
				
				String address  = br.readLine();
				String username = br.readLine();
				String passwd   = br.readLine();
						
				m_Connection = DriverManager.getConnection(address, username, passwd);
				m_Connection.setAutoCommit(false);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		return m_Connection;
	}
}
