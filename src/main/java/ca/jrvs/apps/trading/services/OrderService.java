
package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao_v1_jdbcCrudDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.modelRepo.domain.Position;
import ca.jrvs.apps.trading.modelRepo.domain.SecurityOrder;
import ca.jrvs.apps.trading.modelRepo.dto.MarketOrderDto;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

        private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

        private AccountDao accountDao;
        private SecurityOrderDao securityOrderDao;
        private QuoteDao_v1_jdbcCrudDao quoteDao;
        private PositionDao positionDao;

        @Autowired
        public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                            QuoteDao_v1_jdbcCrudDao quoteDao, PositionDao positionDao) {
            this.accountDao = accountDao;
            this.securityOrderDao = securityOrderDao;
            this.quoteDao = quoteDao;
            this.positionDao = positionDao;
        }

        /**
         * Execute a market order
         * <p>
         * - validate the order (e.g. size, and ticker)
         * - Create a securityOrder (for security_order table)
         * - Handle buy or sell order
         * - buy order : check account balance
         * - sell order: check position for the ticker/symbol
         * - (please don't forget to update securityOrder.status)
         * - Save and return securityOrder
         * <p>
         * NOTE: you will need to some helper methods (protected or private)
         *
         * @param orderDto market order
         * @return SecurityOrder from security_order table
         * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
         * @throws IllegalArgumentException                    for invalid input
         */

        public SecurityOrder executeMarketOrder(MarketOrderDto  orderDto) {
            Double askPrice = 0.0;
            Double amount = 0.0;
            SecurityOrder securityOrder = new SecurityOrder();
                if (orderDto.getAccountId() < 0 || orderDto.getTicker() == null) {
                    throw new IllegalArgumentException("Invalid input");
                }


                securityOrder.setAccountId(orderDto.getAccountId());
                securityOrder.setSize(orderDto.getSize());
                securityOrder.setTicker(orderDto.getTicker());
                try {
                    askPrice = (quoteDao.findById(orderDto.getTicker())).getAskPrice();
                    logger.info(String.valueOf(askPrice));
                    amount = (accountDao.findById(orderDto.getAccountId())).getAmount();
                    logger.info(String.valueOf(amount));
                } catch (DataAccessException e) {
                    e.getMessage();
                }
                if (orderDto.getSize() > 0)  //buy+ sell-
                    buy(amount, askPrice, securityOrder, orderDto);

                else
                    sell(amount, askPrice, securityOrder, orderDto);



            SecurityOrder securityOrder1 = securityOrderDao.save(securityOrder);
            return securityOrder1;
        }

        protected void buy(Double currentAmount, Double askPrice, SecurityOrder securityOrder, MarketOrderDto orderDto) {
            Double securityCost = (orderDto.getSize() * askPrice);
            if (currentAmount < securityCost) {
                securityOrder.setStatus(SecurityOrder.orderStatus.CANCELLED);
                securityOrder.setNotes("You have less money in account, minimum should be " + securityCost);
            } else {
                securityOrder.setPrice(askPrice);
                securityOrder.setStatus(SecurityOrder.orderStatus.FILLED);
                securityOrder.setNotes(null);
                accountDao.updateAmountByID( orderDto.getAccountId(), currentAmount - securityCost);

            }

        }

        protected void sell(Double amount, Double askPrice, SecurityOrder securityOrder, MarketOrderDto orderDto) {
            Long pos = positionDao.getPosition(orderDto.getAccountId(), orderDto.getTicker());

            if (pos > -(orderDto.getSize())) {
                Double securityCost = -(orderDto.getSize() * askPrice);
                securityOrder.setPrice(askPrice);
                securityOrder.setStatus(SecurityOrder.orderStatus.FILLED);
                securityOrder.setNotes(null);
                accountDao.updateAmountByID(orderDto.getAccountId(), amount + securityCost);
            } else {
                securityOrder.setStatus(SecurityOrder.orderStatus.CANCELLED);
                securityOrder.setNotes("You have less security in account");
            }

        }

}

