    package com.Url_Shortner.SnapURL.Controller;
    
    
    import com.Url_Shortner.SnapURL.DTO.UserResponse;
    import com.Url_Shortner.SnapURL.Entity.User;
    import com.Url_Shortner.SnapURL.Service.JWTService;
    import com.Url_Shortner.SnapURL.Service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    
    import java.util.HashMap;
    import java.util.Map;
    
    @RestController
    public class AuthController {
    
        @Autowired
        private UserService userService;

        @Autowired
        private JWTService jwtService;



        @PostMapping("/login")
        public ResponseEntity<?> login(
                @RequestParam String email,
                @RequestParam String password) {

            try {
                UserResponse user = userService.loginUser(email, password);

                String token = jwtService.generateToken(user.getEmail());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", user);

                return ResponseEntity.ok(response);

            } catch (BadCredentialsException | UsernameNotFoundException e) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password"));
            }
        }


        @GetMapping("/test")
        public String test(){
            return "Hello EveryOne";
        }
    }
