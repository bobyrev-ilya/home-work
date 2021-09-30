package com.sbrf.reboot.bank;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainReport {
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 30;

    public static final LocalDate START_DATE = LocalDate.of(2021, Month.JULY, 1);
    public static final LocalDate END_DATE = LocalDate.of(2021, Month.AUGUST, 1);


    @SneakyThrows
    public static void main(String[] args) {
        Account account1 = new Account(BigDecimal.valueOf(20), "RUB", LocalDate.of(2021, Month.JULY, 2));
        Account account2 = new Account(BigDecimal.valueOf(206), "EUR", LocalDate.of(2021, Month.JULY, 1));
        Account account3 = new Account(BigDecimal.valueOf(13), "RUB", LocalDate.of(2021, Month.AUGUST, 3));
        Account account4 = new Account(BigDecimal.valueOf(1.06), "RUB", LocalDate.of(2021, Month.JULY, 26));

        Customer customer1 = new Customer(20, "Ivan", Arrays.asList(account1, account2));

        Customer customer2 = new Customer(31, "Egor", Arrays.asList(account3, account4));

        Customer customer3 = new Customer(18, "Katy", Arrays.asList(account1, account4));

        System.out.println("Start processing ...");
        getTotalsWithReact(Stream.of(customer1, customer2, customer3));
        getTotalsWithCompletableFuture(Stream.of(customer1, customer2, customer3));
        System.out.println("Start sleeping ...");
        Thread.sleep(5000);
        System.out.println("Stop sleeping ...");
    }

    @SneakyThrows
    public static void getTotalsWithCompletableFuture(Stream<Customer> customerStream) {

        //коллекция Future для каждого Account
        List<CompletableFuture<BigDecimal>> completableFutureList = customerStream
                .filter(c -> c.getAge() >= MIN_AGE && c.getAge() <= MAX_AGE)
                .map(Customer::getAccounts)
                .map(x -> CompletableFuture.supplyAsync(
                        () -> x
                                .filter(a -> START_DATE.isBefore(a.getCreateDate()) || START_DATE.isEqual(a.getCreateDate()))
                                .filter(a -> END_DATE.isAfter(a.getCreateDate()) || END_DATE.isEqual(a.getCreateDate()))
                                .filter(a -> "RUB".equals(a.getCurrency()))
                                .map(Account::getAmount)
                                .reduce(BigDecimal.valueOf(0), BigDecimal::add),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
                ))
                .collect(Collectors.toList());

        //объединяем все Future в один, ждем выполнения и получаем результаты каждого CompletableFuture из ранее собранной коллекции
        //блокировки из-за join не так как объединенный CompletableFuture выполнился по thenApply
        CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(x -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .thenAccept(System.out::println);

    }

    public static void getTotalsWithReact(Stream<Customer> customerStream) {
        customerStream.filter(c -> c.getAge() >= MIN_AGE && c.getAge() <= MAX_AGE)
                .map(Customer::getAccounts)
                .map(x -> Flux.fromStream(x).publishOn(Schedulers.newParallel("Test")).log()
                        .filter(a -> START_DATE.isBefore(a.getCreateDate()) || START_DATE.isEqual(a.getCreateDate()))
                        .filter(a -> END_DATE.isAfter(a.getCreateDate()) || END_DATE.isEqual(a.getCreateDate()))
                        .filter(a -> "RUB".equals(a.getCurrency()))
                        .map(Account::getAmount))
                .reduce(Flux.empty(), Flux::merge)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add)
                .subscribe(System.out::println);
    }
}
