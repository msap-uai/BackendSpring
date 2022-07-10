package com.porfolio.MS.User;

import com.porfolio.MS.Security.WebErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author MANUEL SAPONARO
 *
 */

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> showUsers() {
        List<User> lista = userRepository.findAll();
        return lista;
    }

    @Override
    public User showUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public String changeUser(User user){
        userRepository.save(user);
        return "Se modifico el usuario";
    }

    @Override
    public String deleteUser(Long id) {
        if(userRepository.existsById(id)){
            //roleRepository.deleteById(id);
            //tablas vinculadas no permiten borrar
            userRepository.deleteById(id);

            return "El usuario fue eliminado";
        }
        return "No se ha encontrado al usuario";
    }

    @Override
    public String saveUser(User user) {
        String username = (user.getApellido() + user.getNombre());
        if (userRepository.existsByUsername(username))
        {
            username = user.getEmail();
        }
        user.setUsername(username);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encriptador
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //encripta contraseña
        user.setPassword(encodedPassword);
        
        //crear tabla de profile child
        UserProfile userProfile = new UserProfile();
        //copia datos del usuario de referencia
        //userProfile = userProfileRepository.findById(user.getId());

        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        userRepository.save(user);
        return "El usuario se ha registrado con exito";
    }

    @Override
    public User verUsuario(String username) {

        if( userRepository.findByUsername(username) == null){
            throw new WebErrorException("El usuario " + username + " no existe.");
        }
        User user = userRepository.findByUsername(username);
        user.setPassword("Private");
        user.setId(00L);
        String file = user.getFoto();
        if(file != null) {user.setFoto("user-photos/" + file);}


        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }




}
