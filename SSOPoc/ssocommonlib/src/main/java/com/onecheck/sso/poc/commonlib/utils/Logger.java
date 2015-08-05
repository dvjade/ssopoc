package com.onecheck.sso.poc.commonlib.utils;


/**
 * Created by deepak on 15/4/15.
 */
import java.util.concurrent.ArrayBlockingQueue;

import static android.util.Log.DEBUG;

public class Logger {
    private static final String PKG = "one.sdsso.deepak.com.sdssolib";
    public static final String DEFAULT_TAG = "JB";
    private static final int defaultWhereDepth = 4;

    private static final int MAX_CHARS_PER_LINE = 4000;
    private static final int MAX_LINES = 40;

    // whether log.where is logged, and how much spacing alignment / inversion
    public static int logWhereSpacing = 2;
    public static int pad_invert = 1;

    private static Logger logger = null;

    protected String string = null;

    private ArrayBlockingQueue blockingQueue = null;
    private WriteToExternal writeToExternal = null;

    protected Logger() {
    }

    public static synchronized void leaveBreadcrumb(String s) {
        log(s);
    }

    public static synchronized void log(String string) {
        getLoggerInstance().writeString(string);
    }

    public static synchronized void logError(String msg, Throwable t) {
        getLoggerInstance().writeString("Error : " + msg, t);
    }

    private static synchronized Logger getLoggerInstance() {
        if (logger == null) {
            logger = new Logger();
            logger.blockingQueue = new ArrayBlockingQueue(1024);
            logger.initLogger();
        }
        return logger;
    }

    private void initLogger() {
        writeToExternal = new WriteToExternal(blockingQueue);
        new Thread(writeToExternal).start();
    }

    public void writeString(String msg) {
        writeString(msg, null);
    }

    public void writeString(String msg, Throwable t) {
        try {
            if (true/*Config.DEBUGABLE*/) {
                msg = printToLogcat(DEBUG, msg, t);
                blockingQueue.put("\n------------");
                msg = System.currentTimeMillis() + " : " + msg;
                blockingQueue.put(msg);
                blockingQueue.put("\n------------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String printToLogcat(int level, String msg, Throwable t) {
        // Print to log cat only if it is a debug build
        if (level < 0) level = DEBUG;

        if (t != null) {
            msg += "\n" + android.util.Log.getStackTraceString(t);
        }

        android.util.Log.println(level, DEFAULT_TAG, "----------------------");

        String fullMsg;
        if (logWhereSpacing != 0) {
            String where = where(defaultWhereDepth, new Exception());

            if (logWhereSpacing != 1) {
                int len = where.length();
                int mod = len % logWhereSpacing;
                if (mod != 0) len += (logWhereSpacing - mod);

                where = pad(where, len * pad_invert);
            }
            fullMsg = where + " | " + msg;
        } else {
            fullMsg = msg;
        }

        if (fullMsg.length() <= MAX_CHARS_PER_LINE) {
            android.util.Log.println(level, DEFAULT_TAG, fullMsg);
        } else {


            int i = 0;
            int start = 0;
            int end = 0;
            while (end < fullMsg.length()) {
                if (i < MAX_LINES - 1) {
                    end = Math.min(start + MAX_CHARS_PER_LINE, fullMsg.length());
                } else {
                    int oldStart = start;
                    end = fullMsg.length();
                    start = end - MAX_CHARS_PER_LINE;
                    int trimmed = start - oldStart;
                    if (trimmed > 0) {
                        android.util.Log.println(level, DEFAULT_TAG, "  TRIMMED |    ... " + trimmed + " chars ...");
                    }

                    if (trimmed < 0) {
                        android.util.Log.println(level, DEFAULT_TAG, "trimmed(" + trimmed + ")<0, this is probably a bug in the Logger, Blame Deepak.");
                    }

                }
                if (i == 0) {
                    android.util.Log.println(level, DEFAULT_TAG, fullMsg.substring(start, end));
                } else {
                    android.util.Log.println(level, DEFAULT_TAG, "CONTINUED | " + fullMsg.substring(start, end));
                }
                ++i;
                start += MAX_CHARS_PER_LINE;
            }
        }
        android.util.Log.println(level, DEFAULT_TAG, "-------------------");
        return fullMsg;
    }

    private String where(int depth) {
        return where(depth, new Exception());
    }

    private String where(int depth, Throwable ex) {
        StackTraceElement ste[] = ex.getStackTrace();
        if (ste == null) return "";

        if (depth > ste.length) depth = ste.length - 1;

        StackTraceElement frame = ste[depth];

        String cname = shorten(frame.getClassName());
//        String cname = frame.getClassName();

        int line = frame.getLineNumber();
        String method = frame.getMethodName();

        return cname + "." + method + ":" + line;
    }

    private String shorten(String cn) {
        if (cn.indexOf(PKG) == 0) {
            return cn.replace(PKG, "PKG");
        }

        return cn;
    }

    private String pad(String s, int padsize) {
        return pad(s, padsize, ' ');
    }

    private String pad(String s, int padsize, char c) {
        final int len = s.length();

        final boolean fromLeft;

        if (padsize < 0) {
            padsize = -padsize;
            fromLeft = true;
        } else fromLeft = false;

        if (len >= padsize) return s;

        padsize -= len;

        char[] padchars = new char[padsize];
        for (int i = 0; i < padsize; i++) padchars[i] = c;

        String pad = new String(padchars);

        if (fromLeft) return pad + s;
        else return s + pad;
    }

}