package guru.springframework.msscbrewery.services;

import java.util.List;
import java.util.UUID;

import guru.springframework.msscbrewery.web.model.BeerDto;

/**
 * Created by jt on 2019-04-20.
 */
public interface BeerService {
    BeerDto getBeerById(UUID beerId);
    List<BeerDto> getAll();
    BeerDto saveBeer(BeerDto beerDto);
}
