package iwo.wintech.cafemanagement.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/custom")
public class CustomController {
    @GetMapping("/message")
    public String getMessage() {
        return "Hello from Custom Controller";
    }
}
