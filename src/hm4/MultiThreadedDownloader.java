package hm4;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiThreadedDownloader {
    private static final String DOWNLOAD_FOLDER = "downloads/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of threads to use: ");
        int numThreads = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the URL of the file to download: ");
        String fileUrl = scanner.nextLine();

        System.out.print("Enter the filename to save the file as: ");
        String fileName = scanner.nextLine();

        downloadFileMultithreaded(fileUrl, fileName, numThreads);
    }

    private static void downloadFileMultithreaded(String fileUrl, String fileName, int numThreads) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int fileSize = connection.getContentLength();

            File outputDir = new File(DOWNLOAD_FOLDER);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(DOWNLOAD_FOLDER + fileName);

            List<DownloadThread> threads = new ArrayList<>();
            int chunkSize = fileSize / numThreads;

            for (int i = 0; i < numThreads; i++) {
                int start = i * chunkSize;
                int end = (i == numThreads - 1) ? fileSize : (i + 1) * chunkSize;
                DownloadThread thread = new DownloadThread(fileUrl, outputFile, start, end);
                threads.add(thread);
                thread.start();
            }

            for (DownloadThread thread : threads) {
                thread.join();
            }

            System.out.println("File downloaded successfully!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error downloading file: " + e.getMessage());
        }
    }

    private static class DownloadThread extends Thread {
        private final String fileUrl;
        private final File outputFile;
        private final int start;
        private final int end;

        public DownloadThread(String fileUrl, File outputFile, int start, int end) {
            this.fileUrl = fileUrl;
            this.outputFile = outputFile;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);

                try (InputStream inputStream = connection.getInputStream();
                     RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw")) {
                    randomAccessFile.seek(start);
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        randomAccessFile.write(buffer, 0, bytesRead);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error downloading file: " + e.getMessage());
            }
        }
    }
}
