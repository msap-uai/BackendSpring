package com.porfolio.MS.User;

import java.util.List;
/**
 *
 * @author MANUEL SAPONARO
 *
 */


public interface UserService {
    List<User> showUsers();
    User showUser(Long id);
    String changeUser(User user);
    String deleteUser(Long id);
    String saveUser(User user);
    User verUsuario(String username);

    User findUserByEmail(String email);


}
