package marsana.logicmanager;

import java.sql.*;



public class DataBaseManager {
	
	private String ip;
	private String port;
	private String DBName;
	private String username;
	private String password;
	
	
	public DataBaseManager(String ip, String port, String DBName, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.DBName = DBName;
		this.username = username;
		this.password = password;
	}
	
	public void insertRow(float importo, String dataEmissione, String periodoRelativo) {
		//11/04/2022 to 2022-04-11
		//dd/mm/yyyy to yyyy-mm-dd
		String formatData = dataEmissione.substring(6,10) + "-" + dataEmissione.substring(3,5) 
			+ "-" + dataEmissione.substring(0,2);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =  DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port +"/ "
			+ DBName, username, password);
			
			Statement stat = conn.createStatement();
			
			//build the query
			String query = "INSERT INTO buste (importo_netto, data_emissione, periodo_relativo) "
					+ "VALUES ('" + importo + "' , '" + formatData + "', '" + periodoRelativo + "');";
			//run the query
			stat.executeUpdate(query);
				
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
