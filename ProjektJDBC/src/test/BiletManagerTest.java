package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import main.Bilet;
import manager.BiletManager;

public class BiletManagerTest {
BiletManager biletManager = new BiletManager();
	
	private final static String rodzaj = "Ulgowy";
	private final static double cena = 10.00;
	private final static String opis = "Bilet z 50% znizka";
	private final static String rodzaj2 = "Normalny";
	private final static double cena2 = 20.00;
	private final static String opis2 = "Bilet bez znizek";
	private static final double DELTA = 1e-15;
	
	@Test
	public void checkConnection(){
		assertNotNull(biletManager.getConnection());
	}
	
	@Test
	//DODANIE DO TABELI X
	public void checkAdding(){
		Bilet bilet = new Bilet(rodzaj, cena, opis);
		
		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.dodajBilet(bilet));
		
		List<Bilet> b = biletManager.pobierzBilety();
		Bilet aktualnybilet = b.get(0);
		
		assertEquals(rodzaj, aktualnybilet.getRodzaj());
		assertEquals(cena, aktualnybilet.getCena(), DELTA);
		assertEquals(opis, aktualnybilet.getOpis());
	}
	
	@Test
	public void checkUpdate(){
		Bilet bilet = new Bilet(rodzaj, cena, opis);

		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.dodajBilet(bilet));
		
		List<Bilet> b = biletManager.pobierzBilety();
		Bilet aktualnybilet = b.get(0);
		
		aktualnybilet.setRodzaj(rodzaj2);
		aktualnybilet.setCena(cena2);
		aktualnybilet.setOpis(opis2);
		
		assertEquals(1, biletManager.edytujBilet(aktualnybilet));
		
		List<Bilet> b2 = biletManager.pobierzBilety();
		Bilet aktualnybilet2 = b2.get(0);
		
		assertEquals(rodzaj2, aktualnybilet2.getRodzaj());
		assertEquals(cena2, aktualnybilet2.getCena(), DELTA);
		assertEquals(opis2, aktualnybilet2.getOpis());
		assertEquals(aktualnybilet.getId(), aktualnybilet2.getId());
	}
	
	@Test
	public void checkDelete() {
		Bilet bilet = new Bilet(rodzaj, cena, opis);

		biletManager.wyczyscBilet();
		assertEquals(1, biletManager.dodajBilet(bilet));
		
		List<Bilet> b = biletManager.pobierzBilety();
		Bilet aktualnybilet = b.get(0);

		assertEquals(1, biletManager.usunBilet(aktualnybilet));

	}
}