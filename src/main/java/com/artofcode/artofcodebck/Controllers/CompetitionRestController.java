package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Services.Competition.CompetitionServiceImpl;
import com.artofcode.artofcodebck.Services.Competition.ICompetitionService;
import com.artofcode.artofcodebck.config.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
  import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/competition")
@CrossOrigin("http://localhost:8089")
public class CompetitionRestController {

    @Autowired
    private ICompetitionService competitionService;
    @Autowired
   private CloudinaryService cloudinaryservice;
@Autowired
private CompetitionServiceImpl competitionServiceimpl;
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadUser";

    @GetMapping("getbyid/{IdCompetition}")
    public Competition getCompetitionById(@PathVariable(name = "IdCompetition") Long id){
        return competitionService.getCompetitionById(id);
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return cloudinaryservice.uploadImage(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors du téléversement de l'image : " + e.getMessage();
        }
    }
    @GetMapping("/all")
    public List<Competition> getAllCompetitions(){
        return competitionService.getAllCompetitions();
    }


    @PostMapping("/addCompetition") public Competition addCompetition(
            @RequestBody Competition e){ String image = e.getPicture();
        e.setPicture(image.substring(12)); return competitionService.addCompetition(e); }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {

        Path filePath = Paths.get(uploadDirectory).resolve(filename);
        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() || file.isReadable()) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }
    @PutMapping("/updateCompetition/{id}")
    public Competition updateCompetition(@RequestBody Competition competition,@PathVariable("id")Long id) throws IOException {
        return competitionService.updateCompetition(competition,id);
    }
    // Exception handler for handling IOExceptions
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIOException(IOException ex) {
        return "Error uploading image: " + ex.getMessage();
    }
    private static final Logger logger = LoggerFactory.getLogger(CompetitionRestController.class);

    // Exception handler for handling other exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
// Log the exception for further investigation
        logger.error("An unexpected error occurred", ex);

        // Return a more informative error message
        return "An unexpected error occurred: " + ex.getMessage();
    }
    @GetMapping("/pagedd")
    public Page<Competition> getEventsPaged(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        return competitionServiceimpl.gets(page, pageSize);
    }
    @DeleteMapping("/deleteCompetition/{idCompetition}")
    public void deleteCompetition(@PathVariable(name = "idCompetition") Long id){
        competitionService.deleteCompetition(id);
    }

}
