//package com.example.demo;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.client.RestTemplate;
//
//@Controller
//public class ActorController {
//
//    private final String BACKEND_URL = "http://localhost:8092/api/actors"; // Update with your backend URL and port
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping("/actors")
//    public String showActors(Model model) {
//        try {
//            ResponseEntity<List> response = restTemplate.getForEntity(BACKEND_URL, List.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                model.addAttribute("actors", response.getBody());
//            } else {
//                model.addAttribute("error", "Failed to fetch actors: " + response.getStatusCode());
//            }
//        } catch (Exception e) {
//            model.addAttribute("error", "An error occurred: " + e.getMessage());
//        }
//        return "actors";
//    }
//    
//    @PostMapping("/actors/update")
//    public String updateActor(@ModelAttribute Actor actorDto, Model model) {
//        try {
//            String updateUrl = BACKEND_URL + "/" + actorDto.getActor_Id(); // Construct the update URL
//            ResponseEntity<Actor> response = restTemplate.exchange(updateUrl, org.springframework.http.HttpMethod.PUT, new HttpEntity<>(actorDto), Actor.class);
//            if (response.getStatusCode() == HttpStatus.OK) {
//                return "redirect:/actors"; // Redirect back to the actors list after successful update
//            } else {
//                model.addAttribute("updateError", "Failed to update actor: " + response.getStatusCode());
//            }
//        } catch (Exception e) {
//            model.addAttribute("updateError", "An error occurred while updating actor: " + e.getMessage());
//        }
//        return "actors"; // Stay on the page if an error occurred
//    }
//}



package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.client.RestTemplate;


@Controller
public class ActorController {

    private final String BACKEND_URL = "http://localhost:8092/api/actors"; // Update with your backend URL and port

    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/landingPage")
    public String landingPage() {
    	return "LandingPage";
    }

    @GetMapping("/actors")
    public String showActors(Model model) {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(BACKEND_URL, List.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("actors", response.getBody());
            } else {
                model.addAttribute("error", "Failed to fetch actors: " + response.getStatusCode());
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while fetching actors: " + e.getMessage());
        }
        return "actors";
    }


    @PostMapping("/actors/update")
    public String updateActor(@ModelAttribute Actor actorDto, Model model) {
        try {
            String updateUrl = BACKEND_URL + "/" + actorDto.getActor_id(); 
            ResponseEntity<Actor> response = restTemplate.exchange(updateUrl, org.springframework.http.HttpMethod.PUT, new HttpEntity<>(actorDto), Actor.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/actors"; 
            } else {
                model.addAttribute("updateError", "Failed to update actor: " + response.getStatusCode());
            }
        } catch (Exception e) {
            model.addAttribute("updateError", "An error occurred while updating actor: " + e.getMessage());
        }
        return "actors"; 
    }


    @GetMapping("/actors/{actorId}/edit")
    public String showEditActorForm(@PathVariable short actorId, Model model) {
        try {
            String actorUrl = BACKEND_URL + "/" + actorId;
            ResponseEntity<Actor> response = restTemplate.getForEntity(actorUrl, Actor.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("actor", response.getBody());
                return "edit-actor";
            } else {
                model.addAttribute("error", "Failed to fetch actor details: " + response.getStatusCode());
                return "actors"; // Redirect back to the actor list on error
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "actors";
        }
    }
}

