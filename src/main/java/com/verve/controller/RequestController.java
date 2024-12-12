package com.verve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verve.model.Request;
import com.verve.service.RequestService;

@RestController
@RequestMapping("/api/verve")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/accept")
    public String acceptRequest(@RequestParam Integer id, @RequestParam(required = false) String endpoint) {
        Request request = new Request();
        request.setId(id);
        request.setEndpoint(endpoint);
        return requestService.processRequest(request);
    }
    
    @PostMapping("/post")
    public ResponseEntity<String> acceptPostRequest(@RequestBody Request request) {
        String response = requestService.acceptPostRequest(request);
        return ResponseEntity.ok(response);
    }
}
