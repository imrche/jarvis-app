package org.rch.jarvisapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaintenanceController {

    @GetMapping(value="/probe")
    public ResponseEntity<?> liveProbe(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
