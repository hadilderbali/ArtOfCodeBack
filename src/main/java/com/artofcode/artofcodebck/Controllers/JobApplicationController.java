package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
@CrossOrigin("*")
public class JobApplicationController {
    final JobService jobService;

    @PostMapping("/add")
    public JobApplication addJobApply(@RequestBody JobApplication jobApplication) {
        return jobService.addJobApplication(jobApplication);
    }

    @PutMapping("/update")
    public JobApplication UpdateJobApply(@RequestBody JobApplication jobApplication) {
        return jobService.updateJobApplication(jobApplication);
    }

    @DeleteMapping("{IdDancer}")
    public void deleteJobApply(@PathVariable Long IdDancer) {
        jobService.deleteJobApplication(IdDancer);

    }

    @GetMapping("{IdDancer}")
    public JobApplication getApplication(@PathVariable Long IdDancer) {
        return jobService.getApplication(IdDancer);
    }

    //@GetMapping("{NameD}")
    //public List<JobApplication> jobApplications(@PathVariable String NameD) {
    //    return jobService.jobApplications(NameD);
    //}

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

}
