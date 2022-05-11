package com.example.demo.repositories;

import com.example.demo.models.Numbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NumberRepository extends JpaRepository<Numbers, Long> {

    @Query(value = "SELECT * FROM numbers WHERE flag = false ORDER BY rand() LIMIT 1", nativeQuery = true)
    Numbers findNumberFromRandomSelect();

    @Query(value = "SELECT * FROM numbers WHERE flag = false AND numbers.id > ?1 LIMIT 1", nativeQuery = true)
    Numbers findNumberFromRecentSelect(long id);

    @Transactional
    @Modifying
    @Query("update Numbers n set n.flag = 'true' where n.id = ?1")
    void updateFlagByIdEquals(long id);

}
