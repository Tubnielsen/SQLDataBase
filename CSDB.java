import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author Tobias Nielsen tobia17@student.sdu.dk
 */
public class CSDB {
    
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e);
        }

        Scanner s = new Scanner(System.in);
        int a = 0;
        int x;
        String url = "jdbc:postgresql://horton.elephantsql.com:5432/iqetbqai";
        String username = "iqetbqai";
        String password = "Tlr1_iSkJCWTYY64Hz1CqZNQCdJeVphF";

        

        System.out.println("You've connected to the Counter-Strike Database!");
        System.out.println(" ");
        System.out.println("To get a list of commands, type: 1");
        
        try {
            Connection db = DriverManager.getConnection(url, username, password);
        while (a != 6) {
            System.out.println(" ");
            System.out.print("Waiting for input:");
            a = s.nextInt();
            if (a == 1){
                System.out.println("List of Commands:");

                System.out.println("2 - Lists all coaches and their team.");
                System.out.println("3 - Lists all players and coaches that are on a team, that have won 1 or more tournaments.");
                System.out.println("4 - Lists all teams and number of players in them.");
                System.out.println("5 - Asks for a number, and lists all tournaments that have at least that many participating teams.");
                System.out.println("6 - Exits program.");
            }

            if (a == 2){
                try {
                    Statement st1 = db.createStatement(); 
                    String q1 = "SELECT Person.Name, Coaches.TeamName FROM Person, Coaches WHERE Coaches.CoachEmail = Person.Email";
                    ResultSet rs1 = st1.executeQuery(q1);

                    while (rs1.next()) {    
                        System.out.print(rs1.getString(1) + " ");
                        System.out.println("is coaching " + rs1.getString(2));
                    }
                    rs1.close();
                    st1.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (a == 3){
                try {
                    Statement st2 = db.createStatement(); 
                    String q2 = "SELECT Nickname FROM Person WHERE Person.Email IN (SELECT PlayerEmail FROM Player WHERE teamName IN (SELECT Winner FROM Tournament))";
                    ResultSet rs2 = st2.executeQuery(q2);

                    while (rs2.next()) {    
                        System.out.println(rs2.getString(1) + " ");
                    }
                    rs2.close();
                    st2.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (a == 4){
                try {
                    Statement st3 = db.createStatement(); 
                    String q3 = "SELECT TeamName, COUNT (TeamName) FROM Player GROUP BY TeamName";
                    ResultSet rs3 = st3.executeQuery(q3);

                    while (rs3.next()) {    
                        System.out.print(rs3.getString(1) + " ");
                        System.out.println("has " + rs3.getInt(2) + " players.");
                    }
                    rs3.close();
                    st3.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (a == 5){
                Scanner s2 = new Scanner(System.in);
                System.out.print("How many participating teams shall the tournament atleast have?: ");
                x = s2.nextInt();
                try {
                    Statement st4 = db.createStatement(); 
                    String q4 = "SELECT TournamentName FROM (SELECT TournamentName, COUNT (TournamentName) FROM Attends GROUP BY TournamentName) AS foo WHERE foo.count >= '" + x + "'";
                    ResultSet rs4 = st4.executeQuery(q4);
                    int resultCounter = 0;
                    while (rs4.next()) {    
                        System.out.println(rs4.getString(1) + " ");
                        resultCounter++;
                    }
                    if (resultCounter == 0){
                        System.out.println("There's no tournaments with the requested minimum teams attending.");
                        }
                    
                    rs4.close();
                    st4.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}


