package com.artofcode.artofcodebck.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.artofcode.artofcodebck.profile.dancepref;
import com.artofcode.artofcodebck.profile.musicpref;
import com.artofcode.artofcodebck.profile.profile;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.artofcode.artofcodebck.user.Role;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    EmailConfirmationTokenRepository confirmationTokenRepository;
    EmailService emailService;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }
    public List<User> getUsersByUsername(String start) {
        String startRegex = escapeRegExp(start);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.MANAGER);
        roles.add(Role.ADMIN);


        List<User> startWithUsername =   repository.findByUsernameStartingWithIgnoreCaseAndRoleNotIn(startRegex,
                roles);


        // Not sure about the Java syntax for this part, you may need to concatenate the lists differently

        return startWithUsername;
    }

    private String escapeRegExp(String string) {
        return string.replaceAll("[*]", "\\\\$0");
    }
    public User getUserByEmail(String email) {
        System.out.println("email azedazed" + email);
        try {
            User user = repository.findByEmail(email);
            return user;
        } catch (Exception e) {
            System.out.println("error getting user by email: " + e);
            return null;
        }
    }

    public List<User> followUser(int userId, int followUserId) {
        // Récupérer l'utilisateur qui effectue le suivi
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Récupérer l'utilisateur à suivre
        User followUser = repository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + followUserId));

        // Mettre à jour la liste des utilisateurs suivis par l'utilisateur qui effectue le suivi
        user.getFollowerUsers().add(followUser);

        // Sauvegarder les modifications
        repository.save(user);

        return user.getFollowingUsers();
    }


    public List<User> unfollowUser(int userId, int unfollowUserId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        User unfollowUser = repository.findById(unfollowUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + unfollowUserId));

        user.getFollowerUsers().remove(unfollowUser);
        repository.save(user);

        return user.getFollowerUsers();
    }

    public List<User> getUserFollowingUsers(int userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return user.getFollowingUsers();
    }

    public List<User> getUserFollowerUsers(int userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return user.getFollowerUsers();
    }

    public List<User> getUsersSuggested(int userId) {
    User user = repository.findById(userId)
                          .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    profile userProfile = user.getProfile();
    if (userProfile == null) {
        // Handle the case where the user's profile is null
        throw new RuntimeException("User profile is null for user with id: " + userId);
    }

    dancepref userDancePref = userProfile.getDancepref();
    musicpref userMusicPref = userProfile.getMusicpref();

    List<User> suggestedUsers = new ArrayList<>();

    for (User otherUser : repository.findAll()) {
        if (otherUser.getId() != userId) { // Exclude the user passed to the function
            profile otherUserProfile = otherUser.getProfile();
            if (otherUserProfile != null) {
                dancepref otherUserDancePref = otherUserProfile.getDancepref();
                musicpref otherUserMusicPref = otherUserProfile.getMusicpref();

                if (userDancePref.equals(otherUserDancePref) && userMusicPref.equals(otherUserMusicPref)) {
                    suggestedUsers.add(otherUser);
                }
            }
        }
    }

    return suggestedUsers;
}

    public Optional<User> getUserbyId(Integer id){
        return repository.findById(id);
    }
    @Override
    public User adduser(User user) {
        return null;
    }

    @Override
    public List<User> ShowAllUsers() {
        return repository.findAll();
    }

    @Override
    public User addOrUpdateUser(User user) {
        return repository.save(user);
    }

    @Override
    public void DeleteUser(Integer id) {repository.deleteById(id);

    }



    @Override
    public Optional<User> ShowUser(Integer id) {
        return repository.findById(id);
    }


}

