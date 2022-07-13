package com.porfolio.MS.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author MANUEL SAPONARO
 *
 */

@RestController
//@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200") //ANGULAR
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //porfolio personal del usuario
    @GetMapping("/{username}")
    public ResponseEntity<User> verUsuario(@PathVariable String username){

        return new ResponseEntity<User>(userService.verUsuario(username), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/registro") //
    public ResponseEntity<String> saveUser( @RequestBody User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            return new ResponseEntity<String>("Fail -> El mail ya se encuentra registrado!",
                    HttpStatus.BAD_REQUEST);
        }
        newUser.setRole("USER");
        String response = userService.saveUser(newUser);
        return new ResponseEntity<String>(response, HttpStatus.OK);

    }

//=================================================================================
    //ver todos
    @GetMapping("/admin")
    public ResponseEntity<List<User>> showUsers() {
        List<User> lista = userService.showUsers();
        return new ResponseEntity<List<User>>(lista, HttpStatus.OK);
    }

    //ver uno
    @PostMapping("/admin")
    public ResponseEntity<User> showUser(@RequestParam Long id) {
        return new ResponseEntity<User>(userService.showUser(id), HttpStatus.OK);
    }

    //modificar
    @PutMapping("/admin")
    public ResponseEntity<String> changeUser(User user){

        return new ResponseEntity<String>(userService.changeUser(user), HttpStatus.OK);
    }

    //borrar
    @DeleteMapping("/admin")
    public  ResponseEntity<String> deleteUser(@RequestParam Long id) {


        return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);

    }
//====================================================================================



}
