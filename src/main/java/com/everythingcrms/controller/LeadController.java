package com.everythingcrms.controller;

import com.everythingcrms.entity.Lead;
import com.everythingcrms.service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// @RestController tells Spring this class handles HTTP API requests.
// It also converts Java return values like Lead, List, and Map into JSON responses.
@RestController
public class LeadController {

    // Controller should not store business logic directly.
    // It depends on LeadService, and LeadService handles the actual lead operations.
    private final LeadService leadService;

    // Constructor injection: Spring automatically passes the LeadService object here.
    // Because the field is final, it must be assigned once in the constructor.
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    // GET /leads
    // Returns all leads. If there are no leads, Spring returns [] with 200 OK.
    @GetMapping("/leads")
    public List<Lead> getLeads() {
        return leadService.getAllLeads();
    }

    // GET /leads/{id}
    // @PathVariable reads the id from the URL, for example /leads/1.
    // ResponseEntity is used because the response can be 200 OK or 404 Not Found.
    @GetMapping("/leads/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable String id) {
        // getLeadsById returns Optional because the lead may exist or may not exist.
        return leadService.getLeadsById(id)
                .map(ResponseEntity::ok) // If Optional has a Lead, return 200 OK with body.
                .orElse(ResponseEntity.notFound().build()); // If Optional is empty, return 404.
    }

    // POST /leads
    // @RequestBody converts JSON from Postman into a Lead object.
    // This is the manual-id create endpoint, where the client sends the id.
    @PostMapping("/leads")
    public ResponseEntity<Lead> addLeads(@RequestBody Lead leads) {
        // 409 Conflict means this id already exists, so creating another lead conflicts.
        if (leadService.existsById(leads.getId())) {
            return ResponseEntity.status(409).build();
        }

        Lead savedUser = leadService.addLeads(leads);
        return ResponseEntity.status(200).body(savedUser);
    }

    // PUT /leads/{id}
    // PUT is used for full update of an existing lead.
    // id comes from the URL, and the updated field values come from the JSON body.
    @PutMapping("/leads/{id}")
    public ResponseEntity<Lead> UpdateUserById(@RequestBody Lead leads, @PathVariable String id) {
        Optional<Lead> updatedLead = leadService.UpdateLeadById(leads, id);

        if (updatedLead.isPresent()) {
            return ResponseEntity.ok(updatedLead.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /leads/{id}
    // Deletes one lead by id. Successful delete returns 204 No Content.
    @DeleteMapping("/leads/{id}")
    public ResponseEntity<Lead> DeleteLeadById(@PathVariable String id) {
        boolean delete = leadService.deleteUserById(id);
        if (delete) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /leads/status/{status}
    // Returns a list because many leads can have the same status.
    // Empty result is not an error; Spring returns [] with 200 OK.
    @GetMapping("/leads/status/{status}")
    public List<Lead> getLeadsByStatus(@PathVariable String status) {
        return leadService.getLeadsByStatus(status);
    }

    // GET /leads/search?email=test@gmail.com
    // @RequestParam reads values after ? in the URL.
    // Email search returns Optional because one email should identify one lead.
    @GetMapping("/leads/search")
    public ResponseEntity<Lead> getLeadByEmail(@RequestParam String email) {
        Optional<Lead> foundLead = leadService.getLeadsByEmail(email);

        if (foundLead.isPresent()) {
            return ResponseEntity.ok(foundLead.get());
        }

        return ResponseEntity.notFound().build();
    }

    // PATCH /leads/{id}/status?status=CONTACTED
    // PATCH is used when updating only one or a few fields, not the whole object.
    @PatchMapping("/leads/{id}/status")
    public ResponseEntity<Lead> updateLeadStatus(@PathVariable String id, @RequestParam String status) {
        Optional<Lead> updatedLead = leadService.updateLeadStatus(id, status);

        if (updatedLead.isPresent()) {
            return ResponseEntity.ok(updatedLead.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET /leads/status/{status}/count
    // Returns only a number, so the return type is long.
    @GetMapping("/leads/status/{status}/count")
    public long countLeadsByStatus(@PathVariable String status) {
        return leadService.countLeadsByStatus(status);
    }

    // POST /leads/auto
    // This endpoint creates a lead without asking the user to send an id.
    // ResponseEntity<?> is used because success returns Lead, but errors return Map JSON.
    @PostMapping("/leads/auto")
    public ResponseEntity<?> addnewLeadwithAutoId(@RequestBody Lead lead) {

        // 400 Bad Request means the client sent missing or invalid data.
        if (leadService.isInvalidLead(lead)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid Lead Data"));
        }

        // 409 Conflict means the request is valid, but it conflicts with existing data.
        if (leadService.existsByEmail(lead.getEmail())) {
            return ResponseEntity.status(409).body(Map.of("message", "Email Already Exists"));
        }

        Lead savedLead = leadService.addLeadWithAutoId(lead);
        return ResponseEntity.status(201).body(savedLead);
    }

}
