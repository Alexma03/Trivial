import ficheros.GestionaFicheros;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < GestionaFicheros.cargaPreguntas().get(0).getOpciones().length; i++) {
            System.out.println(GestionaFicheros.cargaPreguntas().get(0).getOpciones()[i]);
        }
    }
}
