package org.darkartech.entity;

import org.darkartech.record.Money;
import org.darkartech.record.PhoneNumber;

import java.util.UUID;


public class Account {
    private final UUID id;
    private final PhoneNumber phoneNumber;
    private Money balance;

    public Account(UUID id, PhoneNumber phoneNumber, Money initialBalance) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.balance = initialBalance;
    }

    public void crediter(Money montant) {
        this.balance = this.balance.add(montant);
    }

    public void debiter(Money montant) {
        if (this.balance.amount().compareTo(montant.amount()) < 0) {
            throw new IllegalStateException("Solde insuffisant. Tentative de " + montant.amount() + " "
                    + montant.currency() + " sur un compte à " + this.balance.amount() + " " + this.balance.currency());
        }
        this.balance = this.balance.subtract(montant);
    }

    public void transfererVers(Account destinataire, Money montant) {
        if (!this.balance.currency().equalsIgnoreCase(montant.currency()) ||
                !destinataire.balance.currency().equalsIgnoreCase(montant.currency())) {
            throw new IllegalArgumentException("Incompatibilité de devise entre l'émetteur, le destinataire et le montant.");
        }

        this.debiter(montant);
        destinataire.crediter(montant);
    }

    public UUID getId() { return id; }
    public PhoneNumber getPhoneNumber() { return phoneNumber; }
    public Money getBalance() { return balance; }
}