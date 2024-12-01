package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
class CustomerController {

    @GetMapping("/customers")
    public String displayCustomers(Model model) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8091/customers/api";

        String response = restTemplate.getForObject(apiUrl, String.class); // Fetch response
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
        List<Map<String, Object>> customers = (List<Map<String, Object>>) responseMap.get("data");
   

        model.addAttribute("customers", customers);

        return "customers";
    }
    
    
    
    

}
