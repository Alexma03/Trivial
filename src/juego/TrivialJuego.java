package juego;

import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

import java.util.*;

public class TrivialJuego {
    private ArrayList<Pregunta> preguntas;
    private Player player;
    private Partida partida;

    public void jugar() {
        Partida partida = new Partida(Date.from(java.time.Instant.now()), 0, player);
        //cargar todas las preguntas del fichero y motrar 5 desordenadas aleatoriamente. Si acierta se suma un punto
        ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
        Collections.shuffle(preguntas);
        for (int i = 0; i < 5; i++) {
            Pregunta pregunta = preguntas.get(i);
            System.out.println(pregunta.getPregunta());
            System.out.println(Arrays.toString(pregunta.getOpciones()));
            System.out.println("Elige una opción: ");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if (pregunta.esCorrecta(opcion)) {
                partida.sumarPunto(1);
            } else {
                //muestra la respuesta correcta
                System.out.println("La respuesta correcta es: " + pregunta.getOpcionCorrecta());
            }
        }
    }

}
