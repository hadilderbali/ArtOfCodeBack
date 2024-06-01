package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.FileMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMediaRepository extends JpaRepository<FileMedia,Long> {
}
