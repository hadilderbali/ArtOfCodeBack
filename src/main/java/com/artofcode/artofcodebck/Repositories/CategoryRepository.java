package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
