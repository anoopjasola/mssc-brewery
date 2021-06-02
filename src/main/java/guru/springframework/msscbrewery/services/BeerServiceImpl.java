package guru.springframework.msscbrewery.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.msscbrewery.web.model.BeerDto;

/**
 * Created by jt on 2019-04-20.
 */
@Service
public class BeerServiceImpl implements BeerService {
	
	private static List<BeerDto> list = new ArrayList<>();
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale").upc(100l)
                .build();
    }

	@Override
	public BeerDto saveBeer(BeerDto beerDto) {
		beerDto.setId(UUID.randomUUID());
		list.add(beerDto);
		return beerDto;
	}

	@Override
	public List<BeerDto> getAll() {
		// TODO Auto-generated method stub
		return list;
	}
}
