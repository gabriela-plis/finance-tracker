import com.zaprogramujzycie.finanse.security.authentication.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(value = "Authentication Management")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "User Registration")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDetailsDTO registerDetailsDTO) {
        authenticationService.registerUser(registerDetailsDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @ApiOperation(value = "User Login")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDetailsDTO loginDetailsDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetailsDTO.getEmail(),
                        loginDetailsDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
}
