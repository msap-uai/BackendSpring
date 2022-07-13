package com.porfolio.MS.ActualUser;

import com.porfolio.MS.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/")
public class ActualUserController {

    @Autowired
    private ActualUserService actualUserService;

    //login

    //datos del usuario loggeado
    @GetMapping("/user")
    public ResponseEntity<User> getActualUser() {

        return new ResponseEntity<User>(actualUserService.getActualUser(), HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteActualUser() {
        return new ResponseEntity<String>(actualUserService.deleteActualUser(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public  ResponseEntity<String> changeActualUser(@RequestBody User user) {
        actualUserService.changeActualUser(user);
        return new ResponseEntity<String>("El usuario fue modificado", HttpStatus.OK);
    }

/*
    //mostrar todos los usuarios loggeados
    @GetMapping("/admin/loginall")
    public ResponseEntity<List<User>> allUsersLogin(){
        List<User> lista = actualUserService.AllLoginUsers();
        return new ResponseEntity<List<User>>(lista, HttpStatus.OK);
    }
*/

}
