package com.services;

import com.entities.User;
import com.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User getUserById(long userId) {
          User user = null;
          Optional<User> op = repository.findById(userId);
          if(op.isPresent()) {
              user = op.get();
          }
          return user;
    }

    public User updateUserById(long userId, User user) {
        user.setId(userId);
        return repository.save(user);
    }

    public void deleteUserById(long userId) {
        if(repository.findById(userId).isPresent()){
            repository.deleteById(userId);
        }
    }

    public User getUserByUserName (String userName){
        return repository.findByUserName(userName);
    }
}
