package model;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StrikerInfo {

    private double runsScored;
    private int ballPlayed;

    public StrikerInfo(double runsScored, int ballPlayed){

        this.runsScored = runsScored;
        this.ballPlayed = ballPlayed;
    }

    public String toString() {

        return ("BALLS PLAYED -> " + ballPlayed + " RUNS SCORED -> " + runsScored);
    }
}
