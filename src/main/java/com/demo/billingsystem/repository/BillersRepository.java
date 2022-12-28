package com.demo.billingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.billingsystem.model.Billers;

@Repository
public interface BillersRepository extends JpaRepository<Billers, Integer> {

}
