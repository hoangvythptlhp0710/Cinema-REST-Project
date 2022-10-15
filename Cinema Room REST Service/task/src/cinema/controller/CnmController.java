package cinema.controller;

import cinema.model.Cinema;
import cinema.model.OrderedSeat;
import cinema.model.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

import static cinema.model.Cinema.getAllSeat;

@RestController
public class CnmController {

    private final Cinema cinema;

    public CnmController() {
        this.cinema = getAllSeat(9, 9);
    }

    @GetMapping("/seats")
    public Cinema getSeat() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        if (seat.getColumn() > cinema.getTotal_columns() || seat.getRow() > cinema.getTotal_rows() || seat.getRow() < 1 || seat.getColumn() < 1){
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < cinema.getAvailable_seats().size(); i++) {
            Seat s = cinema.getAvailable_seats().get(i);
            if (s.equals(seat)) {
                OrderedSeat orderedSeat = new OrderedSeat(s);
                cinema.getOrderedSeats().add(orderedSeat);
                cinema.getAvailable_seats().remove(i);
                return new ResponseEntity<>(s, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }
}
