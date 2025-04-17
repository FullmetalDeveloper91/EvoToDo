package ru.fmd.todo_service.todo_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.fmd.todo_service.todo_service.repository.UserServiceDao;

public class UserDetailServiceImpl  implements UserDetailsService {

    private final UserServiceDao userServiceDao;

    public UserDetailServiceImpl(UserServiceDao userServiceDao) {
        this.userServiceDao = userServiceDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userServiceDao.getUserByLogin(username);
    }
}
