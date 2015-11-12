package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.Sprzedaz;
import main.Klient;
import main.Bilet;

public class SprzedazManager {
	private static Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/";
	private String createTableSprzedaz = "CREATE TABLE Sprzedaz(id_klient int, id_bilet int)";

	private static PreparedStatement DodajSprzedaz;
	private static PreparedStatement UsunSprzedaz;
	private static PreparedStatement PobierzSprzedaz;
	
	private Statement statement;
	
	public SprzedazManager(){
		try{
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if("Sprzedaz".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
					tableExists = true;
					break;
					
				}
			}
			
			if(!tableExists)
				statement.executeUpdate(createTableSprzedaz);
			
			DodajSprzedaz = connection.prepareStatement("INSERT INTO Sprzedaz(id_klient, id_bilet) VALUES (?, ?)");
			UsunSprzedaz = connection.prepareStatement("DELETE FROM Sprzedaz");
			PobierzSprzedaz = connection.prepareStatement("SELECT id_klient, id_bilet FROM Sprzedaz");
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	public static void wyczyscSprzedaz(){
		try{
			UsunSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static int dodajSprzedaz(Klient k, Bilet b){
		int licznik = 0;
		try {
			DodajSprzedaz.setInt(1, k.getId());
			DodajSprzedaz.setInt(2, b.getId());
			licznik = DodajSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public static List<Sprzedaz> PobierzSprzedaz(){
		List<Sprzedaz> sprzedaz = new ArrayList<Sprzedaz>();
		
		try{
			ResultSet rs = PobierzSprzedaz.executeQuery();
			
			while(rs.next()){
				Sprzedaz s = new Sprzedaz();
				s.setId_klient(rs.getInt("id_klient"));
				s.setId_bilet(rs.getInt("id_bilet"));
				sprzedaz.add(s);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return sprzedaz;
	}
}
