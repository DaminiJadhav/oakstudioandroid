package com.sdaemon.oakstudiotv.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TestVelocidad {

static long totalBytesReceived = 0; //
static long startSample; 
static long endSample ; 
private static final long TIME_FOR_DOWNLOAD_MILISECONDS = (long) 10000.0;      
private static final long MILI_TO_NANO = 1000000; 

public static void main(String[] args) throws InterruptedException, ExecutionException {


    try{
        final ExecutorService service;
        String downloadFileUrl100MB = "http://cachefly.cachefly.net/100mb.test";


        startSample = System.nanoTime();

        service = Executors.newFixedThreadPool(6);
        FutureTask futureTask_1 = new FutureTask((Callable) new SpeedTestThread(downloadFileUrl100MB));
        service.execute(futureTask_1);
        FutureTask futureTask_2 = new FutureTask(new SpeedTestThread(downloadFileUrl100MB));
        service.execute(futureTask_2);

        service.shutdownNow();
        long result1 = (Long) futureTask_1.get();
        long result2 = (Long) futureTask_2.get();

        endSample = System.nanoTime();



       long timeSpent = (long) endSample-startSample;
            long totalBytesReceived = result1 + result2;
            System.out.println("Time of threads: " + timeSpent/1000000000.0   + " seconds " + "\nbytes received: " + (totalBytesReceived) );
            double calculatedSpeed;
//            long finalTimeSpent ;
//            finalTimeSpent = (long) ((TIME_FOR_DOWNLOAD_MILISECONDS * MILI_TO_NANO - diff));
            calculatedSpeed =  SpeedInfo.calculate(timeSpent, totalBytesReceived).megabits;
            System.out.println("Velocidad calculada: " + calculatedSpeed   + " mbps" );

    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }

}

}


class SpeedTestThread implements Callable<Long> {

private String url = new String("");
private static final long TIME_FOR_DOWNLOAD_NANOSECONDS = (long) 10000000000.0;
private static final long MILI_TO_NANO = 1000000;
private long bytesThread;

public SpeedTestThread(String urlToDownload){
    url = urlToDownload;
}

public void run() {


}

@Override
public Long call() throws Exception {


    System.out.println("FileDownload " +  " File to download: " + url );
    InputStream stream = null;
    long startCon = System.nanoTime();
    URL urlToDownload = null;
    try {
        urlToDownload = new URL(url);
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    URLConnection con = null;
    try {
        con = urlToDownload.openConnection();
    } catch (IOException e) {
        e.printStackTrace();
    }
    con.setUseCaches(false);
    //Tiempo de acceso al archivo.
    long connectionLatency = (System.nanoTime() - startCon)/MILI_TO_NANO;
    System.out.println("Connection latency = " + connectionLatency + "");

    con.setConnectTimeout(5000);

    try {
        stream = con.getInputStream();
    } catch (IOException e) {
        e.printStackTrace();
    }
    long startNano = System.nanoTime();



    int currentByte = 0;
    try {

        while ((currentByte = stream.read()) != -1 ) {
            bytesThread++;
            if ((System.nanoTime() - startNano) > TIME_FOR_DOWNLOAD_NANOSECONDS){
                System.out.println("Time");
                break;
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println("Thread bytes received:  " + bytesThread);
    return bytesThread;
}
}



class SpeedInfo {
public double kilobits = 0;
public double megabits = 0;
public double downspeed = 0;
private static final double BYTE_TO_KILOBIT = 0.008;
private static final double KILOBIT_TO_MEGABIT = 0.001;

/**
 * 1 byte = 0.0078125 kilobits
 * 1 kilobits = 0.0009765625 megabit
 *
 * @param downloadTime in miliseconds
 * @param bytesIn      number of bytes downloaded
 * @return SpeedInfo containing current testVelocidadThread
 */
public static SpeedInfo calculate(final long downloadTime, final long bytesIn) {
    SpeedInfo info = new SpeedInfo();
    //from mil to sec
    System.out.println("Bytes transferidos: " + bytesIn + "Tiempo de descarga: " + downloadTime/1000000000);
    double time = downloadTime;
    double byteIn1 = bytesIn;
    double division = (double)(byteIn1 / time);
    double bytespersecond = ((division) * 1000000000);
    double kilobits = bytespersecond * BYTE_TO_KILOBIT;
    double megabits = kilobits * KILOBIT_TO_MEGABIT;
    info.downspeed = bytespersecond;
    info.kilobits = kilobits;
    info.megabits = megabits;
    return info;
}
}