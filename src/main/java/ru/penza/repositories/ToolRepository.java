package ru.penza.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.penza.models.Tool;


public interface ToolRepository extends JpaRepository<Tool, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update tool set critical_value = ?2 where tool.name = ?1", nativeQuery = true)
    Integer updateCriticalByToolName(String toolName, Double criticalValue);
}
