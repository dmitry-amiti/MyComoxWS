package ru.penza.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.penza.models.Engine;

import java.util.Optional;

public interface EngineRepository extends JpaRepository<Engine, Integer> {
    Optional<Engine> findOneByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from engine where engine.id = ?1", nativeQuery = true)
    Integer deleteEngine(Long id);
}
