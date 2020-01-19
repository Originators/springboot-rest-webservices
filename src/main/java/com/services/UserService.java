package com.services;

import com.entities.User;
import com.exception.UserExistsException;
import com.exception.UserNotFoundException;
import com.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public User createUser(User user) throws UserExistsException{
        if(repository.findByUserName(user.getUserName()) != null) {
            throw new UserExistsException("user with user name "+ user.getUserName() + " already exists.");
        }

        return repository.save(user);
    }

    public Optional<User> getUserById(long userId) throws UserNotFoundException {
          Optional<User> op = repository.findById(userId);
          if(op.isPresent()) {
              return op;
          }else {
                throw new UserNotFoundException("user with id " + userId + " is not found.");
          }
    }

    public User updateUserById(long userId, User user) throws UserNotFoundException {
        if(repository.findById(userId).isPresent()){
            user.setId(userId);
            return repository.save(user);
        } else {
            throw new UserNotFoundException("user with id " + userId + " is not found.");
        }

    }

    public void deleteUserById(long userId) {
        if(repository.findById(userId).isPresent()){
            repository.deleteById(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user with id " + userId + " is not found.");
        }
    }

    public User getUserByUserName (String userName){
        return repository.findByUserName(userName);
    }
}
