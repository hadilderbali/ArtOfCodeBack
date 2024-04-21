package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Role;
import com.artofcode.artofcodebck.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {

    User findByUseridAndRole(Long userid, Role role);

}
