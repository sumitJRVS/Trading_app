package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.modelRepo.domain.Position;
import ca.jrvs.apps.trading.modelRepo.domain.TraderAccountView;
import ca.jrvs.apps.trading.modelRepo.dto.Account;
import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public RegisterService(TraderDao traderDao, AccountDao accountDao,
                           PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param traderinput trader info
     * @return traderAccountView
     */
    public TraderAccountView createTraderAndAccount(Trader traderinput) {
        Trader trd = new Trader();
        trd = traderDao.save(traderinput);

        Account ac = new Account();
        ac.setAmount(0.0);
        ac.setTraderId(traderinput.getID());
        accountDao.save(ac);

        TraderAccountView trdAc = new TraderAccountView();
        trdAc.setAccount(ac);

        trdAc.setTrader(trd);
        return trdAc;
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws IllegalArgumentException for invalid input
     */
    public void deleteTraderById(Integer traderId) {

        if (traderId == null) {
            throw new IllegalArgumentException("TraderId not exist");
        }

        //use TraderDAO object to check trader ID
        boolean traderIDcheck = traderDao.existsById(traderId);
        if (traderIDcheck == false) {
            throw new IllegalArgumentException("trader_id does not exist");
        }

        //use AccountDAO object to check account ID
        Account account = accountDao.findById(traderId);
        if (account.getAmount() == 0.0) {
            throw new IllegalArgumentException("Account has some balance and can not be deleted");
        }

        //now checking account + trader conditions together (nested if)
        List<Position> positionsList = positionDao.getPosition(traderId);
        if (positionsList.size() != 0) {
            positionsList.forEach(position -> {
                if (account.getAmount() == 0) {
                    securityOrderDao.deleteById(traderId);
                } else throw new IllegalArgumentException("Account does not have 0 balance and can not be deleted.");
            });
        }
        accountDao.deleteById(account.getTraderId());
        traderDao.deleteById(traderId);
    }


}