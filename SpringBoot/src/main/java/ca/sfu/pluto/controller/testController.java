package ca.sfu.pluto.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Class testController is a simple controller that retrieves requests and outputs a certain entity
 */
@RestController
class testController {

    /** getHomePage is a test method to test the REST API when a user requests /index
     *
     * @return The message or object to be sent back to the user
     */
    @GetMapping("/")
    public String getHomePage(){
        return "index.html";
    }


}
