package com.openclassroom.safetynetalerts.web.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalerts.service.PersonsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
public class personsController {

    @Autowired
    private PersonsService personsService;
    
    @GetMapping(value = "/communityEmail")
    public List<String> emailListByCity(@RequestParam(value = "city") String city) {
        return personsService.findCityService(city);
    }

    //Temporaire :
    @GetMapping(value = "/person")
    public List<Persons> personsDB() {
        return personsService.findAllService();
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> newPerson(@RequestBody Persons personToSave) {
        Persons personAdded = personsService.savePerson(personToSave);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person")
    public ResponseEntity<Void> updatePerson(@RequestBody Persons newPersonInfo) {
        Persons personUpdated = personsService.savePerson(newPersonInfo);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personUpdated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/person")
    public ResponseEntity<Void> deletePerson(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName ) {
        boolean deleted = personsService.deletePerson(firstName, lastName);

        if (deleted == false)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().build();
    }
}
