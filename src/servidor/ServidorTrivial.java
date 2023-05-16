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

/**
 * Clase que implementa el servidor del trivial. Carga las preguntas desde el fichero, las baraja y espera a que se conecten los jugadores.
 */
public class ServidorTrivial {
    private static ArrayList<Pregunta> preguntas;
    private static Semaphore semaforo;

    /**
     * Método principal del servidor. Carga las preguntas desde el fichero, las baraja y espera a que se conecten los jugadores.
     * @param args
     */
    public static void main(String[] args) {
        preguntas = GestionaFicheros.cargaPreguntas();
        shuffle(preguntas);
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

    /**
     * Devuelve las preguntas del trivial.
     * @return
     */
    public static ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    /**
     * Adquiere el semáforo. Si no está disponible, espera hasta que lo esté.
     * @throws InterruptedException
     */
    public static void adquirirSemaforo() throws InterruptedException {
        semaforo.acquire();
    }

    /**
     * Libera el semáforo.
     */
    public static void liberarSemaforo() {
        semaforo.release();
    }
}

/**
 * Clase que implementa el hilo del jugador. Se encarga de gestionar la partida de un jugador.
 */
class HiloJugador implements Runnable {
    private Socket socket;
    private Player jugador;
    private ArrayList<Pregunta> preguntas;
    private Random random;

    /**
     * Constructor de la clase. Recibe el socket del jugador y carga las preguntas del trivial.
     * @param socket
     */
    public HiloJugador(Socket socket) {
        this.socket = socket; // Guarda el socket del jugador
        this.jugador = new Player("", ""); // Inicializa el jugador
        try {
            preguntas = ServidorTrivial.getPreguntas(); // Carga las preguntas del trivial
            random = new Random(); // Inicializa el generador de números aleatorios
        } catch (Exception e) {
            e.printStackTrace(); // Muestra la excepción por pantalla
        }
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo. Gestiona la partida del jugador.
     * En primer lugar, se le pide el nombre al jugador y se le da la bienvenida.
     * A continuación, se le muestran las preguntas y se le pide que elija una opción.
     * Si acierta, se le muestra un mensaje de felicitación. Si falla, se le muestra la respuesta correcta.
     * Al finalizar la partida, muestra la puntuacion obtenida
     * Si se produce una excepción, se muestra por pantalla.
     * Finalmente, se cierra el socket.
     */
    @Override
    public void run() {
        try {
            ServidorTrivial.adquirirSemaforo(); // Adquiere el semáforo
            System.out.println("Semaforo adquirido por " + socket.getInetAddress()); // Muestra la IP del jugador que ha adquirido el semáforo
            EnviarMensaje.enviar(socket, "Bienvenido al Trivial. ¿Cuál es tu nombre?"); // Pide el nombre al jugador
            String nombre = RecibirMensaje.recibir(socket); // Recibe el nombre del jugador
            jugador.setName(nombre); // Guarda el nombre del jugador
            EnviarMensaje.enviar(socket, "¡Hola " + nombre + "! Bienvenido al Trivial."); // Da la bienvenida al jugador
            int aciertos = 0;
            for (int i = 0; i < 5; i++) {
                Pregunta pregunta = preguntas.get(i); // Obtiene la pregunta
                shuffle(Arrays.asList(pregunta.getOpciones())); // Baraja las opciones
                EnviarMensaje.enviar(socket, "Pregunta " + (i + 1) + ": " + preguntas.get(i).getPregunta()); // Envía la pregunta
                for (int j = 0; j < preguntas.get(i).getOpciones().length; j++) {
                    EnviarMensaje.enviar(socket, (j + 1) + ". " + preguntas.get(i).getOpciones()[j]); // Envía las opciones
                }
                int respuesta = Integer.parseInt(RecibirMensaje.recibir(socket)); // Recibe la respuesta del jugador
                if (pregunta.getOpciones()[respuesta - 1].isCorrecta()) { // Comprueba si la respuesta es correcta
                    aciertos++;
                    EnviarMensaje.enviar(socket, "1");
                    EnviarMensaje.enviar(socket, "¡Respuesta correcta!");
                } else {
                    EnviarMensaje.enviar(socket, "0");
                    EnviarMensaje.enviar(socket, "Respuesta incorrecta. La respuesta correcta era la opción " + (pregunta.getOpcionCorrecta())); // Envía la respuesta correcta
                }
            }
            EnviarMensaje.enviar(socket, "Has obtenido " + aciertos + " aciertos."); // Envía la puntuación
            GestionaFicheros.guardaPartida(new Partida(Date.from(Instant.now()), aciertos, jugador)); // Guarda la partida
            System.out.println("Semaforo liberado por " + socket.getInetAddress()); // Muestra la IP del jugador que ha liberado el semáforo
            ServidorTrivial.liberarSemaforo(); // Libera el semáforo
        } catch (Exception e) {
            e.printStackTrace(); // Muestra la excepción por pantalla
        } finally {
            try {
                socket.close(); // Cierra el socket
            } catch (IOException e) {
                e.printStackTrace(); // Muestra la excepción por pantalla
            }
        }
    }
}