package account.controllers;

import account.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventRestController {
    @Autowired
    private EventsService auditorService;
    @PreAuthorize("hasRole('ROLE_AUDITOR')")
    @GetMapping("/security/events/")
    public ResponseEntity<?> getAllEvents(){
        return auditorService.getAllEvents();
    }
}
