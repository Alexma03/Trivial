package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class RecibirMensaje {
    public static String recibir(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }
}