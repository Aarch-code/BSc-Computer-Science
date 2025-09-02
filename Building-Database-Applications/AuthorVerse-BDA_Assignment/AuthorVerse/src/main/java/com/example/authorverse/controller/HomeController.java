/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String gethome() {
        return "index"; 
    }
}
