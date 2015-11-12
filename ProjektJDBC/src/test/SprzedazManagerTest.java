package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import main.Klient;
import main.Bilet;
import main.Sprzedaz;
import manager.BiletManager;
import manager.KlientManager;
import manager.SprzedazManager;

public class SprzedazManagerTest {
	SprzedazManager sprzedazManager = new SprzedazManager();
	KlientManager klientManager = new KlientManager();
	BiletManager biletManager = new BiletManager();
	
	private final static int id_klient = 1;
	private final static int id_bilet = 1;
	private final static String imie1 = "Jan";
	private final static String nazwisko1 = "Kowalski";
	private final static long numertelefonu1 = 501601701;
	private final static String rodzaj1 = "ulgowy";
	private final static double cena1 = 10.00;
	private final static String opis1 = "Bilet z 50% znizka";
	private static final double DELTA = 1e-15;

	@Test
	public void checkConnection(){
		assertNotNull(SprzedazManager.getConnection());
	}
	
	@Test
	//DODANIE DO TABELI X + POBRANIE WSZYSTKICH KLIENTOW
	public void checkAddKlient(){
		Klient klient = new Klient(imie1, nazwisko1, numertelefonu1);
		
		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.dodajKlienta(klient));
		
		List<Klient> k = klientManager.pobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		assertEquals(imie1, aktualnyklient.getImie());
		assertEquals(nazwisko1, aktualnyklient.getNazwisko());
		assertEquals(numertelefonu1, aktualnyklient.getNumertelefonu());
	}
	
	@Test
	//DODANIE DO TABELI Y + POBRANIE WSZYSTKICH BILETOW
	public void checkAddBilet(){
		Bilet bilet = new Bilet(rodzaj1, cena1, opis1);
		
		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.dodajBilet(bilet));
		
		List<Bilet> bilety = biletManager.pobierzBilety();
		Bilet aktualnybilet = bilety.get(0);
		
		assertEquals(rodzaj1, aktualnybilet.getRodzaj());
		assertEquals(cena1, aktualnybilet.getCena(), DELTA);
		assertEquals(opis1, aktualnybilet.getOpis());
	}
	
	@Test
	//PRZYPISANIE X DO Y
	public void checkAddSprzedaz(){
		Sprzedaz sprzedaz = new Sprzedaz(id_klient, id_bilet);
		
		SprzedazManager.wyczyscSprzedaz();
		assertEquals(1, SprzedazManager.dodajSprzedaz(sprzedaz));
	}
	
	@Test
	//POBRANIE WSZYSTKICH SPRZEDAZY
	public void checkDeleteSprzedaz(){
		Sprzedaz sprzedaz = new Sprzedaz(id_klient, id_bilet);
		SprzedazManager.wyczyscSprzedaz();
		assertEquals(1, SprzedazManager.dodajSprzedaz(sprzedaz));
		
		List<Sprzedaz> sprzedaze = SprzedazManager.pobierzSprzedaz();
		Sprzedaz aktualnasprzedaz = sprzedaze.get(0);
		
		assertEquals(1, SprzedazManager.usunSprzedaz(aktualnasprzedaz));
		
		List<Sprzedaz> sprzedaze2 = SprzedazManager.pobierzSprzedaz();
		assertEquals(0, sprzedaze2.size());
	}
}
