package com.mandodsg.gift_exchange_api.repository;

import com.mandodsg.gift_exchange_api.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository     // @Repository indica que esta interfaz es un repositorio de Spring Data JPA.
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    // Método personalizado para buscar un participante por su email.
    // Spring Data JPA genera automáticamente la implementación basada en el nombre del método.
    Optional<Participant> findByEmail(String email);
}