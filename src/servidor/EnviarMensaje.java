package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EnviarMensaje {
    public static void enviar(Socket socket, String mensaje) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(mensaje);
        out.flush();
    }
}