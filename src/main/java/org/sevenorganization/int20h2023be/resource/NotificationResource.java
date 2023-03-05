package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.Message;
import org.sevenorganization.int20h2023be.security.service.UserService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class NotificationResource {

    private final UserService userService;
    @GetMapping("/notifications")
    public ResponseEntity<List<Message>> getNotifications(Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            return new ResponseEntity<>(user.getInbox(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

}
