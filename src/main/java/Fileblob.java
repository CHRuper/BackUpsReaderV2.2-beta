import java.io.File;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fileblob {
    public static String backupType = "";


    public static void DivideAndName(List<String> list, List<String> listAcronis,
                                     List<String> listTrueImage) {
        String extension = "";

        for (String s : list) {
            if (s.contains(".")) {
                String[] parts = s.split("\\.");
                extension = parts[parts.length - 1];
            } else {
                //System.out.println(list.get(i));
                //throw new Exception("File name: '" + list.get(i) + "' does not contain extension");
                System.out.println("File name: '" + s + "' does not contain extension");
            }
            if (extension.equals("tibx")) {
                listAcronis.add(s);
            } else {
                if (extension.equals("tib")) {
                    listTrueImage.add(s);
                }
            }

        }
        System.out.println("Acronis: ");
        for (String str : listAcronis)
            System.out.println(str);
        System.out.println("True Image: ");
        for (String str : listTrueImage)
            System.out.println(str);
    }

    public static String hostnameFinder(String filename) {
        if (filename.contains(".tibx")) {
            backupType = "Acronis";
            Pattern compiledPattern = Pattern.compile("-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{13}");
            Matcher matcher = compiledPattern.matcher(filename);
            if (matcher.find()) {
                filename = filename.replaceAll("-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{13}-\\d{4}.tibx", "");
                filename = filename.replaceAll("-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}-\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{13}.tibx", "");
            } else if(filename.length()<75){
                compiledPattern = Pattern.compile("-\\d{4}.tibx");
                matcher = compiledPattern.matcher(filename);
                if(matcher.find()){
                    filename = filename.replaceAll("-\\d{4}.tibx","");
                }
            }
            filename = filename.replaceAll("\\.tib","");
        } else if (filename.contains(".tib")) {
            backupType = "True Image";
            if (filename.contains("_b") && filename.contains("_s") && filename.contains("_v")) {
                if (filename.contains("_full_")) {
                    int index = filename.indexOf("_full_");

                    filename = (filename.substring(0, index));

                } else if (filename.contains("_inc_")) {
                    int index = filename.indexOf("_inc_");

                    filename = (filename.substring(0, index));
                }
            }
        } else {
            backupType = "";
            filename = filename.replaceAll("\\.tib","");
        }
        return filename;
    }

    public static String versionFinder(String filename) {
        String version = "";
        if (backupType.equals("Acronis")) {
            Pattern pattern = Pattern.compile("-\\d{4}.tibx");
            Matcher matcher1 = pattern.matcher(filename);
            if (matcher1.find()) {
                version = matcher1.group().substring(1, matcher1.group().length() - 5);
            } else {
                version = "----";
            }
        }
        if (backupType.equals("True Image")) {
            if (filename.contains("_full_")) {
                version = "Full";
            } else if (filename.contains("_inc_")) {
                version = "Inc";
            }
        }

        return version;
    }

    public static String dateOfModifyString(File filename, String createDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        createDate = sdf.format(filename.lastModified());
        return createDate;
    }

    public static String dateofModifyTimestamp(File filename, String createDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        createDate = sdf.format(filename.lastModified());
        return createDate;
    }

    public static String readableByteCountSI(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }
}
