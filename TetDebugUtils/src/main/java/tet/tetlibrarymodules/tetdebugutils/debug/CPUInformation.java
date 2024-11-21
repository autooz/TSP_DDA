package tet.tetlibrarymodules.tetdebugutils.debug;


import java.io.IOException;
import java.io.InputStream;


public class CPUInformation {

    ProcessBuilder processBuilder;
    String Holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process;
    byte[] byteArry;

    public String getInfo() {


        byteArry = new byte[1024];

        try {
            processBuilder = new ProcessBuilder(DATA);

            process = processBuilder.start();

            inputStream = process.getInputStream();

            while (inputStream.read(byteArry) != -1) {

                Holder = Holder + new String(byteArry);
            }

            inputStream.close();

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return Holder;
    }
}
