class Clock {

    int hours = 12;
    int minutes = 0;

    void next() {
        if (this.hours < 12){
            if (this.minutes < 59){
                this.minutes += 1;
            }
            else{
                this.hours += 1;
                this.minutes = 0;
            }
        }
        else if (this.hours == 12){
            if (this.minutes < 59){
                this.minutes += 1;
            }
            else {
                this.hours = 1;
                this.minutes = 0;
            }
        }
        System.out.println(this.hours + ":" + this.minutes);
        // implement me
    }

    public static void main(String[] args) {
        Clock clock = new Clock();
        clock.minutes = 59;
        clock.hours = 12;
        clock.next();
    }
}