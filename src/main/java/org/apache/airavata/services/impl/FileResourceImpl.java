package org.apache.airavata.services.impl;

import org.apache.airavata.services.FileResource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileResourceImpl implements FileResource.Iface {

    private static final String DEFAULT_ROOT_PATH = "/var/www/portal/experimentData";

    public void init() throws TException {

    }

    public ByteBuffer downloadFile(String accessToken, String path) throws TException {
        try {
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return ByteBuffer.wrap(bytes);
        }catch (IOException e){
            throw new TException(e.getMessage(),e);
        }
    }

    public boolean uploadFileForPath(String accessToken, String path, String fileName, ByteBuffer buffer) throws TException {
        byte[] bytes = ArrayUtils.subarray(buffer.array(), buffer.position(), buffer.limit());
        File file = new File(DEFAULT_ROOT_PATH + File.separator + path + File.separator + fileName);
        try {
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (IOException e){
            throw new TException(e.getMessage(),e);
        }
    }

    public boolean uploadFileForExperiment(String accessToken, String experimentId, String fileName, ByteBuffer buffer) throws TException {
        byte[] bytes = ArrayUtils.subarray(buffer.array(), buffer.position(), buffer.limit());

        try {
            File fileRoot = new File(DEFAULT_ROOT_PATH+File.separator+experimentId);
            if(!fileRoot.exists()){
                if(!fileRoot.mkdir()){
                    throw new TException("Error in creating rood directory for experiment "+experimentId);
                }
            }

            File inputDirectory = new File(DEFAULT_ROOT_PATH+File.separator+experimentId+File.separator+"input");
            if(!inputDirectory.exists()){
                if(!fileRoot.mkdir()){
                    throw new TException("Error in creating input directory for experiment "+experimentId);
                }
            }

            File file = new File(DEFAULT_ROOT_PATH+File.separator+experimentId+File.separator+"input"+File.separator+fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (IOException e){
            throw new TException(e.getMessage(),e);
        }
    }

    public List<String> listOutputFilesForExperiment(String accessToken, String experimentId) throws TException {
        List<String> fileList = new ArrayList<String>();
        File outputDirectory = new File(DEFAULT_ROOT_PATH+File.separator+experimentId+File.separator+"output");
        if(!outputDirectory.exists()){
            return fileList;
        }

        File[] files = outputDirectory.listFiles();
        for(File f : files){
            fileList.add(f.getName());
        }
        return fileList;
    }

    public List<String> listInputFilesForExperiment(String accessToken, String experimentId) throws TException {
        List<String> fileList = new ArrayList<String>();
        File inputDirectory = new File(DEFAULT_ROOT_PATH+File.separator+experimentId+File.separator+"input");
        if(!inputDirectory.exists()){
            return fileList;
        }

        File[] files = inputDirectory.listFiles();
        for(File f : files){
            fileList.add(f.getName());
        }
        return fileList;
    }

    public List<String> listFilesInPath(String accessToken, String path) throws TException {
        List<String> fileList = new ArrayList<String>();

        File directory = new File(path);
        if(!directory.exists() || directory.isFile()){
            return fileList;
        }

        File[] files = directory.listFiles();
        for(File f : files){
            fileList.add(f.getName());
        }
        return fileList;
    }

    public boolean deleteDir(String accessToken, String path) throws TException {
        File directory = new File(path);
        if(!directory.exists()){
            new TException("There is no such directory "+path);
        }

        if(directory.isFile()){
            new TException(path +" is not a directory");
        }

        return directory.delete();
    }

    public boolean deleteFile(String accessToken, String path) throws TException {
        File file = new File(path);
        if(!file.exists()){
            new TException("There is no such file "+path);
        }

        if(!file.isFile()){
            new TException(path +" is not a file");
        }

        return file.delete();
    }

    public boolean rename(String accessToken, String path, String name) throws TException {
        File file = new File(path);
        if(!file.exists()){
            new TException("There is no such file "+path);
        }

        File newFile = new File(file.getParent()+File.separator+name);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(IOUtils.toByteArray(inputStream));
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        }catch (IOException e){
            throw new TException(e.getMessage(),e);
        }
    }

    public boolean getRemoteFile(String accessToken, String host, String path) throws TException {
        return false;
    }

    @Override
    public boolean isDirectoryExists(String accessToken, String path) throws TException {
        File file = new File(path);
        if(file.exists() && file.isDirectory()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isFileExists(String accessToken, String path) throws TException {
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }
}
