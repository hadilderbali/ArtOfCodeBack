package com.artofcode.artofcodebck.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8089/")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final IUserService userservice;

    @PatchMapping("/pass")
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getUsersSuggested/{userId}")
    public List<User> getSuggestedUsers(@PathVariable int userId) {
        return service.getUsersSuggested(userId);
    }
    @PostMapping("/admin/add")
    User adduser(@RequestBody User user){return userservice.addOrUpdateUser(user);}
    @PutMapping("/admin/update")
    User updateuser(@RequestBody User user){return userservice.addOrUpdateUser(user);}
    @GetMapping("/admin/getall")
    List<User> getall(){return service.ShowAllUsers();}
    @DeleteMapping("/admin/delete/{id}")
    void deleteuser(@PathVariable("id")Integer id){userservice.DeleteUser(id);}
    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable("id")Integer id){
        return service.getUserbyId(id);
    }
    @GetMapping("/getUserByUsername")
    public ResponseEntity<List<User>> getUserByUsername(@RequestParam("start") String start) {
        List<User> users = service.getUsersByUsername(start);
        return ResponseEntity.ok(users);
    }
    @PostMapping("/followUser/{userId}/{followUserId}")
    public List<User> followUser(@PathVariable int userId, @PathVariable int followUserId) {
        return service.followUser(userId, followUserId);
    }

    @PostMapping("/unfollowUser/{userId}/{unfollowUserId}")
    public List<User> unfollowUser(@PathVariable int userId, @PathVariable int unfollowUserId) {
        return service.unfollowUser(userId, unfollowUserId);
    }

    @GetMapping("/getUserFollowingUsers/{userId}")
    public List<User> getUserFollowingUsers(@PathVariable int userId) {
        return service.getUserFollowingUsers(userId);
    }

    @GetMapping("/getUserFollowerUsers/{userId}")
    public List<User> getUserFollowerUsers(@PathVariable int userId) {
        return service.getUserFollowerUsers(userId);
    }
}
