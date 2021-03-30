package com.pluralsight.conferencedemo.repositories;

import com.pluralsight.conferencedemo.models.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository <Speaker, Long>{//Speaker is the datatype and Long refers to the prim key
    /* This gives us access to db methods like find, delete, etc. */
}
