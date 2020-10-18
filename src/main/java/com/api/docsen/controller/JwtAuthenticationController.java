package com.api.docsen.controller;

import com.api.docsen.config.JwtTokenUtil;
import com.api.docsen.request.ErrorResponse;
import com.api.docsen.request.JwtRequest;
import com.api.docsen.request.Response;
import com.api.docsen.request.ResponseJwt;
import com.api.docsen.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/roles", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public List<String> getUserRoles(@RequestParam("username") String username) throws Exception {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return userDetails.getAuthorities().stream()
                .map(u->((GrantedAuthority) u)
                        .getAuthority()).collect(Collectors.toList());
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest authenticationRequest) {
        System.out.println(authenticationRequest.getUsername()+" - "+authenticationRequest.getPassword());
        final UserDetails details = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        if(details != null) {
            Authentication authentication = null;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenUtil.generateToken(details);
                System.out.println(jwt);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                ResponseJwt responseJwt = new ResponseJwt(jwt, userDetails.getUsername(), userDetails.getAuthorities());
                return ResponseEntity.ok(new Response("ok", responseJwt));
            } catch (DisabledException e) {
                return ResponseEntity.ok(new Response("error", new ErrorResponse("USER_DISABLED")));
            } catch (BadCredentialsException e) {
                return ResponseEntity.ok(new Response("error", new ErrorResponse("BAD_CREDENTIALS")));
            }
        }
        return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_USERNAME")));
    }
}
