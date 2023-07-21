package account.handler;

import account.entity.security.Event;
import account.services.EventsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class CustomAccessDeniedHandler403 implements AccessDeniedHandler {
    @Autowired
    private EventsService eventsService;
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        eventsService.accessDenied();
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");

    }
}
