import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LOGIC {
    private static final List<String> listFileName = new ArrayList<>();
    private static final ArrayList<String> folderPaths = new ArrayList<>();

    private static final String createdDate = "";

    LOGIC(ArrayList<FileInfo> fileInfoArray, boolean print) throws IOException {

        //(\\130.171.180.79\Acronis\Production\SMT)
        //(\\130.171.180.79\Acronis)
        fileInfoArray.clear();
        folderPaths.add("\\\\130.171.180.79\\Acronis");
        folderPaths.add("\\\\130.171.180.79\\AcronisServer");
        String communicate = "Loading...";
        if (!print) communicate = "Searching...";
        int size = folderPaths.size();
        for (int i = 0; i < size; i++) {
            try {
                String filePath = folderPaths.get(i);
                File folder = new File(filePath);
                File[] contentOfFolder = folder.listFiles();

                System.out.println(communicate);
                assert contentOfFolder != null;
                for (File file : contentOfFolder) {
                    if (file.isFile()) {
                        if (file.getName().contains(".tibx") || file.getName().contains(".tib")) {
                            Path path = Paths.get(file.getAbsolutePath());
                            listFileName.add(file.getName());


                            FileInfo fileInfo = new FileInfo(
                                    Fileblob.hostnameFinder(file.getName()),
                                    Fileblob.versionFinder(file.getName()),
                                    Timestamp.valueOf(Fileblob.dateofModifyTimestamp(file, createdDate)),
                                    Files.size(path),
                                    file.getAbsolutePath()
                            );

                            fileInfoArray.add(fileInfo);
                            System.out.println(file.getPath());
                        } else {
                            System.out.println("bababuj" + file.getPath());
                        }
                    } else if (file.isDirectory() &&
                            !file.getPath().equals("\\\\130.171.180.79\\Acronis\\UPS") &&
                            !file.getPath().equals("\\\\130.171.180.79\\Acronis\\FIS")) {
                        folderPaths.add(file.getAbsolutePath());
                        System.out.println("opening directory: " + file.getPath() + "...");
                        size++;
                    }

                }
            } catch (NullPointerException e) {
                System.out.println("Check your internet conection and try again");
                throw e;
            }
        }
        if (print) {
            for (FileInfo str : fileInfoArray) {
                System.out.println(str);
            }
        }
        System.out.println("Proccess finished");
    }

    public static void saveRaport(ArrayList<FileInfo> fileInfoArray, String fileName) throws IOException {
        if(!fileInfoArray.isEmpty()){
            FileWriter writer = new FileWriter(fileName);
            for (FileInfo str : fileInfoArray) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            System.out.println("Save was succesfull");
        }else{
            System.out.println("nothing to save");
        }
    }
}
