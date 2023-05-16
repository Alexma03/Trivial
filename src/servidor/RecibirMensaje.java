package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class RecibirMensaje {
    /**
     * Recibe un mensaje del socket indicado. El mensaje se recibe como un String.
     * @param socket
     * @return
     * @throws IOException
     */
    public static String recibir(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }
}