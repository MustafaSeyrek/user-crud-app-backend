package com.seyrek.usercrudapp.service;

import com.seyrek.usercrudapp.exception.UserAlreadyExistException;
import com.seyrek.usercrudapp.exception.UserNotFoundException;
import com.seyrek.usercrudapp.model.User;
import com.seyrek.usercrudapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername.isPresent())
            throw new UserAlreadyExistException("User already exist by username: " + user.getUsername());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUserById(Long id, User user) {
        User oldUser = getUserById(id);
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setUsername(user.getUsername());
        userRepository.save(oldUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found id: "+ id));
    }
}
