package account.controllers;

import account.entity.admin.ChangeRole;
import account.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AdminRestServise {
    @Autowired
    private AdminService adminService;
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")

    @PutMapping(value = "/admin/user/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody(required=false) ChangeRole changeRole){
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied!");
        if(changeRole==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Empty");
        return adminService.changeRole(changeRole);
    }


//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping(value = {"/admin/user","/admin/user/{userEmail}"})
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable(required=false) String userEmail){
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied!");
        if(userEmail==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Empty");
        return adminService.deleteUser(userEmail);
    }
//    @DeleteMapping("/admin/user")
//    public ResponseEntity<?> deleteUser2(@AuthenticationPrincipal UserDetails userDetails){
//        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied!");
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Empty");
//
//    }
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/admin/user/", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> infoAllUsers(@AuthenticationPrincipal UserDetails userDetails){
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied!");
        return adminService.infoAllUsers();
    }

}
