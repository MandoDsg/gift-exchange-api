package com.mandodsg.gift_exchange_api.model;

import jakarta.persistence.*;

// La anotación @Entity marca esta clase como una entidad JPA (una tabla en la base de datos).
@Entity
public class Participant {

    @Id     // @Id indica que esta es la clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // @GeneratedValue genera automáticamente valores para esta clave.
    private Long id;

    /*
     * @Column define propiedades de la columna en la base de datos.
     * nullable = false: no permite valores nulos.
     * unique = true: asegura que no haya duplicados.
     */
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    // Campo para almacenar el email del destinatario asignado (puede ser nulo inicialmente).
    private String assignedRecipient;

    // Métodos getter y setter para acceder y modificar los valores de los campos.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedRecipient() {
        return assignedRecipient;
    }

    public void setAssignedRecipient(String assignedRecipient) {
        this.assignedRecipient = assignedRecipient;
    }
}