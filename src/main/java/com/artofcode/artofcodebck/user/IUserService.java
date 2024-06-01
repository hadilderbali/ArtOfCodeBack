package com.artofcode.artofcodebck.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public User adduser(User user);
    public List<User> ShowAllUsers();
    public User addOrUpdateUser(User user);
    void DeleteUser (Integer id);
    public Optional<User> ShowUser(Integer id);

}
