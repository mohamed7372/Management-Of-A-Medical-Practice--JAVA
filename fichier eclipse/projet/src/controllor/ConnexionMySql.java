package controllor;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnexionMySql {

	Connection con = null;
	
	public static Connection ConnexionDb() {
		try {
			
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			Connection con = DriverManager.getConnection ("jdbc:mysql://localhost/gestioncabinet", "root", "");
			
			return con;
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null,ex);
			return null;
		}
	}
	public static void disconnect(PreparedStatement stat,ResultSet rs,Connection cn) throws RemoteException {
        try{
            if(rs != null) rs.close();
        }
        catch(SQLException sqlEx){
            System.out.println("Error: disconnect");
        }   

        try{
           if(stat != null) stat.close();
        }
        catch(SQLException sqlEx){
            System.out.println("Error: disconnect");
        }   

        try{
            if(cn != null) cn.close();
        }
        catch(SQLException sqlEx){
            System.out.println("Error: disconnect");
        }   
  }
}
