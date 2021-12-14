package ru.penza.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.penza.models.Value;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Integer> {
    @Query(value = "SELECT engine.name as engine_name, tool.name as tool_name, value.value as value, " +
            "value.timestamp as timestamp " +
            "FROM engine " +
            "INNER JOIN value  ON engine.id = value.engine_id " +
            "INNER JOIN tool ON tool.id = value.tool_id " +
            "INNER JOIN (SELECT engine.name as e_name, tool.name as t_name, MAX(value.timestamp) as max_ts " +
            "FROM engine " +
            "INNER JOIN value ON engine.id = value.engine_id " +
            "INNER JOIN tool ON tool.id = value.tool_id " +
            "GROUP BY engine.name, tool.name) as sec " +
            "ON engine.name = sec.e_name AND tool.name = sec.t_name AND value.timestamp = sec.max_ts " +
            "ORDER BY engine.name", nativeQuery = true)
    List<Object[]> getAllValues();


    @Transactional
    @Modifying
    @Query(value = "delete from value where value.engine_id = ?1", nativeQuery = true)
    Integer deleteValue(Long id);


    @Query(value = "select * from value where engine_id=(select id from engine where name=?3)" +
            "and tool_id=(select id from tool where name=?4)" +
            "and timestamp between ?1 and ?2 order by timestamp", nativeQuery = true)
    List<Value> getLastValues(Long timeThen, Long timeNow, String engine, String tool);

}
