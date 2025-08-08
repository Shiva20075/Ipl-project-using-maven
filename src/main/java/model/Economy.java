package model;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter

public class Economy {
        Double runs;
        int balls;

    public Economy(double runs, int  balls){
        this.balls = balls;
        this.runs = runs;

    }
    public String toString() {
        return (" | ECONOMY -> " + (this.runs/this.balls)*6);
    }
}
