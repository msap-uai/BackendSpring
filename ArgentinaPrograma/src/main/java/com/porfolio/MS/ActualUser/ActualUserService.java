package com.porfolio.MS.ActualUser;

import com.porfolio.MS.Security.CustomUserDetailsService;
import com.porfolio.MS.User.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ActualUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SessionRegistry sessionRegistry;//usuarios loggeados


    public String deleteActualUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        User saved = userRepository.findByEmail(userName);
        userProfileRepository.delete(userProfileRepository.getReferenceById(saved.getId()));
        userRepository.delete(userRepository.findByEmail(userName));
        return "El usuario "+ userName + " fue eliminado.";
    }

    public void changeActualUser(@NotNull User changeduser) {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userName = ((UserDetails) principal).getUsername();

        //asigna el id al usuario del usuario actual
        User savedUser = userRepository.findByEmail(userName);
        changeduser.setId(savedUser.getId());
        //asigna el rol al usuario modificado
        String roleold =savedUser.getRole();
        changeduser.setRole(roleold);
        //actualiza el pass
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encriptador
        String encodedPassword = passwordEncoder.encode(changeduser.getPassword()); //encripta contraseña
        changeduser.setPassword(encodedPassword);


        //actualiza el profile
        UserProfile changedUserProfile = changeduser.getUserProfile();
        changedUserProfile.setId(savedUser.getId());
        changedUserProfile.setUser(changeduser);
        userProfileRepository.save(changedUserProfile);
        //guarda usuario
        userRepository.save(changeduser);
    }

    public User getActualUser(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userName = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(userName);
        user.setPassword("PRIVATE");
        return user;
    }


/*
    public List<User> AllLoginUsers(){
        List<Object> principals = sessionRegistry.getAllPrincipals();//lista de loggeados
        List<String> usersNamesList = new ArrayList<String>();//nueva lista
        for (Object principal: principals) {
            if (principal instanceof User) {
                usersNamesList.add(((User) principal).getUsername());
            }
        } //carga el array de usernames
        List<User> usersList = new ArrayList<User>();
        for (String username: usersNamesList) {
            if (userRepository.existsByUsername(username)) {
                usersList.add(userRepository.findByUsername(username));
            }
        }
        return usersList;
    }

*/

}