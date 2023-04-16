package juego;

import ficheros.GestionaFicheros;
import users.Partida;
import users.User;

import java.util.Collections;

public class TrivialAdmin {
    /**
     * Método que muestra las partidas jugadas y los usuarios registrados
     */
    public void administrar() {
        //Recorre el arraylist de partidas y muestra todas por pantalla
        for (String partida : GestionaFicheros.leePartidas()) {
            System.out.println(partida);
        }
        System.out.println("Partidas jugadas: " + GestionaFicheros.leePartidas().size());
        Collections.sort(GestionaFicheros.cargaUsers());
        System.out.println("Usuarios registrados: " + GestionaFicheros.cargaUsers().size());
        for (User user : GestionaFicheros.cargaUsers()) {
            System.out.println(user.getName());
        }
    }
}
