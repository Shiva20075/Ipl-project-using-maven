package model;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BowlersInfo {

    public int bowlerRuns;
    private int ballsBowled;

    public BowlersInfo(int bowlerRuns, int ballsBowled) {
        this.bowlerRuns = bowlerRuns;
        this.ballsBowled = ballsBowled;
    }
}
