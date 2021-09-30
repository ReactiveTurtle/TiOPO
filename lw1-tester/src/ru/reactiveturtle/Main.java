package ru.reactiveturtle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(
                    "Invalid count of arguments.\n" +
                            "Usage: java -jar <program file name>.jar <test file name> <test result file name>");
            return;
        }
        File testFile = new File(args[0]);
        if (!testFile.exists()) {
            System.out.println("File with name " + args[0] + " does not exists.");
        }
        File resultFile = new File(args[1]);
        try {
            if (!resultFile.exists() && !resultFile.createNewFile()) {
                System.out.println("Impossible to create test result file");
                return;
            }
            BufferedReader testFileReader = new BufferedReader(new FileReader(testFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile));
            String line;
            int counter = 1;
            while ((line = testFileReader.readLine()) != null) {
                String[] testArgs = ArgsSplitter.parse(line);
                bufferedWriter.write(counter + " ");
                String expected = testArgs[0];
                String[] programArgs = new String[testArgs.length - 1];
                for (int i = 0; i < programArgs.length; i++) {
                    programArgs[i] = String.format("\"%s\"", testArgs[i + 1]);
                }
                Process process = Runtime
                        .getRuntime()
                        .exec("java -jar lw1.jar " + String.join(" ", programArgs));
                InputStream processInputStream = process.getInputStream();
                int b1;
                String str;
                List<Byte> bytes = new ArrayList<>();
                while ((b1 = processInputStream.read()) != -1) {
                    bytes.add((byte) b1);
                }
                byte[] byteArray = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++) {
                    byteArray[i] = bytes.get(i);
                }
                str = new String(byteArray).trim();
                System.out.printf("Actual: %s, Expected: %s%n", str, expected);
                bufferedWriter.write(str.equals(expected) ? "success" : "error");
                bufferedWriter.newLine();
                counter++;
            }
            bufferedWriter.close();
            testFileReader.close();
        } catch (IOException e) {
            System.out.println("Error with test files");
        }
    }
}
