package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.dto.auth.LoginDTO;
import dastrulash.uz.kun.uz.dto.auth.LoginResponseDTO;
import dastrulash.uz.kun.uz.dto.auth.RegistrationDTO;
import dastrulash.uz.kun.uz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 1. Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    // 2. Verify
    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        return ResponseEntity.ok(authService.verify(token));
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }


    // 4. Resend
    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestParam String email) {
        return ResponseEntity.ok(authService.resend(email));
    }
}