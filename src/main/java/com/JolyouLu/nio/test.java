package com.JolyouLu.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: LZJ
 * @Date: 2020/10/1 11:11
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) throws IOException {
        File file = new File("test.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        byte[] arr = new byte[(int) file.length()];
        raf.read(arr);

        Socket socket = new ServerSocket(8080).accept();
        socket.getOutputStream().write(arr);
    }
}
