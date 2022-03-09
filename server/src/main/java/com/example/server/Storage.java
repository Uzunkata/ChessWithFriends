package com.example.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Storage {
    private static final String relativeFolder = "games/";
    private static final Logger logger = LogManager.getLogger(Storage.class);

    public static Object get(String fileName) {
        System.out.println("Storage.get("+relativeFolder + fileName+")");
        Object obj = null;
        try{
            FileInputStream fis = new FileInputStream(relativeFolder + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            return obj;
        } catch (IOException e) {
            logger.info("someone tried to find not existing game "+fileName);
        } catch (ClassNotFoundException e) {
            logger.error("exception", e);
        }
        return obj;
    }

    public static void put(Object obj, String fileName) {
        try{
            File file = new File(relativeFolder + fileName);
            logger.error("GAMES PATH:"+ file.getAbsolutePath());
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            FileOutputStream fos = new FileOutputStream(relativeFolder + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
        } catch (IOException e) {
            logger.error("exception", e);
        }
    }

}

