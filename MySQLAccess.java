package solrAssignment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess {
  Map<String, String> Lat_and_long=new HashMap<String, String>();
	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
		private Properties props;

		public void init() throws Exception{
	    	
	       try{
	    	   Class.forName("com.mysql.jdbc.Driver");
	       
	        // Setup the connection with the DB
	        connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test?"+ "user=root&password=root");

	        // Statements allow to issue SQL queries to the database
	        statement = (Statement) connect.createStatement();
	       }
		    catch (Exception e) {
		        throw e;
		      }
		}
		
	  public Boolean readDataBase(String file, String key) throws Exception {
	    try {
	    	if (!(key.equals("") || key.equals(" ") || key.equals(null))){ 
	    		
	        // Result set get the result of the SQL query
	        resultSet = (ResultSet) statement.executeQuery("select latitude,longitude from new_schema.geonames where name LIKE '"+key+"%' ");
	        if (writeResultSet(resultSet, file)) 
	        {System.out.println("key:"+key); 
	        return true;
	        }
	    	}
	        return false;
	  }
  catch (Exception e) {
	        throw e;
	      }
	    
}
	  private boolean writeResultSet(ResultSet resultSet, String file) throws SQLException {
		    // ResultSet is initially before the first data set
		    while (resultSet.next()) {
		      String lat = resultSet.getString("latitude");
		      String longi = resultSet.getString("longitude");
		      Lat_and_long.put(file, lat+" "+longi);
		      return true;
		    }
		    return false;
		  }
	  
	  public void close() {
		  
		  Set set = Lat_and_long.entrySet();
		// Get an iterator
		Iterator i = set.iterator();
		// Display elements
		while(i.hasNext()) {
		Map.Entry me = (Map.Entry)i.next();
		System.out.print(me.getKey() + ": ");
		System.out.println(me.getValue());
		} 
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }

		      if (statement != null) {
		        statement.close();
		      }

		      if (connect != null) {
		        connect.close();
		      }
		    } catch (Exception e) {

		    }
}
}
