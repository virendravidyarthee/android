package com.virendra.mpd.meshcom.BackgroudTasks;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.virendra.mpd.meshcom.Interfaces.SocketListenerInterface;
import com.virendra.mpd.meshcom.Interfaces.TransferInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TransferSocket implements TransferInterface
{
    private static final String TAG = "TransferInterface ";
    private int myPort;
    private DataOutputStream myDataOutputStream;
    private DataInputStream myDataInputStream;

    private Socket mySocket;
    private ServerSocket myServerSocket;
    private String myAddress;
    private InetAddress myAddressInfo;


    private SocketListenerInterface myListener;

    private byte buff[] = new byte[1024];
    private FileTask myFileTask;
    private MessageTask myMessageTask;


    private ClientTask myClientConnectionTask;
    private ServerTask myServerConnectionTask;



    private File path = Environment.getExternalStorageDirectory().getAbsoluteFile();
    private Context myContext;


    public TransferSocket(Context context) {
        this.myContext = context;
    }

    @Override
    public TransferInterface setAddress(String address) {
        this.myAddress = address;
        return this;
    }


    @Override
    public TransferInterface setPort(int port) {
        this.myPort = port;
        return this;
    }



    @Override
    public TransferInterface setInetAddress(InetAddress address) {
        this.myAddressInfo = address;
        return this;
    }





    @Override
    public void startClient() {
        myClientConnectionTask = new ClientTask();
        myClientConnectionTask.execute();
    }

    @Override
    public void startServer() {
        myServerConnectionTask = new ServerTask();
        myServerConnectionTask.execute();

    }

    /** Client connection Task. */



    /** Server connect task. */
    private class ServerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                // create server
                myServerSocket = new ServerSocket(myPort);
                Log.e(TAG, "Waiting for client on port: " + myServerSocket.getLocalPort());

                // create client
                mySocket = myServerSocket.accept();

                if (mySocket.isConnected()) {
                    Log.e(TAG, "Client connected");
                    return true;
                }

            } catch(IOException e) {
                System.out.println(TAG + e.getMessage());
            }
            return false;
        }

    }

    private class ClientTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                // connect to server
                mySocket = new Socket();
                mySocket.bind(null);
                mySocket.connect(new InetSocketAddress(myAddress, myPort), 500);
                Log.e(TAG, "Connect on port: " + myPort + ", address: " + myAddressInfo);

                // is connected?
                if (mySocket.isConnected()) {
                    Log.e(TAG, "Client connected: " + mySocket.getRemoteSocketAddress());
                    return true;
                }
            } catch(IOException e) {
                System.out.println(TAG + e.getMessage());
            }
            return false;
        }
    }

    private class FileTask extends AsyncTask<Void, Void, Void> {
        private Context context;

        public FileTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int len;
                // is it client connected
                if(mySocket == null) {
                    return null;
                } else {
                    Log.e(TAG, "Socket null");
                }

                if(mySocket.isConnected()) {
                    return null;
                } else {
                    Log.e(TAG, "mySocket isn't connected!");
                }

                Log.e(TAG, "FileTask outputstream started");

                OutputStream outputStream = mySocket.getOutputStream();
                ContentResolver contentResolver = context.getContentResolver();
                InputStream inputStream = null;
                inputStream = contentResolver.openInputStream(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/milan.jpg"));

                while ((len = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                    Log.e(TAG, "write operation");
                }

                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    private class MessageTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try{
                myDataOutputStream = new DataOutputStream(mySocket.getOutputStream());
                myDataInputStream = new DataInputStream(mySocket.getInputStream());

                while (mySocket.isConnected()) {
                    Log.e(TAG, "Receive");
                    if (myListener != null) {
                        myListener.onReceiver(base64decode(myDataInputStream.readUTF()));
                    }
                }

                myDataInputStream.close();
                myDataOutputStream.close();

                mySocket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void endConnection() {
        try {
            mySocket.close();
            myServerSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TransferSocket.class.getName()).log(Level.SEVERE
                    , null, ex);
        }
    }





    @Override
    public void sendFile(String path) {
        SendFileTask sendFileTask = new SendFileTask(path);
        sendFileTask.execute();
    }


    private class SendFileTask extends AsyncTask<Void, Void, Void> {
        private String path;

        public SendFileTask(String path) {
            this.path = path;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e(TAG, "Send file");
                sendMessage(Utility.createMessage("sumit", "blah", Utility.FILE
                        , 22, Utility.IMAGE));

                File file = new File(path);
                if (!file.exists() || file.isDirectory()) {
                    return null;
                }
                myDataInputStream = new DataInputStream(mySocket.getInputStream());
                copyFiles(myDataInputStream, new FileOutputStream(file));

            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        private void copyFiles(DataInputStream dataInputStream, FileOutputStream fileOutputStream) {
            try{
                int read = 0;
                while((read = dataInputStream.read(buff)) != -1){
                    fileOutputStream.write(buff, 0, read);
                    Log.e(TAG, "write");
                }
            }catch (IOException e){}
        }
    }


    @Override
    public void sendMessage(String message) {
        try {
            myDataOutputStream.writeUTF(base64encode(message));
            myDataOutputStream.flush();
        } catch (IOException | NullPointerException ex) {
            System.out.println(TAG + ex.getMessage());
        }
    }



    @Override
    public void startMessageReceiver() {
        myMessageTask = new MessageTask();
        myMessageTask.execute();
    }



    @Override
    public void stopMessageReceiver() {
        if(myMessageTask != null) {
            myMessageTask.cancel(true);
        }
    }




    private String base64encode(String message){
        try {
            return Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(TransferSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    private String base64decode(String message){
        try {
            byte[] base64decodedBytes = Base64.decode(message, Base64.DEFAULT);
            return new String(base64decodedBytes, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TransferSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void stopFileReceiver() {
        if(myFileTask != null) {
            myFileTask.cancel(true);
        }
    }

    @Override
    public void startFileReceiver() {
        Log.e(TAG, "File reciever started");
        myFileTask = new FileTask(myContext);
        myFileTask.execute();
    }

    @Override
    public void addListener(SocketListenerInterface listener) {
        this.myListener = listener;
    }

}
