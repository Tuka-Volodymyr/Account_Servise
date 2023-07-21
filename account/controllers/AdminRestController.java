package account.controllers;

import account.entity.admin.ChangeAccess;
import account.entity.admin.ChangeRole;
import account.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    @Autowired
    private AdminService adminService;
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping(value = "/admin/user/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserDetails userDetails, @RequestBody(required=false) ChangeRole changeRole){
        return adminService.changeRole(changeRole);
    }
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping(value = {"/admin/user","/admin/user/{userEmail}"})
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable(required=false) String userEmail){
        return adminService.deleteUser(userEmail);
    }
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/admin/user/", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> infoAllUsers(){
        return adminService.infoAllUsers();
    }
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping(value = "/admin/user/access")
    public ResponseEntity<?> changeAccess(@AuthenticationPrincipal UserDetails userDetails,@RequestBody(required=false) ChangeAccess changeAccess){
        return adminService.changeAccess(changeAccess);
    }

}
