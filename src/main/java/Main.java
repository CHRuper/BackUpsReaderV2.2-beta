
import java.io.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws Exception {
//~~~~~~~~~~~~~~~~~~~~~~~~WELCOME~~~~~~~~~~~~~~~~~~~~~~~~
        String fileName = "backup_raport_output.txt";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        ArrayList<FileInfo> fileInfoArray = new ArrayList<>();
        boolean work = true;

        System.out.println("Starting program... ");

        while (work) {
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~save information about backups to array~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            if (fileInfoArray.isEmpty()) {
                new LOGIC(fileInfoArray, true);
            } else {
                for (FileInfo fileInfo : fileInfoArray) {
                    System.out.println(fileInfo);
                }
            }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~save array values to the Database~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            LOGIC.saveRaport(fileInfoArray, fileName);
            DatabaseConection conection = new DatabaseConection();
            conection.connect();
            conection.deleteAll();
            for (FileInfo str : fileInfoArray)
                try {
                    conection.insertToDataBase(str);
                    System.out.println("saving proccess...");
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                }
            System.out.println("All save");
            work = false;

        }
    }
}
