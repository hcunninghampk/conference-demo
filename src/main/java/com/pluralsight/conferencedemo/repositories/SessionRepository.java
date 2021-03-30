package com.pluralsight.conferencedemo.repositories;

import com.pluralsight.conferencedemo.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository <Session, Long>{//Session is the datatype and Long refers to the prim key
    /* This is a JPA repo.  Gives us access to db methods like find, delete, etc. */
}
