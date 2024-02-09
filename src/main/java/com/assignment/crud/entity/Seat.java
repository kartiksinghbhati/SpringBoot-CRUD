package com.assignment.crud.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat_details")
public class Seat {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    @Id
    private String seatNo;
    private String seatStatus;
    private int userId;

}

