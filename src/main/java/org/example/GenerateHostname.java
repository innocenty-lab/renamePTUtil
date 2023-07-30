package org.example;

public class GenerateHostname {
    public static String getHostname(String fileName, String from, String to) {
        return fileName.substring(fileName.indexOf(from)+1, fileName.lastIndexOf(to));
    }
}
