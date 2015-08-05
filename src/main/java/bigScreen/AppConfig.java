package bigScreen;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

public class AppConfig {
	
	public static final String fileName = "appConfig.properties";
	
	public static final String key_ip = "database.ip";
	public static final String key_port = "database.port";
	public static final String key_username = "database.username";
	public static final String key_password = "database.password";
	
	public static final String key_labels="config.labels";
	public static final String key_sleep = "config.sleep";
	
	public static final String value_ip = "127.0.0.1";
	public static final String value_port = "1433";
	public static final String value_username = "sa";
	public static final String value_password = "1";
	
	public final String value_labels="label1";
	public static final String value_sleep = "3000";
	
	private Properties p = new Properties();
	
	public AppConfig(){
		createConfigFile();
		
		try(FileInputStream is = new FileInputStream(fileName) ; InputStreamReader fis = new InputStreamReader(is,"UTF-8")){
			p.load(fis);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createConfigFile(){
		Path path = Paths.get(fileName);
		if(!Files.exists(path)){
			try {
				Files.createFile(path);
				PrintWriter print = new PrintWriter(fileName,"utf-8");
				print.println("#config on date: "+new Date());
				print.println();
				print.println("#数据库ip");
				print.println(String.format("%s=%s", key_ip,value_ip));
				print.println("#数据库port");
				print.println(String.format("%s=%s", key_port,value_port));
				print.println("#数据库用户名");
				print.println(String.format("%s=%s", key_username,value_username));
				print.println("#数据库密码");
				print.println(String.format("%s=%s", key_password,value_password));
				print.println();
				print.println("#刷新间隔");
				print.println(String.format("%s=%s", key_sleep,value_sleep));
				print.println();
				print.println("#显示字段");
				print.println(String.format("%s=%s", key_labels,value_labels));
				print.println();
				
				print.println("#label1");
				print.println(String.format("%s=%s", "label1.sql","select '木工组'"));
				print.println(String.format("%s=%s", "label1.font","20"));
				print.println(String.format("%s=%s", "label1.x","10"));
				print.println(String.format("%s=%s", "label1.y","10"));
				print.println(String.format("%s=%s", "label1.width","100"));
				print.println(String.format("%s=%s", "label1.height","80"));
				
				print.flush();
				print.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getProperties(String key){
		return p.getProperty(key);
	}

}
