package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import main.Klient;
import manager.KlientManager;

public class KlientManagerTest {
KlientManager klientManager = new KlientManager();
	
	private final static String imie = "Jan";
	private final static String nazwisko = "Kowalski";
	private final static long numertelefonu = 501601701;
	private final static String imie2 = "Adam";
	private final static String nazwisko2 = "Malinowski";
	private final static long numertelefonu2 = 600700800;
	
	
	@Test
	public void checkConnection(){
		assertNotNull(klientManager.getConnection());
	}
	
	@Test
	//DODANIE DO TABELI Y
	public void checkAdding(){
		Klient klient = new Klient(imie, nazwisko, numertelefonu);
		
		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.dodajKlienta(klient));
		
		List<Klient> k = klientManager.pobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		assertEquals(imie, aktualnyklient.getImie());
		assertEquals(nazwisko, aktualnyklient.getNazwisko());
		assertEquals(numertelefonu, aktualnyklient.getNumertelefonu());
	}
	
	@Test
	public void checkUpdate(){
		Klient klient = new Klient(imie, nazwisko, numertelefonu);

		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.dodajKlienta(klient));
		
		List<Klient> k = klientManager.pobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		aktualnyklient.setImie(imie2);
		aktualnyklient.setNazwisko(nazwisko2);
		aktualnyklient.setNumertelefonu(numertelefonu2);
		
		assertEquals(1, klientManager.edytujKlienta(aktualnyklient));
		
		List<Klient> k2 = klientManager.pobierzKlientow();
		Klient aktualnyklient2 = k2.get(0);
		
		assertEquals(imie2, aktualnyklient2.getImie());
		assertEquals(nazwisko2, aktualnyklient2.getNazwisko());
		assertEquals(numertelefonu2, aktualnyklient2.getNumertelefonu());
		assertEquals(aktualnyklient.getId(), aktualnyklient2.getId());
	}
	
	@Test
	public void checkDelete() {
		Klient klient = new Klient(imie, nazwisko, numertelefonu);

		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.dodajKlienta(klient));
		
		List<Klient> k = klientManager.pobierzKlientow();
		Klient aktualnyklient = k.get(0);

		assertEquals(1, klientManager.usunKlienta(aktualnyklient));

	}
}