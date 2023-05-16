package juego;

import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

import java.util.*;

import static java.util.Collections.shuffle;

public class TrivialJuego {
    private ArrayList<Pregunta> preguntas;
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
        partida = new Partida(Date.from(java.time.Instant.now()), 0, player);
        preguntas = GestionaFicheros.cargaPreguntas();
        shuffle(preguntas);
        for (int i = 0; i < 5; i++) {
            Pregunta pregunta = preguntas.get(i);
            shuffle(Arrays.asList(pregunta.getOpciones()));
            System.out.println(preguntas.get(i).getPregunta());
            for (int j = 0; j < preguntas.get(i).getOpciones().length; j++) {
                System.out.println(j + ". " + preguntas.get(i).getOpciones()[j]);
            }
            System.out.println("Elige una opción: ");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.hasNextInt() ? sc.nextInt() : -1;
            if (opcion < 0 || opcion > 3) {
                System.out.println("Opción incorrecta");
                i--;
                continue;
            }

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
