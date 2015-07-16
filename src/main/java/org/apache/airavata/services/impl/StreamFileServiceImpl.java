package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileChunk;
import org.apache.airavata.services.StreamFileService;
import org.apache.thrift.TException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by dimuthuupeksha on 7/15/15.
 */
public class StreamFileServiceImpl implements StreamFileService.Iface {
    public FileChunk getBytes(String fileName, long offset, int size) throws TException {
        File file = new File(fileName);
        FileChunk chunk = new FileChunk();

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            MappedByteBuffer buffer = outputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, offset, size);

            chunk.data = buffer;
            chunk.remaining = outputStream.getChannel().size() - offset - size;

            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return chunk;
    }
}
