import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, CsvValidationException {

        Scanner scanner = new Scanner(System.in);



        CSVReader csvReader = new CSVReader(new FileReader("C:\\Pyramid\\Projects\\ATMInterface\\src\\LoginTable.csv"));

        String[] row = csvReader.readNext();
        for(String data : row){
            System.out.println(data);
        }





//        System.out.print("Username: ");
//        String username = scanner.nextLine();
//        System.out.print("\nPassword: ");
//        String password = scanner.nextLine();
//
//        loginTable login = new loginTable();
//        String id = login.login(username, password);
//
//        accountTable account = new accountTable();
//        account.printBalance(id);
//
//        account.printTable();


    }
}
