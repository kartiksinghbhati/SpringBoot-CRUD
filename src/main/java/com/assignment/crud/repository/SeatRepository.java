package com.assignment.crud.repository;

import com.assignment.crud.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,String> {
}
