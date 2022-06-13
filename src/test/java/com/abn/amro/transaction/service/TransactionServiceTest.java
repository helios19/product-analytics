package com.abn.amro.transaction.service;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.repository.TransactionRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.abn.amro.common.utils.ClassUtils.toDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test", "cacheDisabled"})
@SpringBootTest(classes = TransactionServiceTest.TestAppConfig.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private Transaction sampleTransaction = Transaction
            .builder()
            .customer("1")
            .date(toDate("1/10/2016 2:51:23 AM"))
            .amount(BigDecimal.valueOf(23.4))
            .description("transaction description")
            .build();

    @Test
    @DirtiesContext
    public void shouldFindTransactionByCustomerId() {
        // given
        when(repository.findByCustomerId(any(String.class))).thenReturn(
                Lists.newArrayList(sampleTransaction));

        // when
        List<Transaction> transactions = transactionService.findByCustomerId("1");

        // then
        assertFalse(transactions.isEmpty());
        assertNotNull(transactions.get(0));
        assertEquals("1", transactions.get(0).getCustomer());
        assertEquals(toDate("1/10/2016 2:51:23 AM"), transactions.get(0).getDate());
        assertEquals(BigDecimal.valueOf(23.4), transactions.get(0).getAmount());
        assertEquals("transaction description", transactions.get(0).getDescription());
        verify(repository, times(1)).findByCustomerId(any(String.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DirtiesContext
    public void shouldFindTransactionByCustomerIdAndDate() {
        // given
        when(repository.findByCustomerIdAndDate(any(String.class), any(Date.class), any(Date.class)))
                .thenReturn(Lists.newArrayList(sampleTransaction));

        // when
        Date start = toDate("1/10/2016 1:00:00 AM");
        Date end = toDate("1/10/2016 3:00:00 AM");
        List<Transaction> transactions = transactionService.findByCustomerIdAndDate("1", start, end);

        // then
        assertFalse(transactions.isEmpty());
        assertNotNull(transactions.get(0));
        assertEquals("1", transactions.get(0).getCustomer());
        assertEquals(toDate("1/10/2016 2:51:23 AM"), transactions.get(0).getDate());
        assertEquals(BigDecimal.valueOf(23.4), transactions.get(0).getAmount());
        assertEquals("transaction description", transactions.get(0).getDescription());
        verify(repository, times(1)).findByCustomerIdAndDate(any(String.class), any(Date.class), any(Date.class));
        verifyNoMoreInteractions(repository);
    }
//
//    @Test
//    @DirtiesContext
//    public void shouldFindAll() {
//        // given
//        when(repository.findAll()).thenReturn(
//                Lists.newArrayList(sampleTransaction));
//
//        // when
//        List<Transaction> transactions = transactionService.findAll();
//
//        // then
//        assertFalse(transactions.isEmpty());
//        assertTrue(transactions.size() == 1);
//        assertNotNull(transactions.get(0));
//        assertEquals(transactions.get(0).getId(), "1");
//        assertEquals(transactions.get(0).getVersion(), 1l, 0);
//        assertEquals(transactions.get(0).getTitle(), "transaction title");
//        assertEquals(transactions.get(0).getBody(), "transaction body");
//        assertEquals(transactions.get(0).getTags(), Arrays.asList("news", "sport"));
//        assertEquals(transactions.get(0).getDate(), toDate("2016-10-01"));
//        verify(repository, times(1)).findAll();
//        verifyNoMoreInteractions(repository);
//    }

    @Test
    @DirtiesContext
    public void shouldSaveTransaction() {
        // when
        transactionService.save(sampleTransaction);

        // then
        verify(repository, times(1)).saveOrUpdate(sampleTransaction);
        verifyNoMoreInteractions(repository);
    }

    @Configuration
//    @EnableAutoConfiguration
//    @EnableMongoRepositories(basePackages = "com.abn.amro.transaction.repository")
    @ComponentScan({
            "com.abn.amro.transaction.service"
    })
    public static class TestAppConfig {

        @Bean
        public TransactionRepository transactionRepository() {
            return null;
        }
    }
}

