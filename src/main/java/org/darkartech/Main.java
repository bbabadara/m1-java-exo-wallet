package org.darkartech;

import java.math.BigDecimal;
import java.util.UUID;
import org.darkartech.record.Money;
import org.darkartech.record.PhoneNumber;
import org.darkartech.entity.Account;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ATELIER DAKAR-TECH WALLET (DDD) ===\n");

        //  Création valide
        PhoneNumber phone1 = new PhoneNumber("+221771234567");
        Money soldeInitial = new Money(new BigDecimal("50000"), "XOF");
        Account compteA = new Account(UUID.randomUUID(), phone1, soldeInitial);
        System.out.println(" Compte A créé | Solde: " + compteA.getBalance().amount() + " " + compteA.getBalance().currency());

        //  TEST 1 : Montant négatif
        System.out.println("\n Test 1 : Création d'un montant négatif...");
        try {
            new Money(new BigDecimal("-1000"), "XOF");
            System.out.println("ÉCHEC : Validation non appliquée !");
        } catch (IllegalArgumentException e) {
            System.out.println(" SUCCÈS : " + e.getMessage());
        }

        //  TEST 2 : Transfert multi-devises
        System.out.println("\nTest 2 : Transfert avec devises différentes...");
        try {
            PhoneNumber phone2 = new PhoneNumber("+221789876543");
            Account compteB = new Account(UUID.randomUUID(), phone2, new Money(new BigDecimal("10000"), "XOF"));
            Money euros = new Money(new BigDecimal("50"), "EUR");
            compteA.transfererVers(compteB, euros);
            System.out.println(" ÉCHEC : Devises mélangées acceptées !");
        } catch (IllegalArgumentException e) {
            System.out.println(" SUCCÈS : " + e.getMessage());
        }

        //  TEST 3 : Dépassement de solde
        System.out.println("\n Test 3 : Débit supérieur au solde...");
        try {
            compteA.debiter(new Money(new BigDecimal("999999"), "XOF"));
            System.out.println(" ÉCHEC : Solde dépassé !");
        } catch (IllegalStateException e) {
            System.out.println(" SUCCÈS : " + e.getMessage());
        }

        // BONUS : Transfert réussi pour valider le flux positif
        System.out.println("\n Test Bonus : Transfert XOF valide...");
        try {
            PhoneNumber phoneB = new PhoneNumber("+221705551122");
            Account compteB = new Account(UUID.randomUUID(), phoneB, new Money(new BigDecimal("5000"), "XOF"));
            Money montant = new Money(new BigDecimal("2000"), "XOF");
            compteA.transfererVers(compteB, montant);
            System.out.println(" SUCCÈS | Solde A: " + compteA.getBalance().amount()
                    + " | Solde B: " + compteB.getBalance().amount());
        } catch (Exception e) {
            System.out.println(" ÉCHEC : " + e.getMessage());
        }
    }
}