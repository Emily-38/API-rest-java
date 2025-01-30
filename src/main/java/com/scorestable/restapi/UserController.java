package com.scorestable.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Utilisateur déjà existant.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());  // Hachage du mot de passe avant de l'enregistrer

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole("USER");  // Ajoute des rôles si nécessaire

        userRepository.save(user);  // Enregistrement dans la base de données
        return "Utilisateur enregistré avec succès!";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Essayer d'authentifier l'utilisateur avec AuthenticationManager
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Si l'authentification réussit, générer un JWT
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Générer un token JWT
            String token = jwtUtil.generateToken(String.valueOf(authentication));

            // Retourner le token JWT
            return ResponseEntity.ok("Bearer " + token);  // Prefixe le token avec "Bearer "
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentification échouée");  // Mauvais nom d'utilisateur ou mot de passe
        }
    }
}
