package tr.com.java.designpattern.adapter.objectpool;

import java.sql.Connection;

public class Main {
	  public static void main(String args[]) {
	    // Do something...
	   

	    // Create the ConnectionPool:
	    JDBCConnectionPool pool = new JDBCConnectionPool(
	      "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.109.42)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=GRANITE)))",
	      "TT_R1", "tt_r1");

	    // Get a connection:
	    Connection con = pool.checkOut();

	    // Use the connection
	    

	    // Return the connection:
	    pool.checkIn(con);
	    
	    System.out.println("OK");
	 
	  }
	}
