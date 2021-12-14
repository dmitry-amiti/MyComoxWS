package ru.penza.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.penza.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    @Transactional
    @Modifying
    @Query(value = "delete from mycomox_user where login = ?1", nativeQuery = true)
    Integer deleteUser(String login);

    @Query(value = "select login from mycomox_user", nativeQuery = true)
    List<String> getUsersLogins();
}
