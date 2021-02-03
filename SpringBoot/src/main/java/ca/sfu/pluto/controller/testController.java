package ca.sfu.pluto.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Class testController is a simple controller that retrieves requests and outputs a certain entity
 */
@RestController
class testController {

    // this image link makes getTest() more readable
    final static String TEST_IMAGE="https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5d72a5675b52ce00088287fb%2F960x0.jpg%3Ffit%3Dscale";

    /**
     * A test method to test the REST API for the home page "/"
     *
     * @return The message or object to be sent back to the user
     */
    @GetMapping("/")
    public String getHomePage(){
        // just returns a string object
        return "Home";
    }

    /**
     * A test method to test the REST API when a user requests the path "/test"
     *
     * @return The message or object to be sent back to the user
     */
    @GetMapping("/test")
    public String getTest(){
        // returns a HTML style object that the browser will parse
        return "<html><body><h1>Test</h1><img  src=\""+TEST_IMAGE+"\" alt=\"Pluto\"/></body></html>";
    }
}
