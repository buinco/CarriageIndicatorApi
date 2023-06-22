package de.db.train.carriage.indicator.controller;

import de.db.train.carriage.indicator.services.CarriageIndicatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class CarriageIndicatorController {

    private final CarriageIndicatorService carriageIndicatorService;

    @GetMapping(value="/station/{ril100}/train/{trainNumber}/waggon/{number}")
    public ResponseEntity<List<String>> getTrackSection(@PathVariable("ril100") String ril100, @PathVariable("trainNumber") int trainNumber, @PathVariable("number") int number) {
        List<String> sections =  carriageIndicatorService.getTrackSection(ril100, trainNumber, number);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }
}
