package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.modelRepo.dto.Account;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class FundTransferService {

    private static final Logger logger = LoggerFactory.getLogger(FundTransferService.class);

    private AccountDao accountDao;
    private TraderDao traderDao;

    @Autowired
    public FundTransferService(AccountDao accountDao, TraderDao traderDao) {
        this.accountDao = accountDao;
        this.traderDao = traderDao;
    }


    /**
     * Deposit a fund to the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader id cant be null
     * @param amount   (can't be 0)
     * @return updated Account object
     * @throws ResourceNotFoundException if account is not found
     * @throws IllegalArgumentException  for invalid input
     */
    public Account deposit(Integer traderId, Double amount) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderId is not valid");
        }

        if (amount <= 0.0) {
            throw new IllegalArgumentException("Fund amount must be > than 0");
        }

        if (!(traderDao.existsById(traderId))) {
            throw new ResourceNotFoundException("Trader does not exists");
        }
        Account account = accountDao.findById(traderId);
        accountDao.updateAmountByID(account.getID(), amount);

        return null;
    }


    /**
     * Withdraw a fund from the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId             trader ID
     * @param currentTranscAmount amount can't be 0
     * @return updated Account object
     * @throws IllegalArgumentException for invalid input
     */
    public Account withdraw(Integer traderId, Double currentTranscAmount) {

        if (traderId == null || currentTranscAmount == null) {
            throw new IllegalArgumentException("Invalid input/argument");
        } else {

            Account account = accountDao.findById(traderId);
            Double newAmount = account.getAmount() - currentTranscAmount;

            if (newAmount >= 0) {
                accountDao.updateAmountByID(traderId, newAmount);
            } else {
                throw new IllegalArgumentException("Not enough amount for withdrawal");
            }

            account.setAmount(newAmount);
            logger.info("Withdrawing " + currentTranscAmount + " from Trader account#: " + account.getTraderId());
            return account;
        }
    }
}


