package bigScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class AppMain {

	protected Shell shell;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppMain window = new AppMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		shell.setSize(1024, 768);
		shell.setText("东陆高新大屏显示");
		shell.setLayout(null);
		
		AppConfig app = new AppConfig();
		String properties = app.getProperties(AppConfig.key_labels);
		String[] split = properties.split(",");
		for (String string : split) {
			try{
				final Text text = new Text(shell, SWT.NONE);
				
				String sql = String.valueOf(app.getProperties(string+".sql"));
				
				Integer x = Integer.valueOf(app.getProperties(string+".x"));
				Integer y = Integer.valueOf(app.getProperties(string+".y"));
				Integer width = Integer.valueOf(app.getProperties(string+".width"));
				Integer height = Integer.valueOf(app.getProperties(string+".height"));
				
				Integer font = Integer.valueOf(app.getProperties(string+".font"));
				
				text.setBackground(new Color(shell.getDisplay(), 0,0,0));
				text.setForeground(new Color(shell.getDisplay(), 255,0,0));
				text.setBounds(x, y, width, height);
				text.setFont(new Font(shell.getDisplay(),"宋体",font,SWT.NORMAL));
				
				new Thread(new UpdateTextThread(text, sql)).start();
			}catch(Exception e){
				System.out.println("创建显示label时发生错误:"+string);
			}
		}
	}
	
	class UpdateTextThread implements Runnable{
		
		private Text text;
		private String sql;
		
		public UpdateTextThread(Text text, String sql) {
			this.text = text;
			this.sql = sql;
		}

		@Override
		public void run() {
			while(true){
				try {
					final String executeSql = DatabaseUtil.getInstance().executeSql(sql);
					if(executeSql == null || executeSql.isEmpty()){
						return;
					}else{
						Display.getDefault().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								text.setText(executeSql);
							}
						});
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						TimeUnit.SECONDS.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
