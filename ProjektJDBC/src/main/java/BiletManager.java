package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.Bilet;

public class BiletManager {
	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/";
	private String createTableBilet = "CREATE TABLE Bilet(id_bilet int GENERATED BY DEFAULT AS IDENTITY, rodzaj varchar(100), cena double, opis varchar(500))";
	
	private PreparedStatement DodajBilet;
	private PreparedStatement UsunBilet;
	private PreparedStatement UsunBilety;
	private PreparedStatement EdytujBilet;
	private PreparedStatement PobierzBilety;
	private Statement statement;
	
	public BiletManager(){
		try{
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if("Bilet".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
						tableExists = true;
						break;
				}
			}
			
		if(!tableExists){
			statement.executeUpdate(createTableBilet);
		}
		
		DodajBilet = connection.prepareStatement("INSERT INTO Bilet(rodzaj, cena, opis) VALUES (?, ?, ?)"); 
		UsunBilet = connection.prepareStatement("DELETE FROM Bilet where id_bilet = ?");
		UsunBilety = connection.prepareStatement("DELETE FROM Bilet");
		EdytujBilet = connection.prepareStatement("UPDATE Bilet set rodzaj = ?, cena = ?, opis = ? where id_bilet = ?");
		PobierzBilety = connection.prepareStatement("SELECT id_bilet, rodzaj, cena, opis FROM Bilet");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void wyczyscBilet(){
		try{
			UsunBilety.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int DodajBilet(Bilet bilet){
		int licznik = 0;
		try{
			DodajBilet.setString(1, bilet.getRodzaj());
			DodajBilet.setDouble(2, bilet.getCena());
			DodajBilet.setString(3, bilet.getOpis());
			licznik = DodajBilet.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public int UsunBilet(Bilet bilet){
		int licznik = 0;
		try{
			UsunBilet.setInt(1, bilet.getId());
			licznik = UsunBilet.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public int EdytujBilet(Bilet bilet){
		int licznik = 0;
		try{
			EdytujBilet.setString(1, bilet.getRodzaj());
			EdytujBilet.setDouble(2, bilet.getCena());
			EdytujBilet.setString(3, bilet.getOpis());
			EdytujBilet.setInt(4,  bilet.getId());
			licznik = EdytujBilet.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public List<Bilet> PobierzBilety(){
		List<Bilet> bilety = new ArrayList<Bilet>();
		try{
			ResultSet rs = PobierzBilety.executeQuery();
			while(rs.next()){
				Bilet b = new Bilet();
				b.setId(rs.getInt("id_bilet"));
				b.setRodzaj(rs.getString("rodzaj"));
				b.setCena(rs.getDouble("cena"));
				b.setOpis(rs.getString("opis"));
				bilety.add(b);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bilety;
	}
}
