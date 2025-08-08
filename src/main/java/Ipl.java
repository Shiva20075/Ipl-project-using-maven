
import model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Ipl {
    
    // MAIN METHOD
    public static void main(String[] args)  {

        Scanner s = new Scanner(System.in);
        Ipl ipl = new Ipl ();
        String filePathOfMatches = "/home/p/Downloads/archive/matches.csv";
        String filePathOfDeliveries = "/home/p/Downloads/archive/deliveries.csv";
        ArrayList <Matches> matchData = new ArrayList<>();
        ArrayList<Deliveries> deliveriesData = new ArrayList<>();

        try {
            ArrayList<String> matches = new ArrayList<>(Files.readAllLines(Paths.get(filePathOfMatches)));
            for (int i = 1; i < matches.size(); i++) {

                String[] data = matches.get(i).trim().split(",");

                    Matches match = new Matches(); // creating Matches object
                    match.setId(parseInt(data[0]));
                    match.setSeason(parseInt(data[1]));
                    match.setCity(data[2]);
                    match.setDate(data[3]);
                    match.setTeam1(data[4]);
                    match.setTeam2(data[5]);
                    match.setTossWinner(data[6]);
                    match.setTossDecision(data[7]);
                    match.setResult(data[8]);
                    match.setDlApplied(data[9]);
                    match.setWinner(data[10]);
                    match.setWinByRuns(Integer.parseInt(data[11]));
                    match.setWinByWickets(Integer.parseInt(data[12]));
                    match.setPlayerOfMatch(data[13]);
                    match.setVenue(data[14]);

                    if (data.length >= 16) {
                    if (!data[15].isEmpty()) {
                        match.setUmpire1(data[15]);
                    }

                    if (!data[16].isEmpty()) {
                        match.setUmpire2(data[16]);
                    }
                    matchData.add(match);
                }
            }

        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        try {
            ArrayList<String> deliveries = new ArrayList<>(Files.readAllLines(Paths.get(filePathOfDeliveries)));
            for (int i = 1; i < deliveries.size(); i++) {

                String[] deliverie = deliveries.get(i).split(",");
                Deliveries data = new Deliveries();

                data.setMatchId(Integer.parseInt(deliverie[0]));
                data.setInings(Integer.parseInt(deliverie[1]));
                data.setBattingTeam(deliverie[2]);
                data.setBowlingTeam(deliverie[3]);
                data.setOver(Integer.parseInt(deliverie[4]));
                data.setBall(Integer.parseInt(deliverie[5]));
                data.setBatsman(deliverie[6]);
                data.setNotStriker(deliverie[7]);
                data.setBowler(deliverie[8]);
                data.setSuperOver(Integer.parseInt(deliverie[9]));
                data.setWideRuns(Integer.parseInt(deliverie[10]));
                data.setByeRuns(Integer.parseInt(deliverie[11]));
                data.setLegBye(Integer.parseInt(deliverie[12]));
                data.setNoBall(Integer.parseInt(deliverie[13]));
                data.setPenaltyRuns(Integer.parseInt(deliverie[14]));
                data.setBatsManRuns(Integer.parseInt(deliverie[15]));
                data.setExtraRuns(Integer.parseInt(deliverie[16]));
                data.setTotalRuns(Integer.parseInt((deliverie[17])));

                if (deliverie.length >= 19) {
                    data.setPlayerDismissed(deliverie[18]);
                }
                deliveriesData.add(data);
            }

        } catch (IOException e) {
            System.out.println("CANNOT FIND FILE");
        }

        showMatchesPerYear(matchData);
        showMatchesWonByPerTeam(matchData);
        showMatchesWonByTeamPerYear(matchData);
        showExtraRuns(matchData,deliveriesData);
        showtopEconomicalBowlers(deliveriesData);
        showIplMatch(deliveriesData,matchData);


    }

    private static void showIplMatch(ArrayList<Deliveries> deliveriesData,ArrayList<Matches> matchData) {

        Scanner s = new Scanner(System.in);
        Map <String, StrikerInfo> batterInfo = new HashMap<>();
        Map <String, BowlersInfo> bowlerInfo = new HashMap<>();
        Map <Integer, OversData> oversInfo = new HashMap<>();
        Map <String , Integer> highrstRuns = new HashMap<>();
        Map <String, Integer> playersSixesCount = new HashMap<>();
        Map <String,Integer> playersFoursCount = new HashMap<>();
        Map <String,Integer> highestWicketTaker = new HashMap<>();

        System.out.print("ENTER MATCH ID TO FIND DETAILS -> ");
        int matchId = s.nextInt();
        boolean intro = false;
        int sixCount = 0;
        int fourCount = 0;
        int extraRuns = 0;
        String highestRunsScorer = "Null";
        String mostSixesScorer = "Null";
        String mostFoursScorer = "Null";
        String mostWicketsTaker = "Null";
        int highestRuns = 0;
        int mostNoOfSixesByAPlayer = 0;
        int mostNoOFFoursByAPlayer = 0;
        int mostWicketsTaken = 0;

        for (Deliveries deliveriesDatas : deliveriesData) {
            if (deliveriesDatas.getMatchId() == matchId){
                if (!intro){
                    System.out.println(" -------------------------- WELCOME TO IPL MATCH -> " + deliveriesDatas.getMatchId() + " ----------------------------------");
                    System.out.println( " <------ " + deliveriesDatas.getBatsman() + " and " + deliveriesDatas.getNotStriker() + " are at the crease " + deliveriesDatas.getBatsman() + " is on Strike " + deliveriesDatas.getBowler() + " Will open attack " + "----->" );
                    intro = true;
                }
                if ( deliveriesDatas.getWideRuns() > 0 ){
                    System.out.println(deliveriesDatas.getOver() + "." + deliveriesDatas.getBall()+ ")" + " WIDE BALL BY " + deliveriesDatas.getBowler() + " EXTRA RUNS -> 1");
                }else if(deliveriesDatas.getNoBall() > 0) {
                    System.out.println(deliveriesDatas.getOver()  + "." + deliveriesDatas.getBall()+ ")" + " NO BALL BY " + deliveriesDatas.getBowler()  + " EXTRA RUNS -> 1 AND FREE HIT" );
                }else {
                    System.out.println(deliveriesDatas.getOver() + "." + (deliveriesDatas.getBall()) + ") " + deliveriesDatas.getBowler()  + " to " + deliveriesDatas.getBatsman() + " -> " + deliveriesDatas.getBatsManRuns());
                }

                BowlersInfo bowlersInfo = bowlerInfo.get( deliveriesDatas.getBowler());
                StrikerInfo strikerInfo = batterInfo.get(deliveriesDatas.getBatsman());

                if (batterInfo.containsKey(deliveriesDatas.getBatsman())){
                    strikerInfo.setRunsScored(strikerInfo.getRunsScored()+deliveriesDatas.getBatsManRuns());
                    strikerInfo.setBallPlayed(strikerInfo.getBallPlayed()+1);

                }else{
                    batterInfo.put(deliveriesDatas.getBatsman(),new StrikerInfo(deliveriesDatas.getBatsManRuns(),1));
                }
// OVERS
                if (oversInfo.containsKey(deliveriesDatas.getOver())){

                }else {
                    // oversInfo.put(over,new OversData(over,BatsManruns));
                }
// BOWLERS
                if (bowlerInfo.containsKey( deliveriesDatas.getBowler())){

                    if (deliveriesDatas.getWideRuns() >0 || deliveriesDatas.getNoBall() > 0){
                        bowlersInfo.setBowlerRuns(bowlersInfo.getBowlerRuns()+1);
                    }else{
                        bowlersInfo.setBallsBowled(bowlersInfo.getBallsBowled()+1);
                        bowlersInfo.setBowlerRuns((int) (bowlersInfo.getBowlerRuns()+deliveriesDatas.getBatsManRuns()));
                    }

                }else{
                    bowlerInfo.put( deliveriesDatas.getBowler(),new BowlersInfo((int) deliveriesDatas.getBatsManRuns(),1));
                }
//SixesAndFours
                if (deliveriesDatas.getBatsManRuns() == 6){
                    sixCount++;
                }else if (deliveriesDatas.getBatsManRuns() == 4){
                    fourCount++;
                }
// ExtraRuns
                if ( deliveriesDatas.getExtraRuns() > 0){
                    extraRuns += deliveriesDatas.getExtraRuns();
                }

//Initilizing highestRuns map
                if (highrstRuns.containsKey(deliveriesDatas.getBatsman())){
                    highrstRuns.put(deliveriesDatas.getBatsman(),highrstRuns.get(deliveriesDatas.getBatsman()) + deliveriesDatas.getBatsManRuns());
                }else{
                    highrstRuns.put(deliveriesDatas.getBatsman(),0);
                }
//Inititlizing mostSixesByAPlayer Map

                if (playersSixesCount.containsKey(deliveriesDatas.getBatsman())){
                    if (deliveriesDatas.getBatsManRuns() == 6){
                        playersSixesCount.put(deliveriesDatas.getBatsman(), playersSixesCount.get(deliveriesDatas.getBatsman()) + 1);
                    }
                }else{
                    playersSixesCount.put(deliveriesDatas.getBatsman(),0);
                }
//Initilizing mostFoursByAplayer Map

                if (playersFoursCount.containsKey(deliveriesDatas.getBatsman())){
                    if (deliveriesDatas.getBatsManRuns() == 4){
                        playersFoursCount.put(deliveriesDatas.getBatsman(), playersFoursCount.get(deliveriesDatas.getBatsman()) + 1);
                    }
                }else{
                    playersFoursCount.put(deliveriesDatas.getBatsman(),0);
                }

//Initilizing highestWicketTaker map
                if(highestWicketTaker.containsKey(deliveriesDatas.getBowler())){
                    if(deliveriesDatas.getPlayerDismissed() != null){
                        highestWicketTaker.put(deliveriesDatas.getBowler(),highestWicketTaker.get(deliveriesDatas.getBowler()) + 1);
                    }
                }else{
                    highestWicketTaker.put(deliveriesDatas.getBowler(),0);
                }
            }
        }

//Calculating highestRuns
        for (String key : highrstRuns.keySet()){
            int runs = highrstRuns.get(key);
            if (runs > highestRuns){
                highestRuns = runs;
                highestRunsScorer = key;
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println(" TOP SCORER OF THE MATCH -> " + highestRunsScorer + " RUNS -> " + highestRuns);

//calculating highestWicketTaker
        for (String key : highestWicketTaker.keySet()){
            int wickets = highestWicketTaker.get(key);
            if (wickets > mostWicketsTaken){
                mostWicketsTaken = wickets;
                mostWicketsTaker = key;
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println(" TOP WICKET TAKER -> " + mostWicketsTaker + " | WICKETS -> " + mostWicketsTaken);

// playerOfTheMatch
        for (Matches matchDatas : matchData) {
            if (matchDatas.getId() == matchId){
                System.out.println("-------------------------------------------------------------------------------------------------------------");
                System.out.println(" PLAYER OF THE MATCH -> " + matchDatas.getPlayerOfMatch());
            }
        }

// MatchWinners
        for (Matches matchDatas : matchData) {
            if (matchDatas.getId() == matchId){
                System.out.println("-------------------------------------------------------------------------------------------------------------");
                System.out.println(" TEAM WON -> " + matchDatas.getWinner());
            }
        }

//calculating Most sixes scored by a player
        for (String key : playersSixesCount.keySet()) {
            int sixes = playersSixesCount.get(key);
            if (sixes > mostNoOfSixesByAPlayer) {
                mostNoOfSixesByAPlayer = sixes;
                mostSixesScorer = key;
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("MOST SIXES BY -> " + mostSixesScorer + " | NO OF SIXES -> " + mostNoOfSixesByAPlayer);

//calculating most fours scored by a player
        for (String key : playersFoursCount.keySet()){
            int fours = playersFoursCount.get(key);
            if (fours > mostNoOFFoursByAPlayer){
                mostFoursScorer = key;
                mostNoOFFoursByAPlayer = fours;
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("MOST FOURS BY -> " + mostFoursScorer + " | NO OF SIXES -> " + mostNoOFFoursByAPlayer);

// Printing six count and fours couont
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("TOTAL NO OF SIXES -> " + sixCount + " | TOTAL NO OF FOURS -> " + fourCount);

//Printing ExtraRuns
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("TOTAL EXTRA RUNS IN A MATCH -> " + extraRuns);
        System.out.println("-------------------------------------------------------------------------------------------------------------");

    }


    private static void showtopEconomicalBowlers(ArrayList<Deliveries> deliveriesData) {

        Map<String, Economy> topEconomicalBowlers = new HashMap<>();
        for (Deliveries deliveriesDatas : deliveriesData) {
            if (topEconomicalBowlers.containsKey(deliveriesDatas.getBowler())) {
                Economy bowlerEconomy = topEconomicalBowlers.get(deliveriesDatas.getBowler());
                bowlerEconomy.setBalls(bowlerEconomy.getBalls() + 1);
                bowlerEconomy.setRuns(bowlerEconomy.getRuns()+deliveriesDatas.getTotalRuns());
            }else {
                topEconomicalBowlers.put(deliveriesDatas.getBowler(), new Economy(deliveriesDatas.getTotalRuns(),1));
            }
        }
        System.out.println("BOWLERS ECONOMY OVERALL SEASONS -> \n");
        for (String key : topEconomicalBowlers.keySet()) {
            System.out.println("BOWLER -> " + key + " | ECONOMY -> " + topEconomicalBowlers.get(key));
        }
    }

    private static void showExtraRuns(ArrayList<Matches> matchData, ArrayList<Deliveries> deliveriesData) {
        Scanner s = new Scanner(System.in);
        Map<String,Integer> extraRunsInYear = new HashMap<>();
        System.out.println("ENTER A YEAR TO FIND TOTAL EXTRA RUNS GIVEN BY TEAM ");
        int year  = s.nextInt();
        int firstId = 0;
        int lastId = 0;

        for ( int i = 0; i < matchData.size(); i++) {
            if (matchData.get(i).getSeason() == year) {
                firstId = matchData.get(i).getId();
                break;
            }
        }
        for (int i = matchData.size() - 1; i >= 0; i-- ) {
            if (matchData.get(i).getSeason() == year) {
                lastId = matchData.get(i).getId();
                break;
            }
        }
        for (Deliveries deliveriesDatas : deliveriesData) {
            if (deliveriesDatas.getMatchId() >= firstId && deliveriesDatas.getMatchId() <= lastId) {
                if (extraRunsInYear.containsKey(deliveriesDatas.getBowlingTeam())) {
                    extraRunsInYear.put(deliveriesDatas.getBowlingTeam(), extraRunsInYear.get(deliveriesDatas.getBowlingTeam()) + deliveriesDatas.getByeRuns() + deliveriesDatas.getNoBall() + deliveriesDatas.getLegBye() + deliveriesDatas.getWideRuns());
                }else {
                    extraRunsInYear.put(deliveriesDatas.getBowlingTeam(),deliveriesDatas.getByeRuns() + deliveriesDatas.getNoBall() + deliveriesDatas.getLegBye() + deliveriesDatas.getWideRuns());
                }
            }
        }
        System.out.println("EXTRA RUNS IN -> " + year);
        for (String key : extraRunsInYear.keySet()) {
            System.out.println("TEAM -> " + key + " | EXTRA RUNS -> " + extraRunsInYear.get(key));
        }
    }

    private static void showMatchesWonByTeamPerYear(ArrayList<Matches> matchData) {
        Map<Integer,Map<String,Integer>> seasonData = new HashMap<>();
        for (Matches matchDatas : matchData) {

            if (seasonData.containsKey(matchDatas.getSeason())) {
                Map<String,Integer> TeamsWon = seasonData.get(matchDatas.getSeason());
                if (TeamsWon.containsKey((matchDatas.getWinner()))){
                    TeamsWon.put(matchDatas.getWinner(),TeamsWon.get(matchDatas.getWinner()) + 1);
                }else {
                    TeamsWon.put(matchDatas.getWinner(),1);
                }

            }else {
                Map<String,Integer> TeamsWon = new HashMap<>();
                TeamsWon.put(matchDatas.getWinner(),1);
                seasonData.put(matchDatas.getSeason(),TeamsWon);
            }

        }
        for (Map.Entry<Integer, Map<String, Integer>> outerEntry : seasonData.entrySet()) {
            Integer season = outerEntry.getKey();
            Map<String, Integer> TeamsWon = outerEntry.getValue();
            System.out.println("WINS IN YEAR: " + season);

            for (Map.Entry<String, Integer> innerEntry : TeamsWon.entrySet()) {
                String team = innerEntry.getKey();
                Integer wins = innerEntry.getValue();
                System.out.println(" TEAM: " + team + " | WINS: " + wins);
            }
        }
    }

    private static void showMatchesWonByPerTeam(ArrayList<Matches> matchData) {

        Map<String,Integer> victoriesOfEacIPLTeam = new HashMap<>();
        for (Matches matchDatas : matchData) {
            if (victoriesOfEacIPLTeam.containsKey(matchDatas.getWinner())) {
                victoriesOfEacIPLTeam.put(matchDatas.getWinner(), victoriesOfEacIPLTeam.get(matchDatas.getWinner()) + 1);
            } else {
                victoriesOfEacIPLTeam.put(matchDatas.getWinner(), 1);
            }
        }
        for (String key : victoriesOfEacIPLTeam.keySet()){
            System.out.println("TeamName -> " + key + " |  VICTORIES " + victoriesOfEacIPLTeam.get(key));
        }
    }

    private static void showMatchesPerYear(ArrayList<Matches> matchData) {
        Map<Integer, Integer> TotalNumberOfMatchesInAllSeasons = new HashMap<>();

        for (Matches matchDatas : matchData) {
            if (TotalNumberOfMatchesInAllSeasons.containsKey(matchDatas.getSeason())) {
                TotalNumberOfMatchesInAllSeasons.put(matchDatas.getSeason(), TotalNumberOfMatchesInAllSeasons.get(matchDatas.getSeason()) + 1);
            } else {
                TotalNumberOfMatchesInAllSeasons.put(matchDatas.getSeason(), 1);
            }
        }
        for (Integer key : TotalNumberOfMatchesInAllSeasons.keySet()) {
            System.out.println("YEAR -> " + key + " |  MATCHES -> " + TotalNumberOfMatchesInAllSeasons.get(key));
        }
    }
}