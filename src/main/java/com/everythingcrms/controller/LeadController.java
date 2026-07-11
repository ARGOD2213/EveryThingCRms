package com.everythingcrms.controller;
import com.everythingcrms.entity.Lead;
import com.everythingcrms.service.LeadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
@RestController
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService){
        this.leadService = leadService;
    }

    @GetMapping("/leads")
    public List<Lead> getLeads(){
        return leadService.getAllLeads();
    }

    @GetMapping("/leads/{id}")

    public ResponseEntity<Lead> getLeadById(@PathVariable String id){

        return leadService.getLeadsById(id).map(ResponseEntity :: ok)
                                         .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("leads")
    public ResponseEntity<Lead> addLeads(@RequestBody Lead leads){

        if(leadService.existsById(leads.getId())){
            return ResponseEntity.status(409).build();
        }

        Lead savedUser = leadService.addLeads(leads);
        return ResponseEntity.status(200).body(savedUser);
    }


    @PutMapping("leads/{id}")
    public ResponseEntity<Lead> UpdateUserById(@RequestBody Lead leads,@PathVariable String id){
       Optional<Lead> updatedLead = leadService.UpdateLeadById(leads,id);

       if(updatedLead.isPresent()){
        return ResponseEntity.ok(updatedLead.get());
       }
       return ResponseEntity.notFound().build();
    }

}
