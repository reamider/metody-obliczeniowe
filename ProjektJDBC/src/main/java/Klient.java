package main;

public class Klient {
	private int id_klient;
	private String imie;
	private String nazwisko;
	private String numertelefonu;
	
	public Klient() {}
	
	public Klient(String imie, String nazwisko, String numertelefonu){
		super(); 
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.numertelefonu = numertelefonu;
	}
	
	public int getId(){
		return id_klient;
	}
	
	public void setId(int id_klient){
		this.id_klient = id_klient;
	}
	
	public String getImie(){
		return imie;
	}
	
	public void setImie(String imie){
		this.imie = imie;
	}
	
	public String getNazwisko(){
		return nazwisko;
	}
	
	public void setNazwisko(String nazwisko){
		this.nazwisko = nazwisko;
	}
	
	public String getNumertelefonu(){
		return numertelefonu;
	}
	
	public void setNumertelefonu(String numertelefonu){
		this.numertelefonu = numertelefonu;
	}
}
