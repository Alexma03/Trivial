package servidor;

import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.util.Collections.shuffle;

public class ServidorTrivial {
    private static ArrayList<Pregunta> preguntas;
    private static Semaphore semaforo;

    public static void main(String[] args) {
        preguntas = GestionaFicheros.cargaPreguntas();
        semaforo = new Semaphore(2);
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado. Esperando conexiones...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Conexión entrante de " + socket.getInetAddress());
                Thread hiloJugador = new Thread(new HiloJugador(socket));
                hiloJugador.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public static void adquirirSemaforo() throws InterruptedException {
        semaforo.acquire();
    }

    public static void liberarSemaforo() {
        semaforo.release();
    }
}

class HiloJugador implements Runnable {
    private Socket socket;
    private Player jugador;
    private ArrayList<Pregunta> preguntas;
    private Random random;

    public HiloJugador(Socket socket) {
        this.socket = socket;
        this.jugador = new Player("", "");
        try {
            preguntas = ServidorTrivial.getPreguntas();
            random = new Random();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ServidorTrivial.adquirirSemaforo();
            System.out.println("Semaforo adquirido por " + socket.getInetAddress());
            EnviarMensaje.enviar(socket, "Bienvenido al Trivial. ¿Cuál es tu nombre?");
            String nombre = RecibirMensaje.recibir(socket);
            jugador.setName(nombre);
            EnviarMensaje.enviar(socket, "¡Hola " + nombre + "! Bienvenido al Trivial.");
            int aciertos = 0;
            for (int i = 0; i < 5; i++) {
                int indicePregunta = random.nextInt(preguntas.size());
                Pregunta pregunta = preguntas.get(indicePregunta);
                EnviarMensaje.enviar(socket, "Pregunta " + (i + 1) + ": " + pregunta.getPregunta());
                for (int j = 0; j < pregunta.getOpciones().length; j++) {
                    EnviarMensaje.enviar(socket, (j + 1) + ". " + pregunta.getOpciones()[j].getEnunciado());
                }
                int respuesta = Integer.parseInt(RecibirMensaje.recibir(socket));
                if (pregunta.getOpciones()[respuesta - 1].isCorrecta()) {
                    aciertos++;
                    EnviarMensaje.enviar(socket, "1");
                    EnviarMensaje.enviar(socket, "¡Respuesta correcta!");
                } else {
                    EnviarMensaje.enviar(socket, "0");
                    EnviarMensaje.enviar(socket, "Respuesta incorrecta. La respuesta correcta era la opción " + (pregunta.getOpcionCorrecta()));
                }
            }
            EnviarMensaje.enviar(socket, "Has obtenido " + aciertos + " aciertos.");
            GestionaFicheros.guardaPartida(new Partida(Date.from(Instant.now()), aciertos, jugador));
            System.out.println("Semaforo liberado por " + socket.getInetAddress());
            ServidorTrivial.liberarSemaforo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}