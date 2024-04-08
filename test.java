import java.io.*;
import java.util.*;


public class test{ 
    public static void main(String[] arg) throws IOException{
        String file1 = "Input1.csv";
        String file2 = "Input2.csv";

        List<String[]> csv1Data = readCSV(file1);
        List<String[]> csv2Data = readCSV(file2);

        List<String[]> csvTest = csvData(csv2Data);

        for (String[] list: csvTest) {
            for (String s: list) {
                System.out.println(s);
            }
        }

    }

    public static List<String[]> csvData(List<String[]> csvFile) {
        List<String[]> csvData = new ArrayList<>();
        List<String> csvID = new ArrayList<>();
        List<String> csvName = new ArrayList<>();
        List<String> csvQTY = new ArrayList<>();

        for (String[] list: csvFile) {
            csvID.add(list[0]);
                 csvName.add(list[1]);  
                 csvQTY.add(list[2]);
        }
        String[] csvIDFin = csvID.toArray(new String[0]);
        String[] csvNameFin = csvID.toArray(new String[0]);
        String[] csvQTYFin = csvID.toArray(new String[0]);
        csvData.add(csvIDFin);
        csvData.add(csvNameFin);
        csvData.add(csvQTYFin);

        return csvData;




    }


    public static List<String[]> outputCSV(List<String[]> file1, List<String[]> file2) {
        List<String[]> outputCSV;
        List<String[]> csv1 = csvData(file1);
        List<String[]> csv2 = csvData(file2);



        return null;
    }

    public static List<String[]> readCSV(String filePath) throws IOException{
        List<String[]> csvData = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = " ";
            while ((line = reader.readLine()) != null) {
                String[] Values = line.split(","); 
                csvData.add(Values);
            }
        }
        return csvData;
    }
    
}