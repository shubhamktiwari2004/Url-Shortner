    package com.Url_Shortner.SnapURL.Controller;
    
    
    import com.Url_Shortner.SnapURL.DTO.UserResponse;
    import com.Url_Shortner.SnapURL.Entity.User;
    import com.Url_Shortner.SnapURL.Service.JWTService;
    import com.Url_Shortner.SnapURL.Service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;
    
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
    
            UserResponse user = userService.loginUser(email, password);
            System.out.println("here");
            System.out.println(user);
            String token = jwtService.generateToken(user.getEmail());
    
            Map<String, Object> response = new HashMap<>();
    
            response.put("token", token);
            response.put("user", user);
    
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }


        @GetMapping("/test")
        public String test(){
            return "Hello EveryOne";
        }
    }
