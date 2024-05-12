package org.caicoders.infrastructure;

import java.io.DataOutputStream;
import java.net.Socket;

public interface IConnection {
    void sendData();
    void task(Socket connectionSocket, DataOutputStream outToClient);
}
