package ficheros;

import preguntas.Opcion;
import preguntas.Pregunta;
import users.Partida;
import users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GestionaFicheros {
    private static final File filePreguntas = new File("files\\preguntas.txt");
    private static final File fileUser = new File("files\\user.dat");
    private static final File filePartidas = new File("files\\partidas.txt");

    public static ArrayList<User> cargaUsers () {
        ArrayList<User> users = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(fileUser);
             ObjectInputStream in = new ObjectInputStream(file)) {
            users = new ArrayList<>(Arrays.asList((User[]) in.readObject()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void guardaUsers (ArrayList<User> users) {
        try {
            FileOutputStream file = new FileOutputStream(fileUser);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(users);

            out.close();
            file.close();

            System.out.println("Datos guardados en el archivo: " + fileUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pregunta> cargaPreguntas () {
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        try {
            FileReader file = new FileReader(filePreguntas);
            BufferedReader in = new BufferedReader(file);
            String linea = in.readLine();
            while (linea != null) {
                String pregunta = linea;
                Opcion[] opciones = new Opcion[4];
                for (int i = 1; i < 4; i++) {
                    linea = in.readLine();
                    if (i == 1) {
                        opciones[0] = new Opcion(linea, true);
                    } else {
                        opciones[i] = new Opcion(linea, false);
                    }
                }
                preguntas.add(new Pregunta(pregunta, opciones));
            }
            in.close();
            file.close();
            return preguntas;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    public static void guardaPartida (Partida partida) {
        try {
            FileWriter file = new FileWriter(filePartidas, true);
            BufferedWriter out = new BufferedWriter(file);
            out.write(partida.toString());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> leePartidas () {
        ArrayList<String> partidas = new ArrayList<>();
        try {
            FileReader file = new FileReader(filePartidas);
            BufferedReader in = new BufferedReader(file);

            String linea = in.readLine();
            while (linea != null) {
                partidas.add(linea);
                linea = in.readLine();
            }
            in.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partidas;
    }
}
