package com.crio.cred.repository;

import com.crio.cred.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author harikesh.pallantla
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by email id.
     *
     * @param emailId the email id
     * @return the user details if found
     */
    Optional<User> findByEmailId(String emailId);

    /**
     * Find by email id and password optional.
     *
     * @param emailId  the email id
     * @param password the password
     * @return the user details if found
     */
    Optional<User> findByEmailIdAndPassword(String emailId, String password);
}
