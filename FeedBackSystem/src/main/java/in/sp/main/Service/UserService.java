package in.sp.main.Service;

import in.sp.main.Entity.User;
import in.sp.main.Repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkUser(String username) {
        return userRepo.findByUsername(username) != null;
    }

    public boolean checkPass(String pass, String cpass) {
        return pass.equals(cpass);
    }

    public void registerUser(User user) {
        if (checkUser(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        if (!checkPass(user.getPassword(), user.getCpassword())) {
            throw new RuntimeException("Passwords and conform passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(user.getRoles()))
                .build();
    }
}
