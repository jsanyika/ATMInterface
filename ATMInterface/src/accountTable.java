import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class accountTable {

    private Scanner scanner = null;
    private ArrayList<String[]> table = new ArrayList<>();


    public accountTable(){
        File tableFile = new File("C:\\Pyramid\\Projects\\ATMInterface\\src\\AccountTable.csv");
        try {
            scanner = new Scanner(tableFile);
            getTable();
        } catch (IOException ignored){
        }

    }

    public void getTable(){
        while(scanner.hasNextLine()){
            table.add(scanner.nextLine().split(","));
        }
    }

    public void printTable(){
        System.out.println("uid, checking, savings");
        for(String[] row : table) {
            System.out.println(row[0] + ", " + row[1] + ", " + row[2]);
        }
    }

    public void printBalance(String id) {
        while(this.scanner.hasNextLine()){
            String[] row = this.scanner.nextLine().split(",");
            if(row[0].equals(id)){
                System.out.println("Checking: " + row[1]);
                System.out.println("Savings: " + row[2]);
            }
        }
    }

    public void withdraw(String id, int amount){
        Scanner newScanner = new Scanner("C:\\Pyramid\\Projects\\ATMInterface\\src\\AccountTable.csv");
        while(newScanner.hasNextLine()){
            String[] row = newScanner.nextLine().split(",");
            if(row[0].equals(id)){
            }
        }
    }
}
