package com.belum.apitemplate.controllers;

import com.belum.apitemplate.domain.Example;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

/**
 * Created by bel-sahn on 7/29/19
 */
@RestController
@RequestMapping("/api/v1")
public class ExampleController {
//region PROPERTIES
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//endregion

//region CONSTRUCTORS
    public ExampleController(){}
//endregion

//region HELPER METHODS

    @GetMapping("/resource")
    @ApiOperation(value = "Get Resource", notes = "Returns an example resource")
    public ResponseEntity<Example> getResource() {
        log.info("Getting resource...");
        return ResponseEntity.ok(new Example());
    }
//endregion
}
