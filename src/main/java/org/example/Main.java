package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        HashMap<String,Path> stringHashMapFilePaths = getFilePathExecution();
        TreeMap<String,String> stringHashMapDataNaming = getDataNaming();
        Path resultDir = Paths.get("/home/user/Belajar/Enigmacamp/untitled/src/result/ ");
        String[] suffix = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        int totalProcess = 0;
        int temp = 0;
        String tempA = "";

        for (Map.Entry<String,String> pathNaming: stringHashMapDataNaming.entrySet()) {
            if (!Objects.equals(pathNaming.getValue(), tempA)) {
                temp = 0;
            }
            for (Map.Entry<String,Path> pathFile: stringHashMapFilePaths.entrySet()) {
                if (pathNaming.getKey().equals(pathFile.getKey())) {
                    tempA = pathNaming.getValue();
                    Files.copy(pathFile.getValue(), resultDir.resolveSibling(pathNaming.getValue() + " - " + suffix[temp] + ".png"), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(resultDir.toString() + pathNaming.getValue() + " - " + suffix[temp] + ".png");
//                    System.out.println(pathFile.getValue() + " " + pathNaming.getValue() + " - " + suffix[temp] + ".png");
                    totalProcess++;
                    temp = temp + 1;
                }
            }
        }

        System.out.println("TOTAL PROCESS: " + totalProcess);
    }

    private static TreeMap<String,String> getDataNaming() throws IOException {
        File path = new File("/home/user/Belajar/Enigmacamp/untitled/src/TemplateUtil.xlsx");
        FileInputStream file = new FileInputStream(path);

        XSSFWorkbook myWorkBook = new XSSFWorkbook(file);
        XSSFSheet mySheet = myWorkBook.getSheetAt(5);

        Iterator<Row> rowIterator = mySheet.rowIterator();
        rowIterator.next();
        TreeMap<String, String> dataNaming = new TreeMap<>();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            String role = "";
            String hostname = "";

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (Objects.equals(role, "")) {
                        role = cell.getStringCellValue();
                    } else {
                        hostname = cell.getStringCellValue();
                    }
                }
            }
            dataNaming.put(hostname, role);
        }
        return dataNaming;
    }

    private static HashMap<String, Path> getFilePathExecution() {
        File directoryPath = new File("/home/user/Belajar/Enigmacamp/untitled/src/DiskRun77");
        File[] content = directoryPath.listFiles();

        HashMap<String, Path> data = new HashMap<>();

        for (int i = 0; i < Objects.requireNonNull(content).length; i++) {
            String name = GenerateHostname.getHostname(content[i].getName(), "_", "_");
            data.put(name, Paths.get(content[i].getPath()));
        }
        return data;
    }
}