package com.sbrf.reboot.bank;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MainReport {
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 30;

    public static final LocalDate START_DATE = LocalDate.of(2021, Month.JULY, 1);
    public static final LocalDate END_DATE = LocalDate.of(2021, Month.AUGUST, 1);


    public static void main(String[] args) {
        Account account1 = new Account(BigDecimal.valueOf(20), "RUB", LocalDate.of(2021, Month.JULY, 2));
        Account account2 = new Account(BigDecimal.valueOf(206), "EUR", LocalDate.of(2021, Month.JULY, 1));
        Account account3 = new Account(BigDecimal.valueOf(13), "RUB", LocalDate.of(2021, Month.AUGUST, 3));
        Account account4 = new Account(BigDecimal.valueOf(1.06), "RUB", LocalDate.of(2021, Month.JULY, 26));

        Customer customer1 = new Customer(20, "Ivan", new ArrayList<Account>() {{
            add(account1);
            add(account2);
        }});

        Customer customer2 = new Customer(31, "Egor", new ArrayList<Account>() {{
            add(account3);
            add(account4);
        }});

        Customer customer3 = new Customer(18, "Katy", new ArrayList<Account>() {{
            add(account1);
            add(account4);
        }});

        BigDecimal sum = MainReport.getTotalsWithCompletableFuture(Stream.of(customer1, customer2, customer3));
        BigDecimal sum1 = MainReport.getTotalsWithReact(Stream.of(customer1, customer2, customer3));
    }

    @SneakyThrows
    public static BigDecimal getTotalsWithCompletableFuture(Stream<Customer> customerStream) {
        return CompletableFuture.supplyAsync(() -> customerStream
                                .filter(c -> c.getAge() >= MIN_AGE && c.getAge() <= MAX_AGE)
                                .flatMap(Customer::getAccounts)
                                .filter(a -> START_DATE.isBefore(a.getCreateDate()) || START_DATE.isEqual(a.getCreateDate()))
                                .filter(a -> END_DATE.isAfter(a.getCreateDate()) || END_DATE.isEqual(a.getCreateDate()))
                                .filter(a -> "RUB".equals(a.getCurrency()))
                                .map(Account::getAmount)
                                .reduce(BigDecimal::add)
                                .orElse(BigDecimal.valueOf(0)),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .get();
    }

    public static BigDecimal getTotalsWithReact(Stream<Customer> customerStream) {
        Mono<BigDecimal> mono = Mono.just(
                customerStream.filter(c -> c.getAge() >= MIN_AGE && c.getAge() <= MAX_AGE)
                        .flatMap(Customer::getAccounts)
                        .filter(a -> START_DATE.isBefore(a.getCreateDate()) || START_DATE.isEqual(a.getCreateDate()))
                        .filter(a -> END_DATE.isAfter(a.getCreateDate()) || END_DATE.isEqual(a.getCreateDate()))
                        .filter(a -> "RUB".equals(a.getCurrency()))
                        .map(Account::getAmount)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.valueOf(0))

        );
        return mono.block();
    }
}
