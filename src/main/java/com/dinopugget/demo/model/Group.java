// this class belongs to the model package (must match folder structure)
package com.dinopugget.demo.model;

// lombok automatically generates boilerplate code (getters, setters, constructors, toString, etc...)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// import JPA annotations (Java Persistence API) used to manage relational database in Java apps
import jakarta.persistence.*;
// use Set to store multiple Event objects without duplicates (like how a set can't have duplicates)
import java.util.Set;

// lombok annotations
@Data // automatically generates getters, setters, toString(), equals(), hashCode() --> (return int hash code to store and retrieve data in HashMap or HashTable)
@NoArgsConstructor // generates public Group() {}, JPA requires a no-argumetn constructor
@RequiredArgsConstructor // generates constructor for all fields marked with @NonNull
@Entity // marks this class as a database entitny
@Table(name = "user_group") 
public class Group {

    @Id // primary key
    @GeneratedValue 
    private Long id; 
    @NonNull
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @ManyToOne(cascade=CascadeType.PERSIST) // many Group objects can belong to one User
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) // One Group has many Event objects.
    private Set<Event> events; // prevent duplicates
}