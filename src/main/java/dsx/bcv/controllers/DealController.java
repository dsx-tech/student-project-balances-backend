package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockDeals;
import dsx.bcv.data.models.Deal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deals")
public class DealController {

    private final MockDeals dealRepository;

    public DealController() {
        this.dealRepository = new MockDeals();
    }

    @RequestMapping("/list")
    public List<Deal> getAllDeals(){
        return dealRepository.getAllDeals();
    }
}
