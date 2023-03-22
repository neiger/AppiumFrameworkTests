package general;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ErrorsManager {

    public static void errNExpManager(Exception e){
        try {
            String error = e.toString();
            boolean append = true;
            File txtObj = new File("logging.txt");
            FileWriter fileWriter = new FileWriter("logging.txt", append);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
            String dateAndTime = LocalDateTime.now().format(dateTimeFormatter);

            if (txtObj.createNewFile()) {
                System.out.println("File created. " + txtObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            fileWriter.write("\n"+"\nDate and Time: "+dateAndTime);
            fileWriter.write("\nERROR MESSAGE LOG "+ "\n" + error);
            fileWriter.close();
        } catch (Exception i) {
            System.out.println("An error has occurred\n" + i);
        }
    }

}
