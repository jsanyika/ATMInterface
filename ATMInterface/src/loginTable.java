import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class loginTable extends Table{

    CSVReader reader = new CSVReader(new FileReader("C:\\Pyramid\\Projects\\ATMInterface\\src\\LoginTable.csv"));
    private Scanner scanner = null;

    public loginTable() throws FileNotFoundException {
        try {
            File tableFile = new File("C:\\Pyramid\\Projects\\ATMInterface\\src\\LoginTable.csv");
            this.scanner = new Scanner(tableFile);
            FileWriter fileWriter = new FileWriter(tableFile);
        } catch(FileNotFoundException ignored){} catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String login(String username, String password) {
        while(this.scanner.hasNextLine()){
            String[] row = this.scanner.nextLine().split(",");
            if (row[1].equals(username) && row[2].equals(password)){
                return row[0];
            }
        }
        return null;
    }
}
