package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Club;
import com.artofcode.artofcodebck.Repositories.IClubRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClubService implements IClubService{
    @Autowired
    private IClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;
    private  final ServletContext context;

    @Override
    public Club createOrUpdateClub(Club club) {
        return clubRepository.save(club);
    }
    @Override

    public byte[] getClubPhotoById(Long clubId) throws Exception {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new Exception("imageclub not found"));
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + club.getFileName()));
    }

    @Override
    public  void deleteClub(Long clubId) {
        clubRepository.deleteById(clubId);

    }
    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
    @Override
    public Optional<Club> retrieveClubById(Long id) {
        return clubRepository.findById(id);
    }


}
