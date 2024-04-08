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
          //  System.out.println(s);
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
        List<String[]> csv1 = csvData(file1);
        List<String[]> csv2 = csvData(file2);
        int maxArrayLength = 0;
        if(csv1.get(0).length > csv2.get(0).length ) {
            maxArrayLength = csv1.get(0).length;
        }
        else {
            maxArrayLength = csv2.get(0).length;
        }
        List<Integer> commonID1 = new ArrayList<>();
        List<Integer> commonID2 = new ArrayList<>();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("ID,Name,QTY1,QTY2,Difference");
            writer.newLine();
            
            for (int i = 0; i < csv1.get(0).length; i++) {
                for(int j = 0; j < csv2.get(0).length; j++) {
                    if (csv1.get(0)[i].equalsIgnoreCase(csv2.get(0)[j])) {

                        commonID1.add(i);
                        commonID2.add(j);
                    }
                }
            }
    
            for (int i = 0; i < commonID1.size(); i++) {
                String id = csv1.get(0)[commonID1.get(i)];
                String name = csv1.get(1)[commonID1.get(i)];
                String qty1 = csv1.get(2)[commonID1.get(i)];
                String qty2 = csv2.get(2)[commonID2.get(i)];
                writer.write(id + "," + name + "," + qty1 + "," + qty2 + "," + !qty1.equalsIgnoreCase(qty2));
                writer.newLine();
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