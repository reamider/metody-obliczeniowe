package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import main.Klient;
import main.Bilet;
import manager.BiletManager;
import manager.KlientManager;
import manager.SprzedazManager;

public class SprzedazManagerTest {
	SprzedazManager sprzedazManager = new SprzedazManager();
	KlientManager klientManager = new KlientManager();
	BiletManager biletManager = new BiletManager();
	
	private final static String imie1 = "Jan";
	private final static String nazwisko1 = "Kowalski";
	private final static String numertelefonu1 = "501601701";
	private final static String rodzaj1 = "ulgowy";
	private final static double cena1 = 10.00;
	private final static String opis1 = "Bilet z 50% znizka";
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void checkConnection(){
		assertNotNull(SprzedazManager.getConnection());
	}
	
	@Test
	public void checkAddKlient(){
		Klient klient = new Klient(imie1, nazwisko1, numertelefonu1);
		
		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.DodajKlienta(klient));
		
		List<Klient> k = klientManager.PobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		assertEquals(imie1, aktualnyklient.getImie());
		assertEquals(nazwisko1, aktualnyklient.getNazwisko());
		assertEquals(numertelefonu1, aktualnyklient.getNumertelefonu());
	}
	
	@Test
	public void checkAddBilet(){
		Bilet bilet = new Bilet(rodzaj1, cena1, opis1);
		
		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.DodajBilet(bilet));
		
		List<Bilet> bilety = biletManager.PobierzBilety();
		Bilet aktualnybilet = bilety.get(0);
		
		assertEquals(rodzaj1, aktualnybilet.getRodzaj());
		assertEquals(cena1, aktualnybilet.getCena(), DELTA);
		assertEquals(opis1, aktualnybilet.getOpis());
	}
	
	@Test
	public void checkAddSprzedaz(){
		Klient klient = new Klient(imie1, nazwisko1, numertelefonu1);
		
		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.DodajKlienta(klient));
		
		List<Klient> k = klientManager.PobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		Bilet bilet = new Bilet(rodzaj1, cena1, opis1);
		
		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.DodajBilet(bilet));
		
		List<Bilet> b = biletManager.PobierzBilety();
		Bilet aktualnybilet = b.get(0);

		assertEquals(1, SprzedazManager.dodajSprzedaz(aktualnyklient, aktualnybilet));
	}
}
