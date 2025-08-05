package model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Matches {

    private int id;
    private int season;
    private String city;
    private String date;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String result;
    private String dlApplied;
    private String winner;
    private int winByRuns;
    private int winByWickets;
    private String playerOfMatch;
    private String venue;
    private String umpire1;
    private String umpire2;
    private String umpire3;

    public String toString(){
        return (" Id = " + this.id + " Season = " + this.season + " City = " + this.city + " Team 1 = " + this.team1 +  " Team2 =  " + this.team2 + " TossWiner = " +  this.tossWinner + " TossDecision = " + " Result = " + this.result + " DlApplied  = " + this.dlApplied +  " Winner = " + this.winner + " PlayerOfMatch = " + this.playerOfMatch + "  venue = " +  this.venue + "  Umpire1 = " +   this.umpire1 + " Umpire2  = " + this.umpire2 );
    }
}
