package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileResource;
import org.apache.airavata.services.Utils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @author Dimuthu
 */
public class FileResourceClient {
    private static FileResource.Client client;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7911;

    public static FileResource.Client getClient(){
        if(client==null){
            try {
                TTransport theClientTransport = new TFramedTransport(new TSocket(HOST,PORT));
                TProtocol theProtocol = new TBinaryProtocol(theClientTransport);
                client = new FileResource.Client(theProtocol);
                theClientTransport.open();
            } catch (TTransportException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public void downloadFile(String remotePath, String localPath) {
        try{
            ByteBuffer buffer = getClient().downloadFile("",remotePath);
            Utils.byteBufferToFile(buffer,localPath);
        } catch (TException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(String localFile, String remoteFilePath, String remoteFileName){
        try{
            ByteBuffer buffer = Utils.fileToByteBuffer(localFile);
            getClient().uploadFileForPath("", remoteFilePath, remoteFileName, buffer);
        } catch (TException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String ar[]) {
        FileResourceClient client = new FileResourceClient();
        client.downloadFile("/Users/dimuthuupeksha/file_manager_tests/sample/jobslist.xml","/Users/dimuthuupeksha/file_manager_tests/new/jobslist.xml");
        client.uploadFile("/Users/dimuthuupeksha/file_manager_tests/sample/jobslist.xml", "/Users/dimuthuupeksha/file_manager_tests/new", "jobslist2.xml");
    }
}
