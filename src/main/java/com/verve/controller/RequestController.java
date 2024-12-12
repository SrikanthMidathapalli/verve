package com.verve.controller;

import com.verve.model.Request;
import com.verve.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
