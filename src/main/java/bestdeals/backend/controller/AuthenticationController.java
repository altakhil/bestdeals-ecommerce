package bestdeals.backend.controller;

import bestdeals.backend.dto.*;
import bestdeals.backend.entities.Role;
import bestdeals.backend.entities.User;
import bestdeals.backend.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest)
    {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest)
    {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/username")
    public ResponseEntity<String> returnUserName(@RequestBody UsernameRequest usernameRequest)
    {
        return ResponseEntity.ok(authenticationService.getUsername(usernameRequest));
    }

    @PostMapping("/roles")
    public ResponseEntity<List<Role>> retrieveRoles(@RequestBody RoleRequest roleRequest)
    {
        return ResponseEntity.ok(authenticationService.retrieveRoles(roleRequest));
    }

    @PostMapping("/id")
    public ResponseEntity<Long> retrieveRoles(@RequestBody IdRequest idRequest)
    {
        return ResponseEntity.ok(authenticationService.retrieveId(idRequest.getToken()));
    }

}
