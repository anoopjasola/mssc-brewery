package guru.springframework.msscbrewery.web.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }
    
    @GetMapping({"/getall"})
    public ResponseEntity<?> getAll(){

        return new ResponseEntity<>(beerService.getAll(), HttpStatus.OK);
    }
    
    @PostMapping({"/addbeer"})
    public ResponseEntity<BeerDto> addBeer(@RequestBody BeerDto beerDto){

        return new ResponseEntity<>(beerService.saveBeer(beerDto), HttpStatus.OK);
    }

}
