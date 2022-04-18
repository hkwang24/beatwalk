package com.example.beatwalk.bluetooth;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    private Handler handler; // handler that gets info from Bluetooth service
    private ConnectedThread ct;

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;
    }

    public void setup() {
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
        if (bt == null) {
            // Device doesn't support Bluetooth
            // TODO
        }
        if (!bt.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
            setThread();
        }
        // TODO: check whether permission was granted
    }

    public ConnectedThread setThread() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                // maybe check if its arduino

                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice btDevice = btAdapter.getRemoteDevice(deviceAddress);
                UUID uuid = btDevice.getUuids()[0].getUuid();
                try {
                    ct = new ConnectedThread(btDevice.createInsecureRfcommSocketToServiceRecord(uuid));
                    ct.run();
                } catch (Exception e) {

                }
            }
        }
        return null;
    }

    public String getMessage() {
        return handler.obtainMessage().toString();
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

            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                // TODO: catch this exception
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                // TODO: catch this exception
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes;
            while (true) {
                try {
                    numBytes = mmInStream.read(mmBuffer);
                    Message readMsg = handler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer
                    );
                } catch (IOException e) {
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
