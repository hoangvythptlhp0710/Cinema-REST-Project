package cinema.model;

import java.util.UUID;


public class OrderedSeat {

    private UUID token;
    private Seat ticket;


    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public OrderedSeat(UUID token, Seat ticket){
        this.ticket = ticket;
        this.token = token;
    }

    public void setTicket(Seat ticket){
        this.ticket = ticket;
    }

    public Seat getTicket(){
        return ticket;
    }

}
