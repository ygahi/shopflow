package ma.shopflow.commande;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.shopflow.panier.Panier;
import ma.shopflow.panier.Produit;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service de commande")
class ServiceCommandeTest {

    static final Client AMINA = new Client("C-001", "amina@exemple.ma");
    static final Produit SOURIS = new Produit("SOUR-02", "Souris sans fil", new BigDecimal("120.00"));

    @Mock
    StockService stock;
    @Mock
    PaiementGateway paiement;
    @Mock
    CommandeRepository commandes;
    @Mock
    NotificationService notifications;

    @InjectMocks
    ServiceCommande service;

    Panier panier;

    @BeforeEach
    void preparerUnPanier() {
        panier = new Panier();
        panier.ajouter(SOURIS, 2);
    }

    @Test
    @DisplayName("C4 : un paiement accepté donne une commande confirmée")
    void unPaiementAccepteDonneUneCommandeConfirmee() {
        // Arrange : on programme le comportement des doublures
        when(stock.estDisponible(anyString(), anyInt())).thenReturn(true);
        when(paiement.debiter(anyString(), any())).thenReturn(ResultatPaiement.accepte("PAY-789"));
        when(commandes.enregistrer(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Commande commande = service.passerCommande(AMINA, panier);

        // Assert : l'état renvoyé...
        assertThat(commande.statut()).isEqualTo(StatutCommande.CONFIRMEE);
        assertThat(commande.referencePaiement()).isEqualTo("PAY-789");
        assertThat(commande.total()).isEqualByComparingTo("270.00");
        // ... et les interactions avec les dépendances
        verify(stock).reserver("SOUR-02", 2);
        verify(paiement).debiter("C-001", new BigDecimal("270.00"));
        verify(notifications).envoyerConfirmation("amina@exemple.ma", commande);
    }
}
