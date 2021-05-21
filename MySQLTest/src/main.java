import java.sql.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws SQLException {
        Statement statement = null;
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/atminterface";
            conn = DriverManager.getConnection(url, "root", "");
            statement = conn.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }

        int uid = login(conn);
        while (true) {

            // stay in login prompt until successful login
            int userid = main.login(conn);

            // stay in main menu until user quits
            if (userid > -1) {
                main.printUserMenu(conn, userid);
            }

        }
//        System.out.println(getCheckings(conn, uid));
//        withdraw(conn, uid);
//        System.out.println(getCheckings(conn,uid));
//        deposit(conn, uid);
//        System.out.println(getCheckings(conn, uid));
//        deposit(conn, uid);
//        System.out.println(getCheckings(conn, uid));
//        transfer(conn, uid);
        //conn.close();
        
    }

    public static void transfer(Connection conn, int senderUID) throws SQLException {
        Scanner scanner = new Scanner (System.in);
        System.out.println("What is the username of who you would like to send money to?");
        String reciever = scanner.nextLine();
        System.out.println("Amount?");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Note: ");
        String memo = scanner.nextLine();
        int recieverUID = getUID(conn, reciever);
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE account SET checkings = " + (getCheckings(conn, senderUID) - amount) + " WHERE uid = '" + senderUID + "'" );
        statement.executeUpdate("UPDATE account SET checkings = " + (getCheckings(conn, recieverUID) + amount) + " WHERE uid = '" + recieverUID + "'" );
        statement.execute("INSERT INTO transfer (sender, reciever, amount, memo) VALUES (" + senderUID + ", " + recieverUID + ", " + amount + ", '" + memo +"')");

    }

    public static void createUser(Connection conn) throws SQLException {
        Scanner scanner = new Scanner (System.in);
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        System.out.print("Checkings deposit: ");
        int checkingsDeposit = scanner.nextInt();
        System.out.println("Savings deposit: ");
        int savingsDeposit = scanner.nextInt();
        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO login (username, password) " +
                "VALUES ('" + username + "', '" + password + "')");
        int uid = getUID(conn, username);
        statement.execute("INSERT INTO account(uid, checkings, savings) " +
                "VALUES ('" + uid + "', '" + checkingsDeposit + "', '" + savingsDeposit + "')");
    }

//    public static int login(Connection conn) throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("username: ");
//        String username = scanner.nextLine();
//        System.out.print("password: ");
//        String password = scanner.nextLine();
//        Statement statement = conn.createStatement();
//        ResultSet rs = statement.executeQuery("SELECT * FROM Login WHERE username = '" + username + "'");
//        rs.next();
//        String userPassword = rs.getString("password");
//        if (password.equals(userPassword)) {
//            return rs.getInt("uid");
//        }
//        return 0;
//    }

    public static int login(Connection conn) throws SQLException {

        System.out.println("========== Welcome to Pyramid Bank ATM ===========\n");
        Scanner scanner = new Scanner(System.in);

        String userPassword = null;
        String password = null;

        do {
            System.out.print("Please enter username: ");
            String username = scanner.nextLine();
            System.out.print("Please enter password: ");
            password = scanner.nextLine();



            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Login WHERE username = '" + username + "'");
            try {
                rs.next();
                userPassword = rs.getString("password");
                if (password.equals(userPassword)) {
                    return rs.getInt("uid");
                } else {
                    System.out.println("Incorrect Username/Password combination. " +
                            "Please try again");
                }
            } catch (Exception ignore){}

        } while (!password.equals(userPassword));
        {

        }

        return -1;

    }
    public static boolean withdraw(Connection conn, int uid) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Amount: ");
        int amount = scanner.nextInt();
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE account SET checkings = " + (getCheckings(conn, uid) - amount) + " WHERE uid = '" + uid + "'" );

        return true;
    }

    public static boolean deposit(Connection conn, int uid) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Checkings(1) or Savings(2)");
        int choice = scanner.nextInt();
        System.out.println("Amount: ");
        try {
            int amount = scanner.nextInt();
            Statement statement = conn.createStatement();
            switch (choice) {
                case (1):
                    statement.executeUpdate("UPDATE account SET checkings = " + (getCheckings(conn, uid) + amount) + " WHERE uid = '" + uid + "'");
                    break;
                case (2):
                    statement.executeUpdate("UPDATE account SET savings = " + (getSavings(conn, uid) + amount) + " WHERE uid = '" + uid + "'");
                    break;
            }
        } catch(Exception e) {
            System.out.println("Please enter a number");
            return false;

        }

        return true;
    }

    public static void printUserMenu(Connection conn, int uid) throws SQLException {


        int choice;

        Scanner userChoice = new Scanner (System.in);

        // user menu
        do {

            System.out.println("Available Transactions\n");
            System.out.println("  1) Withdraw");
            System.out.println("  2) Deposit");
            System.out.println("  3) Transfer");
            System.out.println("  4) Quit");
            System.out.println();
            System.out.print("Please choose a transaction: ");
            choice = userChoice.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please choose 1-4.");
            }

        } while (choice < 1 || choice > 4);

        // process the choice
        switch (choice) {

            case 1:
                main.withdraw(conn, uid);
                break;
            case 2:
                main.deposit(conn, uid);
                break;
            case 3:
                main.transfer(conn, uid);
                break;
            case 4:
                userChoice.nextLine();
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 4) {
            main.printUserMenu(conn, uid);
        }

    }

    public static int getSavings(Connection conn, int uid) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT savings FROM account WHERE uid = '" + uid + "'");
        rs.next();
        return rs.getInt("savings");
    }
    public static int getCheckings(Connection conn, int uid) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT checkings FROM account WHERE uid = '" + uid + "'");
        rs.next();
        return rs.getInt("checkings");

    }

    public static int getUID(Connection conn, String username) throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT uid FROM login WHERE username = '" + username + "'");
        rs.next();
        return rs.getInt("uid");
    }




}
