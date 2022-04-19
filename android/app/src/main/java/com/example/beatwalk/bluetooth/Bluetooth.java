package com.example.beatwalk.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.SongOne;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

public class Bluetooth {
    BluetoothAdapter bt;
    public Handler handler; // handler that gets info from Bluetooth service
    public ConnectedThread ct;
    public BluetoothDevice btd;
    Stack<String> msgs;
    public BluetoothSocket bts;
    public boolean waiting = false;

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
    }

    public void setup() {
        System.out.println("launching setup func");
        bt = BluetoothAdapter.getDefaultAdapter();
        msgs = new Stack<String>();
        msgs.add("start");
        btd = searchDevice();
        UUID uuid = btd.getUuids()[0].getUuid();
        System.out.println("device uuid: " + uuid.toString());
        try {
            bts = btd.createInsecureRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) {
            System.out.println("Error creating socket");
        }
        try {
            bts.connect();
        } catch (IOException e) {
            System.out.println("connection failed");
            try {
                System.out.println("backup connect");
                bts =(BluetoothSocket) btd.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(btd,1);
                bts.connect();
            } catch (Exception ee) {
                System.out.println(ee);
            }
        }
    }

    public void start() {
        try {
            ct = new ConnectedThread(bts);
            ct.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public BluetoothDevice searchDevice() {
        Set<BluetoothDevice> pairedDevices = bt.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                // maybe check if its arduino
                System.out.println("Device Name:" + deviceName);
                if (deviceName.equals("DSD TECH HC-05")) {
                    bt = BluetoothAdapter.getDefaultAdapter();
                    return bt.getRemoteDevice(deviceAddress);
                }
            }
        }
        return null;
    }

    public String getMessage() {
        System.out.println("CHECK MESSAGES");
        if (msgs.size() > 0) {
            String result = msgs.pop();
            msgs = new Stack<String>();
            msgs.add("start");
            waiting = false;
            return result;
        } else {
            return "NO_MSG";
        }
    }

    public boolean hasMessage() {
        return waiting;
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            System.out.println("ConnectedThread constructor");

            try {
                tmpIn = socket.getInputStream();
                System.out.println("got input stream");
            } catch (IOException e) {
                System.out.println("input socket fail");
                // TODO: catch this exception
            }
            try {
                tmpOut = socket.getOutputStream();
                System.out.println("got output stream");
            } catch (IOException e) {
                System.out.println("output socket fail");
                // TODO: catch this exception
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            int numBytes;
            String inter = "";
            boolean hasInter = false;
            while (true) {
                try {
                    if (mmInStream.available() > 0) {
                        mmBuffer = new byte[1024];
                        numBytes = mmInStream.read(mmBuffer);
                        if (numBytes > 0) {
                            String curr_msg = new String(mmBuffer.clone(), StandardCharsets.UTF_8);
                            String strip = curr_msg.replaceAll("[^\\d,;]", "");
                            System.out.println("start msg");
                            if (strip.split(",").length != 16) {
                                if (hasInter) {
                                    String new_msg = inter + strip;
                                    System.out.println(new_msg);
                                    System.out.flush();
                                    msgs.push(new_msg);
                                    waiting = true;
                                    hasInter = false;
                                } else {
                                    hasInter = true;
                                    inter = strip;
                                }
                            } else {
                                hasInter = false;
                                inter = "";
                                System.out.println(strip);
                                System.out.flush();
                                msgs.push(strip);
                                waiting = true;
                                System.out.println("Stack size: " + Integer.toString(msgs.size()));
                            }
                        }
                        SystemClock.sleep(300);
                    } else {
                        SystemClock.sleep(400);
                    }
                } catch (IOException e) {
                    System.out.println("Something broke");
                    System.out.println(e);
                    // TODO: catch exception
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                Message writtenMsg = handler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer
                );
                writtenMsg.sendToTarget();

            } catch (IOException e) {
                // TODO: catch exception
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                // TODO: catch exception
            }
        }
    }

}
/* STUPID SETUP CODE I MIGHT NEED LATER */
/****************************************/
//        requestPermissionLauncher =
//                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                System.out.println("GRANTED");
//                bt = BluetoothAdapter.getDefaultAdapter();
//                if (bt == null) {
//                // Device doesn't support Bluetooth
//                // TODO
//                }
//                if (!bt.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 2);
//                }
//                System.out.println("Got here");
//                setThread();
//                } else {
//                System.out.println("NOT GRANTED");
//                // Explain to the user that the feature is unavailable because the
//                // features requires a permission that the user has denied. At the
//                // same time, respect the user's decision. Don't link to system
//                // settings in an effort to convince the user to change their
//                // decision.
//                }
//                });

//        handler = new Handler(Looper.getMainLooper()) {
//@Override
//public void handleMessage(Message msg) {
//        switch (msg.what) {
//        case MessageConstants.MESSAGE_READ:
//        msgs.push(msg.obj.toString());
//        break;
//        }
//        }
//        };

//        requestPermissionLauncher.launch(
//                    Manifest.permission.BLUETOOTH_ADMIN);


/* handler code */
//        if (handler.hasMessages(0)) {
//            return handler.obtainMessage().toString();
//        } else {
//            return "";
//        }
