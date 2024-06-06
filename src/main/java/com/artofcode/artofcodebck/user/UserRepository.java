package com.artofcode.artofcodebck.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);
  @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(concat(?1, '%')) AND u.role NOT IN (?2)")
  List<User> findByUsernameStartingWithIgnoreCaseAndRoleNotIn(String usernameStart, ArrayList<Role> roles);

  @Query(value="SELECT * FROM User WHERE  firstname= :firstname and password= :password",nativeQuery = true)
  User login(@Param("firstname")String firstname, @Param("password")String password);
  Optional<User> findByFirstname(String firstname);
  //Optional<User> findByUsername(String username);
  @Query(value = "select count(*) from user;", nativeQuery=true)
  Integer nubusers();
  @Query(value="select * from user where id != :id",nativeQuery = true)
  List<User> userconnect(@Param("id") Integer id);

  User findByIdAndRole(Integer id, Role role);
}
