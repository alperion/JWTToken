package com.example.demo.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin()
public class DemoController {


     @GetMapping("/deneme")
     public ResponseEntity<String> sayhello(){
         return ResponseEntity.ok("alperen muhteşemdir");
     }

    @GetMapping("/denemee")
    public ResponseEntity<String> sayhelloo(){
        return ResponseEntity.ok("alperen efsanedir");
    }



}
