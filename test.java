import java.io.*;
import java.util.*;


public class test{ 
    public static void main(String[] arg) throws IOException{
        String file1 = "Input1.csv";
        String file2 = "Input2.csv";

        List<String[]> csv1Data = readCSV(file1);
        List<String[]> csv2Data = readCSV(file2);

        List<String[]> csvTest = csvData(csv1Data);

        for (String s: csvTest.get(0)) {
            System.out.println(s);
        }

        outputCSV(csv1Data, csv2Data);

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
        csvID.remove(0);
        csvName.remove(0);
        csvQTY.remove(0);
        String[] csvIDFin = csvID.toArray(new String[0]);
        String[] csvNameFin = csvName.toArray(new String[0]);
        String[] csvQTYFin = csvQTY.toArray(new String[0]);
        csvData.add(csvIDFin);
        csvData.add(csvNameFin);
        csvData.add(csvQTYFin);

        return csvData;




    }


    public static void outputCSV(List<String[]> file1, List<String[]> file2) {
        String filePath = "testOutput.csv";

        List<String[]> outputCSV = null;
        List<String[]> csv1 = csvData(file1);
        List<String[]> csv2 = csvData(file2);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("ID,Name,QTY1,QTY2,Diference");
            writer.newLine();

        int count = 0;
        
        for (String s: csv1.get(0)) {
            if (s.equalsIgnoreCase(csv2.get(0)[0])) {
                System.out.println("This is true");
               // writer.write();
               writer.write(s + "," + csv1.get(1)[count] + "," + csv1.get(2)[count] + "," + csv2.get(2)[count] +  "," + "TRUE");
                writer.newLine();
            }
            else {
                System.out.println("This is false");
                writer.write(s + "," + csv1.get(1)[count] + "," + csv1.get(2)[count] + "," + csv2.get(2)[count] + "," + "FALSE");
                writer.newLine();
            }
            count++;
        }
    }
    catch (IOException e) {
        e.printStackTrace();
    }
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