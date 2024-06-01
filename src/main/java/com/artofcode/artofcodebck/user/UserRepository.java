package com.artofcode.artofcodebck.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);
  @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(concat(?1, '%')) AND u.role NOT IN (?2)")
  List<User> findByUsernameStartingWithIgnoreCaseAndRoleNotIn(String usernameStart, ArrayList<Role> roles);

}
