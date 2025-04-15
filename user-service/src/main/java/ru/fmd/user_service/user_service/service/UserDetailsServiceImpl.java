package ru.fmd.user_service.user_service.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmd.user_service.user_service.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optUser = repository.getUserByLogin(username);
        if(optUser.isPresent()){
            var user = optUser.get();

            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return List.of(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return String.format("ROLE_%s", user.getRole().name());
                        }
                    });
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getLogin();
                }
            };
        }else{
            throw new UsernameNotFoundException("User with that login not found");
        }
    }
}
