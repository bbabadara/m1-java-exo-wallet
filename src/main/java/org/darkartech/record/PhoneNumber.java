package org.darkartech.record;


public record PhoneNumber(String number) {

    // Regex : +221 suivi de 70, 76, 77 ou 78, puis 7 chiffres
    private static final String SENEGAL_REGEX = "^\\+221(70|76|77|78)\\d{7}$";

    public PhoneNumber {
        if (number == null || !number.matches(SENEGAL_REGEX)) {
            throw new IllegalArgumentException("Numéro invalide. Format attendu : +2217Xxxxxxxx (X ∈ {0,6,7,8})");
        }
    }
}