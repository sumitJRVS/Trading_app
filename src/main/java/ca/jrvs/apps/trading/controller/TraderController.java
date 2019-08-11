package ca.jrvs.apps.trading.controller;


import ca.jrvs.apps.trading.modelRepo.domain.TraderAccountView;
import ca.jrvs.apps.trading.modelRepo.dto.Account;
import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import ca.jrvs.apps.trading.services.FundTransferService;
import ca.jrvs.apps.trading.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/trader")
public class TraderController {


    private FundTransferService fundService;
    private RegisterService registerService;

    @Autowired
    public TraderController(FundTransferService fundService, RegisterService registerService) {
        this.fundService = fundService;
        this.registerService = registerService;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TraderAccountView createTrader(@RequestBody Trader trader) {
        try {
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = "/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId) {
        try {
            registerService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/deposit/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account depositToAccount(@PathVariable Integer traderId, @PathVariable Double amount) {
        try {
            return fundService.deposit(traderId, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/withdraw/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdrawFromAccount(@PathVariable Integer traderId, @PathVariable Double amount) {
        try {
            return fundService.withdraw(traderId, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

