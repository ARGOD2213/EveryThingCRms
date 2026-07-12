package com.everythingcrms.service;

import com.everythingcrms.entity.Lead;
import org.springframework.stereotype.Service;

import java.util.*;

// @Service tells Spring this class contains business logic.
// Spring creates one LeadService object and injects it into LeadController.
@Service
public class LeadService {

    // This list acts like a temporary in-memory database for practice.
    // Data will reset whenever the Spring Boot app restarts.
    private final List<Lead> lead = new ArrayList<>();

    // Constructor runs once when Spring creates LeadService.
    // We add dummy data here so GET APIs have records to return immediately.
    public LeadService() {
        lead.add(new Lead("1", "Mahindra", "mahindra@gmail.com", "9618112357", "CONTACTED"));
        lead.add(new Lead("2", "Vyshu", "vyshu@gmail.com", "9876545633", "NEW"));
    }

    // Returns all leads from the in-memory list.
    public List<Lead> getAllLeads() {
        return lead;
    }

    // Finds one lead by id.
    // Optional is used because the id may exist or may not exist.
    public Optional<Lead> getLeadsById(String id) {
        return lead.stream()
                .filter(lead -> lead.getId().equals(id))
                .findFirst();
    }

    // Returns true if any lead already has this id.
    // Useful before creating a manual-id lead.
    public boolean existsById(String id) {
        return lead.stream()
                .anyMatch(lead -> lead.getId().equals(id));
    }

    // Adds a new lead to the list and returns the saved lead.
    public Lead addLeads(Lead leads) {
        lead.add(leads);
        return leads;
    }

    // Updates an existing lead using the id from the URL.
    // Returns Optional<Lead> so the controller can return 200 if found, or 404 if missing.
    public Optional<Lead> UpdateLeadById(Lead leads, String id) {
        Optional<Lead> existingUser = getLeadsById(id);

        if (existingUser.isPresent()) {
            // existingUser.get() gives the real Lead object from inside Optional.
            Lead lead = existingUser.get();

            // We update fields one by one from the request body object.
            lead.setName(leads.getName());
            lead.setEmail(leads.getEmail());
            lead.setPhone(leads.getPhone());
            lead.setStatus(leads.getStatus());

            // Optional.of(lead) means update succeeded and we are returning the updated object.
            return Optional.of(lead);
        }

        // Optional.empty() means no lead was found for this id.
        return Optional.empty();
    }

    // Deletes lead by id.
    // removeIf returns true if something was removed, false if nothing matched.
    public boolean deleteUserById(String id) {
        return lead.removeIf(lead -> lead.getId().equals(id));
    }

    // Filters leads by status.
    // Returns List because many leads can have the same status.
    public List<Lead> getLeadsByStatus(String status) {
        return lead.stream()
                .filter(lead -> lead.getStatus().equalsIgnoreCase(status))
                .toList();
    }

    // Finds one lead by email.
    // Optional is used because email may match one lead or no lead.
    public Optional<Lead> getLeadsByEmail(String email) {
        return lead.stream()
                .filter(lead -> lead.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Updates only the status field, so this supports the PATCH endpoint.
    public Optional<Lead> updateLeadStatus(String id, String status) {
        Optional<Lead> existingLead = getLeadsById(id);

        if (existingLead.isPresent()) {
            Lead lead = existingLead.get();
            lead.setStatus(status);
            return Optional.of(lead);
        }
        return Optional.empty();
    }

    // Counts how many leads have the given status.
    // count() returns long, so this method also returns long.
    public long countLeadsByStatus(String status) {
        return lead.stream()
                .filter(lead -> lead.getStatus().equalsIgnoreCase(status))
                .count();
    }

    // Creates an id automatically for practice.
    // This is okay for learning; later a real database will generate ids.
    public Lead addLeadWithAutoId(Lead newLead) {
        String newId = String.valueOf(lead.size() + 1);

        // Set generated id before saving the lead.
        newLead.setId(newId);
        lead.add(newLead);

        return newLead;
    }

    // Basic manual validation.
    // Returns true if required fields are null or blank.
    public boolean isInvalidLead(Lead leadData) {
        return leadData.getName() == null || leadData.getName().isBlank()
                || leadData.getEmail() == null || leadData.getEmail().isBlank()
                || leadData.getPhone() == null || leadData.getPhone().isBlank()
                || leadData.getStatus() == null || leadData.getStatus().isBlank();
    }

    // Checks duplicate email before creating a new lead.
    // equalsIgnoreCase makes RAHUL@gmail.com and rahul@gmail.com count as same email.
    public boolean existsByEmail(String email) {
        return lead.stream()
                .anyMatch(lead -> lead.getEmail().equalsIgnoreCase(email));
    }
}
