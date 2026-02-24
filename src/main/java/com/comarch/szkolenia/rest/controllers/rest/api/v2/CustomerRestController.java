package com.comarch.szkolenia.rest.controllers.rest.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequiredArgsConstructor
public class CustomerRestController {
    private final com.comarch.szkolenia.rest.controllers.rest.api.v1.CustomerRestController v1CustomerRestController;

    /*@GetMapping()
    public void costam() {
        this.v1CustomerRestController.costam();
    }*/
}
