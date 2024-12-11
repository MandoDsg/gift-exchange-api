package com.mandodsg.gift_exchange_api.controller;

import com.mandodsg.gift_exchange_api.model.Participant;
import com.mandodsg.gift_exchange_api.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     // @RestController marca esta clase como un controlador RESTful.
@RequestMapping("/api/participants")    // Define la URL base para todos los endpoints de este controlador.
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    // Endpoint para registrar un nuevo participante.
    @PostMapping
    public ResponseEntity<Participant> registerParticipant(@RequestBody Participant participant) {
        // Llama al servicio para registrar el participante.
        Participant createdParticipant = participantService.registerParticipant(
                participant.getName(), participant.getEmail()
        );
        // Retorna una respuesta con el objeto creado y un código HTTP 201.
        return ResponseEntity.status(201).body(createdParticipant);
    }

    @GetMapping
    public ResponseEntity<List<Participant>> listParticipants() {
        // Llama al servicio para obtener la lista de participantes.
        List<Participant> participants = participantService.listParticipants();
        // Retorna un código HTTP 200 con la lista.
        return ResponseEntity.ok(participants);
    }

    // Endpoint para asignar destinatarios a los participantes.
    @PostMapping("/assign")
    public ResponseEntity<String> assignRecipients() {
        // Asigna los destinatarios.
        participantService.assignRecipients();
        // Retorna un mensaje de éxito.
        return ResponseEntity.ok("Destinatarios asignados y se han enviado los correos correctamente.");
    }

    // Endpoint para obtener el destinatario asignado a un participante.
    @GetMapping("/{email}/recipient")
    public ResponseEntity<String> getAssignedRecipient(@PathVariable String email) {
        // Llama al servicio para obtener el destinatario asignado.
        String recipient = participantService.getAssignedRecipient(email);
        // Retorna el destinatario asignado.
        return ResponseEntity.ok(recipient);
    }
}