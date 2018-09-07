
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    int edullinenHinta;
    int maukasHinta;
          
    @Before
    public void setUp() {
       kassa = new Kassapaate();
       edullinenHinta = 240;
       maukasHinta=400;
    }
     
     @Test
     public void luodunPaatteenTiedotOikein (){
        assertEquals(kassa.kassassaRahaa(), 100000);
     }
     
     @Test
     public void kateisellaMaksettuEdullinenKasvattaaSaldoaOikein() {
         assertEquals(kassa.syoEdullisesti(edullinenHinta + 10), 10);
         assertEquals(kassa.kassassaRahaa(), 100000 + edullinenHinta);
     }
     
     public void tasarahallaMaksettuEdullinenKasitellaanOikein() {
         assertEquals(kassa.syoEdullisesti(edullinenHinta), 0);
         assertEquals(kassa.kassassaRahaa(), 100000 + edullinenHinta);
     }
     
     @Test
     public void kateisellaMaksettuMaukasKasvattaaSaldoaOikein() {
         assertEquals(kassa.syoMaukkaasti(maukasHinta+100), 100);
         assertEquals(kassa.kassassaRahaa(),100000 + maukasHinta);
     }
     
     @Test
     public void tasarahallaMaksettuMaukasKasitellaanOikein() {
         assertEquals(kassa.syoMaukkaasti(maukasHinta), 0);
         assertEquals(kassa.kassassaRahaa(),100000 + maukasHinta);
     }
     
     @Test
     public void myytyjenEdullistenLounaidenMaaraKasvaaOikein() {
         int lkm = kassa.edullisiaLounaitaMyyty();
         kassa.syoEdullisesti(edullinenHinta);
         assertEquals(kassa.edullisiaLounaitaMyyty(),lkm + 1);
     }
     
     @Test 
     public void myytyjenMaukkaidenLounaidenMaaraKasvaaOikein() {
         int lkm = kassa.maukkaitaLounaitaMyyty();
         kassa.syoMaukkaasti(maukasHinta);
         assertEquals(kassa.maukkaitaLounaitaMyyty(), lkm + 1);         
     }
     
     @Test
     public void maksuEiRiittavaEdulliseenLounaaseen() {
         int maksu = edullinenHinta - 100;
         assertEquals(kassa.syoEdullisesti(maksu), maksu);
         assertEquals(kassa.kassassaRahaa(), 100000);
     }
     
     @Test
     public void maksuEiRiitaMaukkaaseenLounaaseen() {
         int maksu = maukasHinta - 100;
         assertEquals(kassa.syoMaukkaasti(maksu), maksu);
         assertEquals(kassa.kassassaRahaa(), 100000);
     }
     
     @Test
     public void kortillaRahaaEdulliseen() {
         Maksukortti kortti = new Maksukortti(edullinenHinta);
         assertTrue(kassa.syoEdullisesti(kortti));
         assertEquals(kortti.saldo(), 0);         
     }
     
     @Test
     public void kortillaRahaaMaukkaaseen() {
         Maksukortti kortti = new Maksukortti(maukasHinta);
         assertTrue(kassa.syoMaukkaasti(kortti));
         assertEquals(kortti.saldo(),0);
     }
     
     @Test
     public void josKortillaRahaaEdullistenMaaraKasvaa() {
         int lkm = kassa.edullisiaLounaitaMyyty();
         Maksukortti kortti= new Maksukortti(edullinenHinta);
         kassa.syoEdullisesti(kortti);
         assertEquals(kassa.edullisiaLounaitaMyyty(), lkm + 1);         
     }   
     
     @Test
     public void josKortillaRahaaMaukkaidenMaaraKasvaa() {
         int lkm = kassa.maukkaitaLounaitaMyyty();
         Maksukortti kortti = new Maksukortti(maukasHinta);
         kassa.syoMaukkaasti(kortti);
         assertEquals(kassa.maukkaitaLounaitaMyyty(), lkm + 1);
     }
     
     @Test
     public void josKortillaEiEdulliseenTarpeeksiRahaa() {
         int lkm = kassa.edullisiaLounaitaMyyty();
         Maksukortti kortti = new Maksukortti(edullinenHinta - 100);
         assertFalse(kassa.syoEdullisesti(kortti));
         assertEquals(kassa.edullisiaLounaitaMyyty(),lkm);
         assertEquals(kortti.saldo(), edullinenHinta - 100);         
     }
     
     @Test
     public void josKortillaEiMaukkaaseenTarpeeksiRahaa() {
         int lkm = kassa.edullisiaLounaitaMyyty();
         Maksukortti kortti = new Maksukortti(maukasHinta - 100);
         assertFalse(kassa.syoMaukkaasti(kortti));
         assertEquals(kassa.maukkaitaLounaitaMyyty(), lkm);
         assertEquals(kortti.saldo(), maukasHinta - 100);
     }
     
     @Test
     public void kassanRahamaaraEiMuutuKunOstetaanKortilla() {        
         int lkm = kassa.edullisiaLounaitaMyyty();
         kassa.syoEdullisesti(new Maksukortti(edullinenHinta));
         assertEquals(kassa.edullisiaLounaitaMyyty(), lkm + 1);
         assertEquals(kassa.kassassaRahaa(), 100000);
     }
     
     @Test 
     public void ladattaessaKortilleRahaaSaldotKasvavatOikein() {
         Maksukortti kortti = new Maksukortti(500);
         kassa.lataaRahaaKortille(kortti, 500);
         assertEquals(kortti.saldo(), 1000);
         assertEquals(kassa.kassassaRahaa(),100500);         
     }
     
     @Test
     public void negatiivinenSummaEiMuutaKortinSaldoa() {
         Maksukortti kortti= new Maksukortti(500);
         kassa.lataaRahaaKortille(kortti, -500);
         assertEquals(kortti.saldo(), 500);
         assertEquals(kassa.kassassaRahaa(), 100000);
     }
}
