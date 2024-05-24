
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*; 
 
public class MsAccessDatabaseConnectionInJava8 {
 	
 	    Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
 		
 	
    public MsAccessDatabaseConnectionInJava8() {
 
        // variables
   
 
        // Step 1: Loading or registering Oracle JDBC driver class
        try {
 
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {
            JOptionPane.showMessageDialog(null,"Problem in loading or "
                    + "registering MS Access JDBC driver");
            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
 
        // Step 2: Opening database connection
        try {
 
            //String msAccDB = "C:/Pharma Inventory/Dclass1.accdb";
            //String dbURL = "jdbc:ucanaccess://" + msAccDB; 
 
        
            connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\AtonPharma\\Inventory\\Dclass1.accdb;jackcessOpener=CryptCodecOpener", "", "jhunbuenz888");
 			//connection = DriverManager.getConnection("jdbc:ucanaccess:////192.168.2.95/New/Dclass1.accdb;jackcessOpener=CryptCodecOpener", "", "pass123");
           
            statement = connection.createStatement();
 
            
            //resultSet = statement.executeQuery("SELECT * FROM PLAYER");
 
           
 
           
            //while(resultSet.next()) {
             //   System.out.println(resultSet.getInt(1) + "\t" + 
             //           resultSet.getString(2) + "\t" + 
            //            resultSet.getString(3) + "\t" +
            //            resultSet.getString(4));
           // }
        }
        catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null,sqlex);
        }
 
    }
    public ResultSet getMySet()
    	{
    		return resultSet;
    	}
    public Statement getMyState()
    	{
    		return statement;
    	}
    public boolean serials()
    	{	boolean ret=false;
    		String sn="";
    		DiskUtils dn =new DiskUtils();
    		String cur = dn.getSerialNumber("C");
    		try{
    			
    			resultSet=statement.executeQuery("SELECT Serial_no FROM Serial");
		    		while(resultSet.next()) 
		    		{ 
		    			
		                	sn=resultSet.getString("Serial_no");
		                	if(sn.equals(cur))return true;
		    		}
		            
    		}catch(Exception e){e.printStackTrace();}
    		
    		
    		
    		return ret;
    	}	
    public void test()
    	{  try{
    			
    			resultSet=statement.executeQuery("SELECT ID FROM Sales_order");
		    			while(resultSet.next()) 
		    			{
		                	System.out.println(resultSet.getString("ID"));
		            	}
    		}catch(Exception e){e.printStackTrace();}
    	}	
    public static void main(String args[])
    	{
    		MsAccessDatabaseConnectionInJava8 ee=new MsAccessDatabaseConnectionInJava8();
    		System.out.println(ee.serials()+"");
    		
    	}		
}