package com.assignment.crud.controller;

import com.assignment.crud.entity.Seat;
import com.assignment.crud.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @PostMapping("/add-seat")
    public Seat addSeat(@RequestBody Seat seat) {
        System.out.println(seat.getSeatNo());
        return seatService.saveSeat(seat);
    }

    @GetMapping("/seat-layout")
    public ResponseEntity<List<Seat>> getAllSeats() {
        List<Seat> seats = seatService.getAllSeats();
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/hold-seat")
    public ResponseEntity<Seat> holdSeat(@RequestParam String seatNo, @RequestParam int userId) {
        Seat heldSeat = seatService.holdSeat(seatNo, userId);
        return ResponseEntity.ok(heldSeat);
    }

    @PostMapping("/book-seat")
    public ResponseEntity<Seat> bookSeat(@RequestParam String seatNo, @RequestParam int userId) {
        Seat bookedSeat = seatService.bookSeat(seatNo, userId);
        return ResponseEntity.ok(bookedSeat);
    }
}
