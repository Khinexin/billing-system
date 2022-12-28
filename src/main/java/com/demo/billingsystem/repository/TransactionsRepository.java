package com.demo.billingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.billingsystem.model.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

}
