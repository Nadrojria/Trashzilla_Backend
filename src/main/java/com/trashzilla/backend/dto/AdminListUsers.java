package com.trashzilla.backend.dto;

public class AdminListUsers {
    private final Long id;
    private String firstName;
    private String lastName;
    private String city;

    public AdminListUsers(Long id, String firstName, String lastName, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}

