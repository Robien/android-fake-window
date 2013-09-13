package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class SilentServer extends Thread {
    // The local server socket
    private final BluetoothServerSocket mmServerSocket;
    private String mSocketType;
    private  BluetoothAdapter mAdapter;

    public SilentServer(BluetoothAdapter adapter, boolean secure) {
    	mAdapter = adapter;
        BluetoothServerSocket tmp = null;
        mSocketType = secure ? "Secure":"Insecure";

        // Create a new listening server socket
        try {
			if (secure) {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord("test",
                   new UUID(0,42));
            } else {
                tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(
                        "test", new UUID(0,42));
            }
        } catch (IOException e) {
            Log.e("DEBUG", "Socket Type: " + mSocketType + "listen() failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
         Log.d("DEBUG", "Socket Type: " + mSocketType +
                "BEGIN mAcceptThread" + this);
        setName("AcceptThread" + mSocketType);

        BluetoothSocket socket = null;

        // Listen to the server socket if we're not connected
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e("DEBUG", "Socket Type: " + mSocketType + "accept() failed", e);
//                break;
            }

            // If a connection was accepted
            if (socket != null) {
            	Log.e("DEBUG", "connected to an app");
//                synchronized (BluetoothChatService.this) {
//                    switch (mState) {
//                    case STATE_LISTEN:
//                    case STATE_CONNECTING:
//                        // Situation normal. Start the connected thread.
//                        connected(socket, socket.getRemoteDevice(),
//                                mSocketType);
//                        break;
//                    case STATE_NONE:
//                    case STATE_CONNECTED:
//                        // Either not ready or already connected. Terminate new socket.
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            Log.e(TAG, "Could not close unwanted socket", e);
//                        }
//                        break;
//                    }
//                }
            }
        Log.i("DEBUG", "END mAcceptThread, socket Type: " + mSocketType);

    }

    public void cancel() {
//        if (D) Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
//        try {
//            mmServerSocket.close();
//        } catch (IOException e) {
//            Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
//        }
    }
}
