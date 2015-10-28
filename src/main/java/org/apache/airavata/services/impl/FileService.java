package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileResource;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

public class FileService {

    private void start() {

        try {
            TNonblockingServerTransport theServerSocket = new TNonblockingServerSocket(9000);
            FileResource.Processor theProcessor = new FileResource.Processor(
                    new FileResourceImpl());
            TServer theServer = new TNonblockingServer(
                    new TNonblockingServer.Args(theServerSocket)
                            .processor(theProcessor));
            System.out.println("Server starting on port 9000...");

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
