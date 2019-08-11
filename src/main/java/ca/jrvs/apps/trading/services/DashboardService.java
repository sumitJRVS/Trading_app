package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao_v1_jdbcCrudDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.modelRepo.domain.PortfolioView;
import ca.jrvs.apps.trading.modelRepo.domain.Position;
import ca.jrvs.apps.trading.modelRepo.domain.SecurityRow;
import ca.jrvs.apps.trading.modelRepo.domain.TraderAccountView;
import ca.jrvs.apps.trading.modelRepo.dto.Account;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class DashboardService {
    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao_v1_jdbcCrudDao quoteDao;

    //constructor
    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao_v1_jdbcCrudDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    /**
     * Create and return a traderAccountView by trader ID
     * - get trader account by id
     * - get trader info by id
     * - create and return a traderAccountView
     *
     * @param traderId trader ID
     * @return traderAccount
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public TraderAccountView getTraderAccountView (Integer traderId) {
        if (traderId < 0 || !traderDao.existsById(traderId))
            throw new IllegalArgumentException(+ traderId + " : TraderID You entered is invalid");

        Trader trader = null;
        Account account = null;

        try {
            trader = traderDao.findById(traderId);
            account = accountDao.findById(traderId);
        } catch (ResourceNotFoundException err){
            err.printStackTrace();    }

        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);

        return traderAccountView;
    }


    /**
     * Create and return portfolioView by trader ID
     * - get account by trader id
     * - get positions by account id
     * - create and return a portfolioView
     *
     * @param traderId
     * @return portfolioView
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public PortfolioView getProfileViewByTraderId(Integer traderId) {

        if (traderId < 0 || !traderDao.existsById(traderId))
            throw new IllegalArgumentException( traderId + " : TraderId is invalid.");

        SecurityRow securityRow = new SecurityRow();
        Account account = accountDao.findById(traderId);

        List<Position> positionList;
        positionList = positionDao.getPosition(account.getID());

        List<SecurityRow> securityRowList;
        securityRowList = new ArrayList<>();

        for (Position position : positionList) {
            Quote quote = quoteDao.findById(position.getTicker());
            securityRow.setQuote(quote);
            securityRow.setPosition(position);
            securityRow.setTicker(position.getTicker());
            securityRowList.add(securityRow);
        }

        PortfolioView portfolioView = new PortfolioView();
        portfolioView.setSecurityRows(securityRowList);

        return portfolioView;

    }


}