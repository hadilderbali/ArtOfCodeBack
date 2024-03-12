package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
@CrossOrigin("http://localhost:4200")
public class JobController {
    final JobService jobService;


    @PostMapping("/addOffer")
    public JobOffer addJobOffer(@RequestBody JobOffer jobOffer){
        return jobService.addJobOffer(jobOffer);
}
    @GetMapping("/offer/{IdR}")
    public  JobOffer getjobofferffer(@PathVariable Long IdR){
        return jobService.getJobOffer(IdR);
}
    @GetMapping("/getoffer")
    public  List<JobOffer> getOffer(){
        return jobService.getJobOffers();
    }

    @PutMapping("/updatejoboffer/{idJobOffer}")
    public JobOffer updateJobOfferById(@PathVariable("idJobOffer") Long idJobOffer, JobOffer updatedJobOffer){
        updatedJobOffer.setIdR(idJobOffer);
        return jobService.addJobOffer(updatedJobOffer);
    }
    @DeleteMapping("/DeletejobOffer/{IdR}")
    public void removeJobOffer(@PathVariable("IdR") Long IdR) {
        jobService.removeJobOffer(IdR);
    }

}
