package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EnviarMensaje {
    /**
     * Envía un mensaje al socket indicado. El mensaje se envía como un String.
     * @param socket
     * @param mensaje
     * @throws IOException
     */
    public static void enviar(Socket socket, String mensaje) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(mensaje);
        out.flush();
    }
}