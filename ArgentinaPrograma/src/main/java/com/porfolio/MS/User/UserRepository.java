package com.porfolio.MS.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author MANUEL SAPONARO
 *
 */
@Repository
//@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findAll();

    User findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsById(Long id);
    void deleteById(Long id);



}

