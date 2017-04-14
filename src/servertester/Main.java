/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servertester;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static String ip = "194.249.231.114";
    static String port = "23233";
    static String address = "/vsn/gps/";
    static String xbee = "403AB8F9";
    static int mSeconds = 30000;
    static int noNodes = 1000;
    static int cycles = 120;
    static GetMethod method;

    public static void testServer() throws IOException {
        HttpClient client = new HttpClient();
        method = new GetMethod("http://" + ip + ":" + port + address + "?id=15&p01=" + xbee + "&p02=09&p03=4644560&p04=25.55&p05=35.62&p06=975.72&p07=25.89&p08=45.87&p09=0&p10=10");
        client.executeMethod(method);
        method.releaseConnection();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args[0].equalsIgnoreCase("-i")) {
            ip = args[1];
            //System.out.println(ip);
        }
        if (args[2].equalsIgnoreCase("-p")) {
            port = args[3];
            //System.out.println(port);
        }
        if (args[4].equalsIgnoreCase("-a")) {
            address = args[5];
            //System.out.println(port);
        }
        if (args[6].equalsIgnoreCase("-x")) {
            xbee = args[7];
            //System.out.println(xbee);
        }
        if (args[8].equalsIgnoreCase("-n")) {
            noNodes = Integer.parseInt(args[9]);
            //System.out.println(noNodes);
        }
        if (args[10].equalsIgnoreCase("-c")) {
            mSeconds = Integer.parseInt(args[11]) * 1000;
            //System.out.println(mSeconds);
        }
        if (args[12].equalsIgnoreCase("-e")) {
            cycles = Integer.parseInt(args[13]);
            //System.out.println(mSeconds);
        }

        System.out.println("Simulating " + noNodes + " sensor nodes sending data every " + mSeconds / 1000 + " seconds\n"
                + "to the server with IP " + ip + " on port " + port + "and address " + address + "...");
        System.out.println("-i: IP address of server");
        System.out.println("-p: Port of server");
        System.out.println("-a: URL address");
        System.out.println("-x: Xbee address");
        System.out.println("-n: Number of nodes in simulation");
        System.out.println("-c: Cycle time of sending measurements in seconds");
        System.out.println("-e: Number of cycles");
        System.out.println("Example:");
        System.out.println("java -jar VsnServerTester.jar -i 194.249.231.21 -p 23233 -x 403AB8F9 -n 1000 -c 30 -e 10");

        for (int i = 0; i <= (cycles - 1); i++) {
            long timerStart = System.currentTimeMillis();
            for (int j = 0; j <= (noNodes - 1); j++) {
                testServer();
                System.out.println("Test: " + (j + 1));
            }
            long timerStop = System.currentTimeMillis();
            long durationTime = (timerStop - timerStart);
            float timeSeconds = (float) durationTime / 1000;
            System.out.println("Sent requests: " + noNodes);
            System.out.println("Duration [s]: " + timeSeconds);
            System.out.println("Requests per second: " + noNodes / timeSeconds);

            Thread.sleep(mSeconds);
        }
    }
}
