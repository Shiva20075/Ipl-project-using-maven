package model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Deliveries {

    private int matchId;
    private int inings;
    private String battingTeam;
    private String bowlingTeam;
    private int over;
    private int ball;
    private String batsman;
    private String notStriker;
    private String bowler;
    private int superOver;
    private int wideRuns;
    private int byeRuns;
    private int legBye;
    private int noBall;
    private int penaltyRuns;
    private int batsManRuns;
    private int extraRuns;
    private double totalRuns;

}
