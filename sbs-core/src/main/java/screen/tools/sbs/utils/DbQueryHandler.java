package screen.tools.sbs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbQueryHandler {
	
	private String query;
	private Connection connection;
	private Statement statement;
	private ResultSet result;
	
	public DbQueryHandler(String query) {
		this.query = query;
		connection = null;
		statement = null;
		result = null;
	}
	
	public boolean process(){
		// load the sqlite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try
		{
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			result = statement.executeQuery(query);
			while(result.next())
			{
				if(!processResult(result)){
					return false;
				}
				// read the result set
				//System.out.println("name = " + rs.getString("name"));
				//System.out.println("id = " + rs.getInt("id"));
			}
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
			return false;
		}
		finally
		{
			try
			{
				if(connection != null)
				connection.close();
			}
			catch(SQLException e)
			{
				// connection close failed.
				System.err.println(e);
				return false;
			}
		}
		return true;
	}
	
	protected abstract boolean processResult(ResultSet result) throws SQLException;
}
