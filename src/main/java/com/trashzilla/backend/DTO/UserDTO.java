package com.trashzilla.backend.DTO;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String city;
    private String role;
    private String email;
    private String password;

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCity() { return city; }
    public String getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}

