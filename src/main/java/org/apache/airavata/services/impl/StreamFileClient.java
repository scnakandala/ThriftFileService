package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileChunk;
import org.apache.airavata.services.StreamFileService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by dimuthuupeksha on 7/15/15.
 */
public class StreamFileClient {
    private int fileChunkSize = 16;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void invoke() {
        try{

            TTransport theClientTransport = new TFramedTransport(new TSocket("127.0.0.1",7911));
            TProtocol theProtocol = new TBinaryProtocol(theClientTransport);

            StreamFileService.Client theClient = new StreamFileService.Client(theProtocol);
            theClientTransport.open();
            filePath = "/Users/dimuthuupeksha/Documents/Academic/gsoc2105/airavata/outputfile.pdf";
            File theFile = new File(filePath);

            theFile.createNewFile();
            FileInputStream stream = new FileInputStream(theFile);
            long currentPosition = 0;

            FileChannel theFileChannel = stream.getChannel();
            boolean again = true;

            do {

                FileChunk chunk = theClient
                        .getBytes("/Users/dimuthuupeksha/Downloads/GridChemAiravataIntegration.pdf",currentPosition,fileChunkSize);
                currentPosition += fileChunkSize;
                theFileChannel.write(chunk.data);

                if(chunk.remaining==0) {
                    again = false;
                }

            } while (again);
            stream.close();

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String ar[]) {
        StreamFileClient client = new StreamFileClient();
        client.invoke();
    }
}
