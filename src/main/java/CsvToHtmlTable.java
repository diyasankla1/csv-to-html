import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToHtmlTable {

    public static void execute(String args[]) {
        String csvFile = args[0];
        String outputFile = args[1];

        System.out.println("CSV file location: " + csvFile);
        System.out.println("Output file location: " + outputFile);

        // read lines of csv to a string array list
        List<String> lines = readFile(csvFile);

        //embrace <td> and <tr> for lines and columns
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }


        // embrace <table> and </table>
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");

        // output result
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFile(String csvFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void main(String[] args) {
        // print info and show user how to call the program if needed
//        System.out.println("This program is tested only for UTF-8 files.");
        if (Arrays.stream(args).count() > 0) {
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("-help") || args.length != 2) {
                System.out.println("java src.CsvToHtmlTable <input file> <output file>");
                System.out.println("Example: java src.CsvToHtmlTable nice.csv nice.html");
            }
            execute(args);
        } else {
            System.out.println("Input parameter missing / invalid");
        }
    }
}