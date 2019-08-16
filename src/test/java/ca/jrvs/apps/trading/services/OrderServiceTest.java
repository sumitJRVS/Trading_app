package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.modelRepo.domain.SecurityOrder;
import ca.jrvs.apps.trading.modelRepo.dto.Account;
import ca.jrvs.apps.trading.modelRepo.dto.MarketOrderDto;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Mockito Test for OrderService (Mocking injection : OrderService)
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    // The Class to get tested will be annotated as @InjectMock
    @InjectMocks
    OrderService orderService;

    // All dependencies will be annotated as @Mock
    @Captor
    ArgumentCaptor<SecurityOrder> argCaptorSecurityOrder;

    // All dependencies will be annotated as @Mock
    @Mock
    private PositionDao positionDao;
    @Mock
    private AccountDao accountDao;
    @Mock
    private QuoteDao_v1_jdbcCrudDao quoteDao;
    @Mock
    private TraderDao traderDao;
    @Mock
    private SecurityOrderDao securityOrderDao;

    @Mock
    private MarketOrderDto marketOrderDtoBuy;
    @Mock
    private MarketOrderDto marketOrderDtoSell;

    @Mock
    private Account account;
    @Mock
    private Trader trader;
    @Mock
    private Quote quote;
    @Mock
    private SecurityOrder securityOrder;

    @Before
    public void init() {
        //setting up fake data to test verify validate.
        marketOrderDtoBuy = new MarketOrderDto();
        marketOrderDtoBuy.setAccountId(1);
        marketOrderDtoBuy.setSize(2.0);
        marketOrderDtoBuy.setTicker("FB");

        marketOrderDtoSell = new MarketOrderDto();
        marketOrderDtoSell.setAccountId(1);
        marketOrderDtoSell.setSize(-2.0);
        marketOrderDtoSell.setTicker("FB");

        trader = new Trader();
        trader.setID(11);
        trader.setCountry("CAN");
        trader.setDob(LocalDate.parse("1995-10-29"));
        trader.setEmail("sumit.mistry.jrvs.ca@gmail.com");
        trader.setFirstName("Sumit");
        trader.setLastName("Mistry");

        account = new Account();
        account.setID(11);
        account.setTraderId(trader.getID());
        account.setAmount(55.55);

        quote = new Quote();
        quote.setAskPrice(9.00);
        quote.setBidPrice(9.99);

        quote.setAskSize((long) 5.5555);
        quote.setBidSize((long) 5.9999);

        quote.setTicker("FB");
        quote.setID("FB");
        quote.setLastPrice(12.0);

    }

    @Test
    public void orderServiceBuyHappyPath() {

        when(quoteDao.findById(anyString())).thenReturn(quote);
        when(accountDao.findById(anyInt())).thenReturn(account);

        when(accountDao.updateAmountByID(anyInt(), anyDouble())).thenReturn(account);
        securityOrder = orderService.executeMarketOrder(marketOrderDtoBuy);

        verify(securityOrderDao).save(argCaptorSecurityOrder.capture());
        SecurityOrder captorOrder = argCaptorSecurityOrder.getValue();

        assertEquals(SecurityOrder.orderStatus.FILLED, captorOrder.getStatus());

    }

    @Test
    public void orderServiceBuySadPath() {
        account.setAmount(1.0);

        when(quoteDao.findById(anyString())).thenReturn(quote);
        when(accountDao.findById(anyInt())).thenReturn(account);

        // List<MarketOrderDto> marketOrderDtos = Arrays.asList(savedOrderDtoBuy);
        securityOrder = orderService.executeMarketOrder(marketOrderDtoBuy);

        verify(securityOrderDao).save(argCaptorSecurityOrder.capture());
        SecurityOrder captorOrder = argCaptorSecurityOrder.getValue();
        assertEquals(SecurityOrder.orderStatus.CANCELLED, captorOrder.getStatus());

    }

    @Test
    public void orderServiceSellHappyPath() {

        when(quoteDao.findById(anyString())).thenReturn(quote);
        when(accountDao.findById(anyInt())).thenReturn(account);
        when(positionDao.getPosition(anyInt(), anyString())).thenReturn((long) 4);

        //  List<MarketOrderDto> marketOrderDtos = Arrays.asList(savedOrderdtoSell);
        securityOrder = orderService.executeMarketOrder(marketOrderDtoSell);

        verify(securityOrderDao).save(argCaptorSecurityOrder.capture());
        SecurityOrder captorOrder = argCaptorSecurityOrder.getValue();
        assertEquals(SecurityOrder.orderStatus.FILLED, captorOrder.getStatus());

    }

    @Test
    public void orderServiceSellSadPath() {

        when(quoteDao.findById(anyString())).thenReturn(quote);
        when(accountDao.findById(anyInt())).thenReturn(account);
        when(positionDao.getPosition(anyInt(), anyString())).thenReturn((long) 2);

        //List<MarketOrderDto> marketOrderDtos = Arrays.asList(savedOrderdtoSell);
        securityOrder = orderService.executeMarketOrder(marketOrderDtoSell);

        verify(securityOrderDao).save(argCaptorSecurityOrder.capture());
        SecurityOrder captorOrder = argCaptorSecurityOrder.getValue();
        assertEquals(SecurityOrder.orderStatus.CANCELLED, captorOrder.getStatus());

    }

}