package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.database.model.User;
import root.database.repo.UserRepository;
import root.dto.UserLoginRequest;
import root.dto.UserRegisterRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            User user = new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setPasswordHash(userRegisterRequest.getPassword());
            user.setRole(userRegisterRequest.getRole());
            user.setEmail(userRegisterRequest.getEmail());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User loginUser(UserLoginRequest userLoginRequest) {
        User user = findByEmail(userLoginRequest.getEmail());
        if (user != null && user.getPasswordHash().equals(userLoginRequest.getPassword())) {
            return user;
        }
        return null;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
