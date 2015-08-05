package com.onecheck.sso.poc.commonlib.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by deepak on 15/4/15.
 */
public class WriteToExternal implements Runnable {
    protected ArrayBlockingQueue blockingQueue = null;
    protected String string = null;
    private boolean isRunning = true;

    public WriteToExternal(ArrayBlockingQueue queue) {
        this.blockingQueue = queue;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                string = (String) blockingQueue.take();
                writeToFile(string);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void writeToFile(String string) {
        String filename = null;
        filename = createFileName();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File folder = new File(Environment.getExternalStorageDirectory(), "SDSSO");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File(folder, filename + ".txt");
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file, true);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String createFileName() {
        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        return ("log_" + date + "_" + month + "_" + year);
    }

}