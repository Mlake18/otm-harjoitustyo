package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(kortti.saldo(), 1000);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(500);
        assertEquals("saldo: 15.0", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(500);
        assertEquals("saldo: 5.0", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(1100);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void metodiPalauttaaTrueJosRahaRiittaa() {
        assertTrue(kortti.otaRahaa(500));
    }
    
    @Test
    public void metodiPalauttaaFalseJosRahaEiRiita() {
        assertFalse(kortti.otaRahaa(1100));
    }
}
