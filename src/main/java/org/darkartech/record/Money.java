package org.darkartech.record;

import java.math.BigDecimal;


public record Money(BigDecimal amount, String currency) {

    public Money {
        if (amount == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Le montant et la devise ne peuvent pas être nuls ou vides.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
    }

    public Money add(Money other) {
        if (!this.currency.equalsIgnoreCase(other.currency)) {
            throw new IllegalArgumentException("Opération impossible : devises incompatibles ("
                    + this.currency + " vs " + other.currency + ").");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equalsIgnoreCase(other.currency)) {
            throw new IllegalArgumentException("Opération impossible : devises incompatibles.");
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }
}
