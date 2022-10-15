package cinema.model;

import cinema.model.Seat;
import org.springframework.core.annotation.Order;


public class OrderedSeat {

    private Seat seat;
    public OrderedSeat() {

    }

    public OrderedSeat(Seat seat){
        this.seat = seat;
    }

    public void setTicket(Seat ticket){
        this.seat = ticket;
    }

    public Seat getTicket(){
        return seat;
    }
}
