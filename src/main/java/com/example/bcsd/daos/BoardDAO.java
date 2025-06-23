/*
package com.example.bcsd.daos;


import com.example.bcsd.models.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class BoardDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<String> findNameById(int id) {
        List<String> names = em.createQuery("SELECT b.name FROM Board b WHERE b.id = :id", String.class)
                .setParameter("id", id)
                .getResultList();
        return names.stream().findFirst();
    }
}
*/
