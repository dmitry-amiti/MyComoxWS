package ru.penza.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.penza.forms.UserForm;
import ru.penza.models.User;
import ru.penza.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> getUser(String login){
        return userRepository.findOneByLogin(login);
    }

    public String addUser(UserForm userForm) {
        User user = User.builder()
                .firstName(userForm.getName())
                .lastName(userForm.getLastname())
                .login(userForm.getLogin())
                .hashPassword(passwordEncoder.encode(userForm.getPassword()))
                .role(userForm.getRole())
                .build();

        try {
            userRepository.save(user);
            return "User with login " + user.getLogin() + " added to DB";
        } catch (Exception e) {
            return "0";
        }
    }


    public Integer deleteUserByLogin(String login) {
        return userRepository.deleteUser(login);
    }


    public List<String> getLogins() {
        return userRepository.getUsersLogins();
    }

}
