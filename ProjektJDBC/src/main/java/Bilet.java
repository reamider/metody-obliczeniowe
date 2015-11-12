package main;

public class Bilet {
	private int id_bilet;
	private String rodzaj;
	private double cena;
	private String opis;
	
	public Bilet() {}
	
	public Bilet(String rodzaj, double cena, String opis){
		super();
		this.rodzaj = rodzaj;
		this.cena = cena;
		this.opis = opis;
	}
	
	public int getId(){
		return id_bilet;
	}
	
	public void setId(int id_bilet){
		this.id_bilet = id_bilet;
	}
	
	public String getRodzaj(){
		return rodzaj;
	}
	
	public void setRodzaj(String rodzaj){
		this.rodzaj = rodzaj;
	}
	
	public double getCena(){
		return cena;
	}
	
	public void setCena(double cena){
		this.cena = cena;
	}
	
	public String getOpis(){
		return opis;
	}
	
	public void setOpis(String opis){
		this.opis = opis;
	}
}
