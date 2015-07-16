package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileResource;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by dimuthuupeksha on 7/16/15.
 */
public class FileService {
    private void start() {
        try {

            TNonblockingServerTransport theServerSocket = new TNonblockingServerSocket(7911);
            FileResource.Processor theProcessor = new FileResource.Processor(
                    new FileResourceImpl());
            TServer theServer = new TNonblockingServer(
                    new TNonblockingServer.Args(theServerSocket)
                            .processor(theProcessor));
            System.out.println("Server starting on port 7911...");

            theServer.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        FileService theFileServer = new FileService();
        theFileServer.start();
    }

}
