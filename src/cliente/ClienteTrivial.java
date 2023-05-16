package cliente;

import servidor.EnviarMensaje;
import servidor.RecibirMensaje;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteTrivial {
    public static void main(String[] args) throws Exception {
        // crear socket de cliente para conectarse al servidor
        Socket socket = new Socket("localhost", 12345);

        // recibir la pregunta "Cómo te llamas?" y enviar la respuesta
        String pregunta = RecibirMensaje.recibir(socket);
        System.out.println(pregunta);
        String respuesta = leerRespuestaDesdeConsola();
        EnviarMensaje.enviar(socket, respuesta);

        // recibir las primeras 5 preguntas y enviar las respuestas correspondientes
        int puntuacionTotal = 0;
        String bienvenida = RecibirMensaje.recibir(socket);
        System.out.println(bienvenida);
        for (int i = 0; i < 5; i++) {
            String preguntaActual = RecibirMensaje.recibir(socket); // recibir pregunta
            String opciones = RecibirMensaje.recibir(socket);
            opciones += "\n" + RecibirMensaje.recibir(socket);
            opciones += "\n" +  RecibirMensaje.recibir(socket);
            opciones += "\n" +  RecibirMensaje.recibir(socket);
            System.out.println(preguntaActual); // mostrar pregunta
            System.out.println(opciones); // mostrar opciones
            String respuestaActual = leerRespuestaDesdeConsola(); // leer respuesta desde la consola
            EnviarMensaje.enviar(socket, respuestaActual); // enviar respuesta al servidor
            String resultado = RecibirMensaje.recibir(socket); // recibir la puntuación obtenida
            int puntuacion = Integer.parseInt(resultado);
            puntuacionTotal += puntuacion; // actualizar la puntuación total
            System.out.println("Respuesta: " + (puntuacion == 1 ? "Correcto!" : "Incorrecto.") +
                    " Puntuación: " + puntuacion + "\n"); // mostrar resultado
        }

        // recibir el mensaje de despedida con la puntuación total
        String mensajeDespedida = RecibirMensaje.recibir(socket);
        System.out.println(mensajeDespedida + " Tu puntuación final es: " + puntuacionTotal);

        // cerrar el socket
        socket.close();
    }

    private static String leerRespuestaDesdeConsola() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Respuesta: ");
        return in.readLine();
    }
}