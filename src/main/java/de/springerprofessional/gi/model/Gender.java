package de.springerprofessional.gi.model;

public enum Gender {

    F("Frau"),
    M("Herr"),
    U("Firma");

    private String value;

    public String getValue() {
        return value;
    }

    Gender(String value) {
        this.value = value;
    }
}