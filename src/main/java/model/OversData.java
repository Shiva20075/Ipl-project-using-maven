package model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

public class OversData {

    private int over;
    private List<Integer> runsPerBall;

    public OversData(int over, List<Integer> runsPerBall) {
        this.over = over;
        this.runsPerBall = runsPerBall;
    }
}
