package ficheros;

import preguntas.Pregunta;
import users.Partida;
import users.User;

import java.io.File;
import java.util.ArrayList;

public class GestionaFicheros {
    private static final File filePreguntas = new File("files\\preguntas.txt");
    private static final File fileUser = new File("files\\user.dat");
    private static final File filePartidas = new File("files\\partidas.txt");

    public static ArrayList<User> cargaUsers () {
        return null;
    }

    public static void guardaUsers (ArrayList<User> users) {

    }

    public static ArrayList<Pregunta> cargaPreguntas () {
        return null;
    }

    public static void guardaPartida (Partida partida) {

    }

    public static ArrayList<String> leePartidas () {
        return null;
    }
}
