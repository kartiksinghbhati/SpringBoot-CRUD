package com.assignment.crud.service;

import com.assignment.crud.entity.Seat;
import com.assignment.crud.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    private final Map<String, Timer> holdSeatTimers = new HashMap<>();

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat holdSeat(String seatNo, int userId) {
        Seat existingSeat = seatRepository.findById(seatNo).orElse(null);

        if(existingSeat!=null){
            if(existingSeat.getSeatStatus().equals("available")){
                existingSeat.setSeatStatus("hold");
                existingSeat.setUserId(userId);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Seat seat = seatRepository.findById(seatNo).orElse(null);
                        if (seat != null && seat.getSeatStatus().equals("hold")) {
                            seat.setSeatStatus("available");
                            seat.setUserId(0);
                            seatRepository.save(seat);
                            holdSeatTimers.remove(seatNo);
                        }
                    }
                }, 10 * 60 * 1000); // 10 minutes in milliseconds

                holdSeatTimers.put(seatNo, timer);

                return seatRepository.save(existingSeat);
            }
            else {
                throw new IllegalStateException("Seat is not available");
            }
        }
        else{
            throw new NoSuchElementException("Seat not found.");
        }
    }

    public Seat bookSeat(String seatNo, int userId) {

        Seat existingSeat = seatRepository.findById(seatNo).orElse(null);

        if(existingSeat!=null){
            if(existingSeat.getSeatStatus().equals("hold")){

                existingSeat.setSeatStatus("booked");

                Timer timer = holdSeatTimers.get(seatNo);
                if (timer != null) {
                    timer.cancel();
                    holdSeatTimers.remove(seatNo);
                }

                return seatRepository.save(existingSeat);
            }
            else {
                throw new IllegalStateException("Seat is not available");
            }
        }
        else{
            throw new NoSuchElementException("Seat not found.");
        }
    }
}
