package org.apache.airavata.services.impl;

import org.apache.airavata.services.StreamFileService;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by dimuthuupeksha on 7/16/15.
 */
public class StreamFileServer {
    private void start() {
        try {

            TNonblockingServerTransport theServerSocket = new TNonblockingServerSocket(7911);
            StreamFileService.Processor theProcessor = new StreamFileService.Processor(
                    new StreamFileServiceImpl());
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
        StreamFileServer theFileServer = new StreamFileServer();
        theFileServer.start();
    }

}
