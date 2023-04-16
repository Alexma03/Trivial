package juego;

import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

import java.util.*;

public class TrivialJuego {
    private final ArrayList<Pregunta> preguntas;
    private final Player player;
    private Partida partida;

    public TrivialJuego(ArrayList<Pregunta> preguntas, Player player, Partida partida) {
        this.preguntas = preguntas;
        this.player = player;
        this.partida = partida;
    }

    /**
     * Método que inicia el juego
     */
    public void jugar() {
        for (int i = 0; i <= 5; i++) {
            Pregunta pregunta = preguntas.get(i);
            System.out.println(pregunta.getPregunta());
            for (int j = 0; j < 4; j++) {
                System.out.println(j + ". " + pregunta.getOpciones()[j]);
            }
            System.out.println("Elige una opción: ");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if (pregunta.esCorrecta(opcion)) {
                partida.sumarPunto(1);
                System.out.println("Respuesta correcta");
            } else {
                System.out.println("La respuesta correcta es: " + pregunta.getOpcionCorrecta());
            }
        }
        System.out.println("Tu puntuación es: " + partida.getPuntuacion());
        GestionaFicheros.guardaPartida(partida);
    }

}
