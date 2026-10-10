package ma.shopflow.panier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals(new BigDecimal("690"), panier.sousTotal());
    }
}
