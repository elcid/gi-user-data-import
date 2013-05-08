package de.springerprofessional.gi.model;

import java.util.LinkedList;
import java.util.List;

public enum User {

    MITGLIEDNR("MITGLIEDNR"),
    ANREDE("ANREDE"),
    TITEL("TITEL"),
    VORNAME("VORNAME"),
    NAME("NAME"),
    EMAIL("EMAIL"),
    USERNAME("USERNAME"),
    MATERIAL_NUMBER("Materialnummer"),
    BEGIN("Beginn"),
    END("Ende"),
    USER_STATUS("Userstatus"),
    COMPANY("FIRMA"),
    STREET("STRASSE"),
    HOUSE_NUMBER("HAUSNUMMER"),
    PLZ("PLZ"),
    CITY("ORT"),
    LAND("LAND"),
    PASSWORD("PASSWORT"),
    SPECIALTY_CODE("specialtyCode"),
    ACCEPTAGB("acceptAgb");

    private String value;

    public String getValue() {
        return value;
    }

    User(String value) {
        this.value = value;
    }

    protected static List<User> newZSVariables = new LinkedList<User>();
    protected static List<User> newSubscriberVariables = new LinkedList<User>();

    static {
        newZSVariables.add(MITGLIEDNR);
        newZSVariables.add(ANREDE);
        newZSVariables.add(TITEL);
        newZSVariables.add(VORNAME);
        newZSVariables.add(NAME);
        newZSVariables.add(EMAIL);
        newZSVariables.add(USERNAME);
        newZSVariables.add(MATERIAL_NUMBER);
        newZSVariables.add(BEGIN);
        newZSVariables.add(END);
        newZSVariables.add(USER_STATUS);

        newSubscriberVariables.add(MITGLIEDNR);
        newSubscriberVariables.add(ANREDE);
        newSubscriberVariables.add(TITEL);
        newSubscriberVariables.add(VORNAME);
        newSubscriberVariables.add(NAME);
        newSubscriberVariables.add(COMPANY);
        newSubscriberVariables.add(STREET);
        newSubscriberVariables.add(HOUSE_NUMBER);
        newSubscriberVariables.add(PLZ);
        newSubscriberVariables.add(CITY);
        newSubscriberVariables.add(LAND);
        newSubscriberVariables.add(EMAIL);
        newSubscriberVariables.add(USERNAME);
        newSubscriberVariables.add(PASSWORD);
        newSubscriberVariables.add(SPECIALTY_CODE);
        newSubscriberVariables.add(ACCEPTAGB);
        newSubscriberVariables.add(USER_STATUS);

    }
}