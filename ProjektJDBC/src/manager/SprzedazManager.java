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
import main.Bilet;

public class SprzedazManager {
	private static Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/";
	private String createTableSprzedaz = "CREATE TABLE Sprzedaz(id_klient int foreign key references Klient(id_klient), id_bilet int foreign key references Bilet(id_bilet))";

	private static PreparedStatement dodajSprzedaz;
	private static PreparedStatement usunSprzedaz;
	private static PreparedStatement usunSprzedaze;
	private static PreparedStatement pobierzSprzedaz;
	private static PreparedStatement edytujSprzedaz;
	private static PreparedStatement pobierzSprzedazPoKliencie;

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
			
			dodajSprzedaz = connection.prepareStatement("INSERT INTO Sprzedaz(id_klient, id_bilet) VALUES (?, ?)");
			edytujSprzedaz = connection.prepareStatement("UPDATE Sprzedaz SET id_bilet = ? WHERE id_klient = ?");
			usunSprzedaze = connection.prepareStatement("DELETE FROM Sprzedaz");
			usunSprzedaz = connection.prepareStatement("DELETE FROM Sprzedaz where id_klient = ? and id_bilet = ?");
			pobierzSprzedaz = connection.prepareStatement("SELECT id_klient, id_bilet FROM Sprzedaz");
			pobierzSprzedazPoKliencie = connection.prepareStatement("SELECT id_klient, id_bilet FROM Sprzedaz WHERE id_klient = ?");
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	//USUNIECIE X Z Y
	public static void wyczyscSprzedaz(){
		try{
			usunSprzedaze.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//PRZYPISANIE X DO Y
	public static int dodajSprzedaz(Sprzedaz s){
		int licznik = 0;
		try {
			dodajSprzedaz.setInt(1, s.getId_klient());
			dodajSprzedaz.setInt(2, s.getId_bilet());
			licznik = dodajSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public int edytujSprzedaz(Sprzedaz s){
		int licznik = 0;
		try{
			edytujSprzedaz.setInt(1, s.getId_klient());
			edytujSprzedaz.setInt(2, s.getId_bilet());
			licznik = edytujSprzedaz.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	
	public static int usunSprzedaz(Sprzedaz s){
		int licznik = 0;
		try{
			usunSprzedaz.setInt(1, s.getId_klient());
			usunSprzedaz.setInt(2, s.getId_bilet());
			licznik = usunSprzedaze.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	//POBRANIE WSZYSTKICH X
	public static List<Sprzedaz> pobierzSprzedaz(){
		List<Sprzedaz> sprzedaz = new ArrayList<Sprzedaz>();
		try{
			ResultSet rs = pobierzSprzedaz.executeQuery();
			
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
	
	//POBRANIE X NALEZACYCH DO Y - POBRANIE BILETOW PO KLIENCIE ZE SPRZEDAZY
	public List<Bilet> pobierzSprzedazPoKliencie(Sprzedaz s) {
		List<Bilet> bilety = new ArrayList<Bilet>();

		try {

			pobierzSprzedazPoKliencie.setInt(1, s.getId_klient());

			ResultSet rs = pobierzSprzedazPoKliencie.executeQuery();

			while (rs.next()) {
				Bilet b = new Bilet();
				b.setId(rs.getInt("id_bilet"));
				b.setRodzaj(rs.getString("rodzaj"));
				b.setCena(rs.getDouble("cena"));
				bilety.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bilety;
	}
}