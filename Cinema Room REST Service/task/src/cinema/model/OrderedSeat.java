package cinema.model;

import cinema.model.Seat;
import org.springframework.core.annotation.Order;


public class OrderedSeat {

    private Seat seat;

    public OrderedSeat(Seat seat){
        this.seat = seat;
    }

    public void setSeat(Seat seat){
        this.seat = seat;
    }

    public Seat getSeat(){
        return seat;
    }
}
