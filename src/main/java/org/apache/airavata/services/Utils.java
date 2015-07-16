package org.apache.airavata.services;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by dimuthuupeksha on 7/17/15.
 */
public class Utils {
    public static void byteBufferToFile(ByteBuffer buffer, String path) throws IOException{
        File file = new File(path);
        file.createNewFile();
        FileOutputStream stream = new FileOutputStream(file);
        byte[] bytes = ArrayUtils.subarray(buffer.array(), buffer.position(), buffer.limit());
        stream.write(bytes);
        stream.flush();
        stream.close();
    }

    public static ByteBuffer fileToByteBuffer(String path) throws IOException {
        File file = new File(path);
        FileInputStream is = new FileInputStream(file);
        byte bytes[] = IOUtils.toByteArray(is);
        return ByteBuffer.wrap(bytes);
    }
}
