import java.io.*;
import java.util.*;


public class test{ 
    public static void main(String[] arg) throws IOException{
        String file1 = "Input1.csv";
        String file2 = "Input2.csv";

        List<String[]> csv1Data = readCSV(file1);
        List<String[]> csv2Data = readCSV(file2);
       
        outputCSV(csv1Data, csv2Data);

    }


    // Grabs the IDs, names, and quantitys and organizes the data in a list
    public static List<String[]> csvData(List<String[]> csvFile) {
        List<String[]> csvData = new ArrayList<>();
        List<String> csvID = new ArrayList<>();
        List<String> csvName = new ArrayList<>();
        List<String> csvQTY = new ArrayList<>();

        // Seperatews the IDs, names, and quantities into their own respective lists
        for (String[] list: csvFile) {
            csvID.add(list[0]);
            csvName.add(list[1]);  
            csvQTY.add(list[2]);
        }
        
        // Removes the header so that only the data is being read
        csvID.remove(0);
        csvName.remove(0);
        csvQTY.remove(0);
        
        // Convert each column list to an array and add them to main data list
        String[] csvIDFin = csvID.toArray(new String[0]);
        String[] csvNameFin = csvName.toArray(new String[0]);
        String[] csvQTYFin = csvQTY.toArray(new String[0]);
        
        csvData.add(csvIDFin);
        csvData.add(csvNameFin);
        csvData.add(csvQTYFin);

        return csvData;
    }


    // Compares the data from two CSV files and produces a new CSV file with the results
    public static void outputCSV(List<String[]> file1, List<String[]> file2) {
        String filePath = "testOutput.csv";
        List<String[]> csv1 = csvData(file1);
        List<String[]> csv2 = csvData(file2);
        List<String[]> largerCSV = null; 
        List<String[]> smallerCSV = null;

        // Determine which CSV file has more data in order to not get out of bounds error
        if(csv1.get(0).length > csv2.get(0).length ) {
            largerCSV = csv1;
            smallerCSV = csv2;
        }
        else {
            largerCSV = csv2;
            smallerCSV = csv1;
        }

        // Lists to store the locations of matching and non-matching IDs
        List<Integer> commonID1 = new ArrayList<>();
        List<Integer> commonID2 = new ArrayList<>();
        List<Integer> uncommonID = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Write header for output CSV file
            writer.write("ID,Name,QTY1,QTY2,Difference");
            writer.newLine();
            
            for (int i = 0; i < largerCSV.get(0).length; i++) {
                for(int j = 0; j < smallerCSV.get(0).length; j++) {
                    if (largerCSV.get(0)[i].equalsIgnoreCase(smallerCSV.get(0)[j])) {
                        commonID1.add(i);
                        commonID2.add(j);
                    }  
                    else if (!largerCSV.get(0)[i].equalsIgnoreCase(smallerCSV.get(0)[j]) && check(uncommonID, i)) {
                        uncommonID.add(i);
                    }
                }
            }

            // Checks which indexes did not have a match and adds them to a new array
            for (int i = 0; i < largerCSV.get(0).length; i++) {
                if (check(commonID1, i)){
                    uncommonID.add(i); 
                }
            }
     

            // Write data for matched IDs to the output CSV file
            for (int i = 0; i < commonID1.size(); i++) {
                String id = largerCSV.get(0)[commonID1.get(i)];
                String name = largerCSV.get(1)[commonID1.get(i)];
                String qty1 = largerCSV.get(2)[commonID1.get(i)];
                String qty2 = smallerCSV.get(2)[commonID2.get(i)];
                writer.write(id + "," + name + "," + qty1 + "," + qty2 + "," + !qty1.equalsIgnoreCase(qty2));
                writer.newLine();
            }


          // Write data for unmatched IDs to the output CSV file
            for (int i = 0; i < uncommonID.size(); i++) {
                String id = largerCSV.get(0)[uncommonID.get(i)];
                String name = largerCSV.get(1)[uncommonID.get(i)];
                String qty1 = largerCSV.get(2)[uncommonID.get(i)];
                String qty2 = "0";
                writer.write(id + "," + name + "," + qty1 + "," + qty2 + "," + "true");
                writer.newLine();
                }
            }
            
        
    catch (IOException e) {
        e.printStackTrace();
    }
}


 // Method to read data from a CSV file and return as a list of string arrays
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



    // Method to check if an index exists in a list
    public static boolean check(List<Integer> arrayList, int i) {
        boolean bool = false;
        if (arrayList.size() == 0) {
            return bool;
        }
        else {
            if (!arrayList.contains(i)) {
                bool = true;
        }
        return bool; 
    }

}
}
