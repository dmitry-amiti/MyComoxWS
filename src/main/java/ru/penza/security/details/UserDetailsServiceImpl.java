package ru.penza.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.penza.models.User;
import ru.penza.repositories.UserRepository;

import java.util.Optional;


/**
 * В этом сервисе мы говорим спрингу, как доставать details с данными юзеров из нашей бд.
 * Реализуем сервис по созданию UserDetails - метод поиска находит по логину в базе юзера
 * и возвращает details, закидывая в него юзера
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent()) {
            return new UserDetailsImpl(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
