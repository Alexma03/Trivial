package cliente;

import servidor.EnviarMensaje;
import servidor.RecibirMensaje;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteTrivial {
    /**
     * Método principal del cliente. Se conecta al servidor y juega al trivial. Al finalizar la partida, muestra la puntuación obtenida.
     * @param args
     * @throws Exception si se produce un error al leer la respuesta desde la consola
     */
    public static void main(String[] args) throws Exception {
        // Crear socket de cliente para conectarse al servidor
        Socket socket = new Socket("localhost", 12345);

        // Recibir la pregunta "Cómo te llamas?" y enviar la respuesta
        String pregunta = RecibirMensaje.recibir(socket);
        System.out.println(pregunta);
        String respuesta = leerRespuestaDesdeConsola();
        EnviarMensaje.enviar(socket, respuesta);

        // Recibir las primeras 5 preguntas y enviar las respuestas correspondientes
        int puntuacionTotal = 0;
        String bienvenida = RecibirMensaje.recibir(socket);
        System.out.println(bienvenida);
        for (int i = 0; i < 5; i++) {
            String preguntaActual = RecibirMensaje.recibir(socket); // Recibir pregunta
            String opciones = RecibirMensaje.recibir(socket); // Recibir opciones
            opciones += "\n" + RecibirMensaje.recibir(socket);
            opciones += "\n" +  RecibirMensaje.recibir(socket);
            opciones += "\n" +  RecibirMensaje.recibir(socket);
            System.out.println(preguntaActual); // Mostrar pregunta
            System.out.println(opciones); // Mostrar opciones
            String respuestaActual = leerRespuestaDesdeConsola(); // Leer respuesta desde la consola
            EnviarMensaje.enviar(socket, respuestaActual); // enviar respuesta al servidor
            String resultado = RecibirMensaje.recibir(socket); // Recibir la puntuación obtenida
            int puntuacion = Integer.parseInt(resultado);
            puntuacionTotal += puntuacion; // Actualizar la puntuación total
            System.out.println(RecibirMensaje.recibir(socket));
            System.out.println("Puntuación Actual: " + puntuacionTotal + "\n"); // Mostrar resultado
        }

        // recibir el mensaje de despedida con la puntuación total
        String mensajeDespedida = RecibirMensaje.recibir(socket);
        System.out.println(mensajeDespedida + " Tu puntuación final es: " + puntuacionTotal);

        // cerrar el socket
        socket.close();
    }

    /**
     * Lee la respuesta desde la consola y la devuelve como un String.
     * @return
     * @throws Exception si se produce un error al leer la respuesta
     */
    private static String leerRespuestaDesdeConsola() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Respuesta: ");
        return in.readLine();
    }
}