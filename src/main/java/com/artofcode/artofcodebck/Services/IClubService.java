package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Club;

import java.util.List;
import java.util.Optional;

public interface IClubService {

    Club createOrUpdateClub(Club club);

    byte[] getClubPhotoById(Long clubId) throws Exception;

    void deleteClub(Long clubId);

    List<Club> getAllClubs();

    Optional<Club> retrieveClubById(Long id);



}
