package juego;

import ficheros.GestionaFicheros;
import users.Partida;
import users.User;

import java.io.IOException;
import java.util.Collections;

public class TrivialAdmin {
    /**
     * Método que muestra las partidas jugadas y los usuarios registrados
     */
    public void administrar() {
        System.out.println("Partidas jugadas: ");
        for (String partida : GestionaFicheros.leePartidas()) {
            System.out.println(partida);
        }
        System.out.println("Usuarios registrados: ");
        for (User user : TrivialMainBDOO.users) {
            System.out.println(user.getName());
        }
    }
}
