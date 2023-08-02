package com.example.api2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

//    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
    public UserService() {
//        this.userRepository = userRepository;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(Math.toIntExact(id));
    }
    public boolean isUserExists(Long id) {
        return userRepository.existsById(Math.toIntExact(id));
    }
}
