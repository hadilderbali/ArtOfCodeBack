package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event,Long> {

}
