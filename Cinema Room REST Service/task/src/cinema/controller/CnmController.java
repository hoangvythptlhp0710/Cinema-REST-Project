package cinema.controller;

import cinema.model.Cinema;
import cinema.model.OrderedSeat;
import cinema.model.Seat;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cinema.model.Cinema.getAllSeat;


class Token {
    UUID token;

    public Token() {

    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
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
                OrderedSeat orderedSeat = new OrderedSeat(UUID.randomUUID(), s);
                cinema.getOrderedSeats().add(orderedSeat);
                cinema.getAvailable_seats().remove(i);
                return new ResponseEntity<>(orderedSeat, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Token token) {
        List<OrderedSeat> orderedSeatList = cinema.getOrderedSeats();
        for (OrderedSeat orderedSeat: orderedSeatList) {
            if (orderedSeat.getToken().equals(token.getToken())){
                orderedSeatList.remove(orderedSeat);
                cinema.getAvailable_seats().add(orderedSeat.getTicket());
                return new ResponseEntity<>(Map.of("returned_ticket", orderedSeat.getTicket()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> statistic(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")){
            Map<String, Integer> statistic = new HashMap<>();
            int currentIncome = 0;
            for (OrderedSeat orderedSeat: cinema.getOrderedSeats()){
                currentIncome += orderedSeat.getTicket().getPrice();
            }
            int numberOfAvailableSeats = cinema.getAvailable_seats().size();
            int numberOfPurchasedTickets = cinema.getOrderedSeats().size();
            statistic.put("current_income", currentIncome);
            statistic.put("number_of_available_seats", numberOfAvailableSeats);
            statistic.put("number_of_purchased_tickets", numberOfPurchasedTickets);
            return new ResponseEntity<>(statistic, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
        }
    }
}
