package bigScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	
	private static DatabaseUtil du = new DatabaseUtil();
	
	public static DatabaseUtil getInstance(){
		return du;
	}
	
	private DatabaseUtil(){
		
	}
	
	public Connection getConnection(String driver,String url,String username,String password) {
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username,password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String executeSql(String sql) throws SQLException{
		AppConfig app = new AppConfig();
		String ip = app.getProperties(AppConfig.key_ip);
		String port = app.getProperties(AppConfig.key_port);
		String username = app.getProperties(AppConfig.key_username);
		String password = app.getProperties(AppConfig.key_password);
		String url = String.format("jdbc:jtds:sqlserver://%s:%s/onecard", ip,port);
		
		try(Connection connection = getConnection("net.sourceforge.jtds.jdbc.Driver", url, username, password);
				Statement createStatement = connection.createStatement();
				ResultSet executeQuery = createStatement.executeQuery(sql);){
			
			while(executeQuery.next()){
				return executeQuery.getString(1);
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("÷¥––≤È—Ø ß∞‹£∫"+sql);
			return null;
		}
	}
}
