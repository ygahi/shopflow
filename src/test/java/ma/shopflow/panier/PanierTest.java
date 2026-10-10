package ma.shopflow.panier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PanierTest {

    @Test
    void unNouveauPanierEstVide() {
        // Arrange
        Panier panier = new Panier();

        // Act
        int nombre = panier.nombreArticles();

        // Assert
        assertTrue(panier.estVide());
        assertEquals(0, nombre);
    }

    @Test
    void leSousTotalEstLaSommeDesLignes() {
        Panier panier = new Panier();
        panier.ajouter(new Produit("CLAV-01", "Clavier mécanique", new BigDecimal("450.00")), 1);
        panier.ajouter(new Produit("SOUR-02", "Souris sans fil", new BigDecimal("120.00")), 2);

        assertMontant("690.00", panier.sousTotal());
    }

    /** Compare deux montants par leur valeur, sans tenir compte du nombre de décimales. */
    static void assertMontant(String attendu, BigDecimal reel) {
        if (new BigDecimal(attendu).compareTo(reel) != 0) {
            fail("Montant attendu : " + attendu + " DH, obtenu : " + reel + " DH");
        }
    }
}
