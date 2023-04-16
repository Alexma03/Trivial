package juego;

import ficheros.GestionaFicheros;
import users.User;

import java.util.Collections;

public class TrivialAdmin {
    public void administrar() {
        //Recorre el arraylist de partidas y muestra todas por pantalla
        GestionaFicheros.leePartidas().forEach(System.out::println);
        //GestionaFicheros.cargaUsers().stream().sorted().forEach(user -> System.out.println(user.getName()));
        Collections.sort(GestionaFicheros.cargaUsers());
        for (User user : GestionaFicheros.cargaUsers()) {
            System.out.println(user.getName());
        }
    }
}
