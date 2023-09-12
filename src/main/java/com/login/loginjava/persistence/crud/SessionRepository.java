package com.login.loginjava.persistence.crud;

import com.login.loginjava.persistence.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository  extends JpaRepository<Session, Integer> {
}
