package ficheros;

import preguntas.Opcion;
import preguntas.Pregunta;
import users.Partida;
import users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GestionaFicheros {
    private static final File fileUser = new File("src/files/user.dat");
    private static final File filePreguntas = new File("src/files/preguntas.txt");
    private static final File filePartidas = new File("src/files/partidas.txt");

    /**
     * Método que carga los usuarios del fichero user.dat
     * @return Arraylist<User>
     */
    public static ArrayList<User> cargaUsers() {
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        ArrayList<User> userArrayList = new ArrayList<>();
        try {
            fis = new FileInputStream(fileUser);
            ois = new ObjectInputStream(fis);
            userArrayList = (ArrayList<User>) ois.readObject();
        } catch (EOFException e){

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userArrayList;
    }

    /**
     * Método que guarda los usuarios en el fichero user.dat
     * @param users ArrayList<User>
     */
    public static void guardaUsers(ArrayList<User> users) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileUser);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Método que carga las preguntas del fichero preguntas.txt
     * @return ArrayList<Pregunta>
     */
    public static ArrayList<Pregunta> cargaPreguntas() {
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        try {
            FileReader file = new FileReader(filePreguntas);
            BufferedReader in = new BufferedReader(file);
            String linea = "";
            while (linea != null) {
                linea = in.readLine();
                String pregunta = linea;
                Opcion[] opciones = new Opcion[4];
                for (int i = 0; i < 4; i++) {
                    linea = in.readLine();
                    if (i == 0) {
                        opciones[0] = new Opcion(linea, true);
                    } else {
                        opciones[i] = new Opcion(linea, false);
                    }
                }
                Collections.shuffle(Arrays.asList(opciones));
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

    /**
     * Método que guarda las partidas en el fichero partidas.txt
     * @param partida Partida
     */
    public static void guardaPartida(Partida partida) {
        try {
            if (!filePartidas.exists()) {
                filePartidas.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePartidas, true));
            bw.write(partida.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que lee las partidas del fichero partidas.txt
     * @return ArrayList<String>
     */
    public static ArrayList<String> leePartidas() {
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
