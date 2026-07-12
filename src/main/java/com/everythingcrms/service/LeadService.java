package com.everythingcrms.service;

import org.springframework.stereotype.Service;
import com.everythingcrms.entity.Lead;

import java.util.*;



@Service
public class LeadService {

    private final List<Lead> lead = new ArrayList<>();

    public LeadService(){
        lead.add(new Lead("1","Mahindra","mahindra@gmail.com","9618112357","CONTACTED"));
        lead.add(new Lead("2","Vyshu","vyshu@gmail.com","9876545633","NEW"));

    }

    public List<Lead> getAllLeads(){
        return lead;
    }


    public Optional<Lead> getLeadsById(String id){


        return lead.stream().filter(lead -> lead.getId().equals(id)).findFirst();
    }
    
    public boolean existsById(String id){
        return lead.stream().anyMatch(lead -> lead.getId().equals(id));
    }
    public Lead addLeads(Lead leads){

       lead.add(leads);
       return leads;
    }

    public Optional<Lead> UpdateLeadById(Lead leads,String id){
        Optional<Lead> existingUser = getLeadsById(id);
        if(existingUser.isPresent()){

            Lead lead = existingUser.get();
             lead.setName(leads.getName());
             lead.setEmail(leads.getEmail());
             lead.setPhone(leads.getPhone());
             lead.setStatus(leads.getStatus());

             return Optional.of(lead);

        }
        return Optional.empty();
    }

    public boolean deleteUserById(String id){
        return lead.removeIf(lead -> lead.getId().equals(id));
    }

    public List<Lead> getLeadsByStatus(String status){
        return lead.stream().filter(lead -> lead.getStatus().equalsIgnoreCase(status)).toList();
    }

    public Optional<Lead> getLeadsByEmail(String email){
        return lead.stream().filter(lead -> lead.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Optional<Lead> updateLeadStatus(String id,String status){
        Optional<Lead> existingLead = getLeadsById(id);

        if(existingLead.isPresent()){
            Lead lead = existingLead.get();
            lead.setStatus(status);
            return Optional.of(lead);
        }
        return Optional.empty();
    }

    public long countLeadsByStatus(String status){
        return lead.stream()
                            .filter(lead -> lead.getStatus().equalsIgnoreCase(status))
                            .count();
//count returns long
    }

    public Lead addLeadWithAutoId(Lead newLead){
        String newId = String.valueOf(lead.size() + 1);
        newLead.setId(newId);
        lead.add(newLead);

        return newLead;
    }



    
}
