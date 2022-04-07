package com.learn.springbootproject.controller;

import com.learn.springbootproject.models.LocationStats;
import com.learn.springbootproject.service.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
//is not RestController, in rest controller, all methods response is converted to json
//but we want Controller to render html ui, so no need of rest controller
public class HomeController {

    @Autowired
    CoronaDataService coronaDataService;

    @GetMapping("/")
    public String home(Model model){

        //System.out.println("testing.........................");
       // System.out.println(coronaDataService.getAllStats());
        List<LocationStats> allStats=coronaDataService.getAllStats();
        //loop through allStats to sum the total cases
        int totalReportedCases=allStats.stream().mapToInt(stat -> stat.getTotalCases()).sum();
        int newTotalCases=allStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("locationStats",allStats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("newTotalCases",newTotalCases);
        return "home";//home is name of template, under templates folder
        //thymeleaf/spring boot model concept: have access to model in ui/gtml page using thymeleaf syntax
    }
}
