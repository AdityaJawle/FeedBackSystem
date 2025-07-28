package in.sp.main.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import in.sp.main.Entity.User;
import in.sp.main.Repository.UserRepo;
import org.springframework.boot.CommandLineRunner;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public CommandLineRunner createAdminUser(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            if (userRepo.findByUsername(adminUsername) == null) {
                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setCpassword(admin.getPassword());
                admin.setRoles("ROLE_ADMIN");
                userRepo.save(admin);
                System.out.println(" *********  Admin user created in MySQL! *********");
            } else {
                System.out.println(" ********* Admin already exists.   *********");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/register", "/login").permitAll()

                        .requestMatchers("/dashboard", "/edit/**", "/update/**", "/view/**", "/delete/**").authenticated()

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .build();
    }
}
