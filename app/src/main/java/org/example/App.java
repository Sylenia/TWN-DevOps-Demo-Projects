package org.example;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        GlobalMemory memory = systemInfo.getHardware().getMemory();

        // Start the Spark web server
        Spark.port(8080);
        Spark.get("/", (req, res) -> {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html>");
            html.append("<head><title>System Health Checker</title></head>");
            html.append("<body>");
            html.append("<h1>System Health Checker</h1>");

            // Add OS details
            html.append("<p><strong>Operating System:</strong> ")
                .append(systemInfo.getOperatingSystem())
                .append("</p>");

            // Add CPU details
            html.append("<p><strong>CPU:</strong> ")
                .append(processor.getProcessorIdentifier().getName())
                .append("</p>");

            // Calculate CPU usage
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            try {
                Thread.sleep(1000); // 1 second delay for CPU load calculation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
            html.append("<p><strong>CPU Usage:</strong> ")
                .append(String.format("%.2f%%", cpuLoad))
                .append("</p>");

            // Add Memory details
            html.append("<p><strong>Total Memory:</strong> ")
                .append(String.format("%.2f GB", memory.getTotal() / 1e9))
                .append("</p>");
            html.append("<p><strong>Available Memory:</strong> ")
                .append(String.format("%.2f GB", memory.getAvailable() / 1e9))
                .append("</p>");

            html.append("</body>");
            html.append("</html>");
            return html.toString();
        });

        System.out.println("Server running at http://localhost:8080/");
    }

    public String getGreeting() {
        return "Hello, System Health Checker!";
    }
}
