package com.mandodsg.gift_exchange_api.service;

import com.mandodsg.gift_exchange_api.model.Participant;
import com.mandodsg.gift_exchange_api.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// @Service indica que esta clase contiene la lógica de negocio de la aplicación.
@Service
public class ParticipantService {

    // Inyecta automáticamente el repositorio para interactuar con la base de datos.
    @Autowired
    private ParticipantRepository repository;

    @Autowired
    private EmailService emailService;

    // Método para registrar un participante.
    public Participant registerParticipant(String name, String email) {
        // Verifica si el email ya existe en la base de datos.
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado. Ingrese uno sin registrar.");
        }
        // Crea un nuevo participante.
        Participant participant = new Participant();
        participant.setName(name);
        participant.setEmail(email);
        // Guarda el participante en la base de datos.
        return repository.save(participant);
    }

    // Método para listar todos los participantes registrados.
    public List<Participant> listParticipants() {
        return repository.findAll();    // Recupera todos los registros de la tabla.
    }

    // Método para asignar destinatarios a cada participante.
    public void assignRecipients() {
        // Recupera todos los participantes de la base de datos.
        List<Participant> participants = repository.findAll();
        // Verifica que haya al menos 2 participantes.
        if (participants.size() < 2) {
            throw new IllegalStateException("Se necesitan al menos 2 participantes.");
        }
        // Mezcla aleatoriamente la lista de participantes.
        Collections.shuffle(participants);
        // Asigna destinatarios en un círculo (el último asigna al primero).
        for (int i = 0; i < participants.size(); i++) {
            Participant giver = participants.get(i);    // Participante que regala.
            Participant recipient = participants.get((i + 1 ) % participants.size());   // Destinatario.
            giver.setAssignedRecipient(recipient.getEmail());   // Asigna el email del destinatario.
            repository.save(giver);     // Guarda los cambios en la base de datos.

            // Enviar correo electrónico al participante con su destinatario asignado.
            String subject = "Intercambio de regalos: Tu destinatario";
            String text = "Hola " + giver.getName() + ",\n\n" +
                    "Te ha tocado regalar a: " + recipient.getName() + " (" + recipient.getEmail() + ").\n" +
                    "¡Feliz intercambio de regalos!";
            emailService.sendEmail(giver.getEmail(), subject, text);
        }
    }

    // Método para obtener el destinatario asignado de un participante específico.
    public String getAssignedRecipient(String email) {
        // Busca al participante por su email.
        Participant participant = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));
        // Devuelve el email del destinatario asignado.
        return participant.getAssignedRecipient();
    }
}