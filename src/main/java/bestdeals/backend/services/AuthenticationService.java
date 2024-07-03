package bestdeals.backend.services;

import bestdeals.backend.dto.*;
import bestdeals.backend.entities.Role;
import bestdeals.backend.entities.User;

import java.util.List;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    String getUsername(UsernameRequest usernameRequest);

    List<Role> retrieveRoles(RoleRequest roleRequest);

    Long retrieveId(String token);
}
