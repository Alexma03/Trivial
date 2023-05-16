package juego;

import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Admin;
import users.Partida;
import users.Player;
import users.User;

import java.util.*;

public class TrivialMain {
    private static ArrayList<User> users;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("1. Registro player \n2. Registro admin \n3. Inicio de sesión \n4. Salir");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1 -> {
                    registrarPlayer(sc);
                }
                case 2 -> {
                    registrarAdmin(sc);
                }
                case 3 -> {
                    inicioSesion(sc);
                } case 4 -> System.out.println("Hasta pronto");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 4);

    }

    /**
     * Método que registra un player
     * @param sc
     */
    private static void registrarPlayer(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombreP = sc.next();
        System.out.println("Introduce tu contraseña");
        String passwordP = sc.next();
        System.out.println("Repite tu contraseña");
        String password2P = sc.next();
        if (!Objects.equals(passwordP, password2P)) {
            System.out.println("Las contraseñas no coinciden");
            return;
        }
        if (existUser(nombreP, passwordP)) {
            System.out.println("El player ya existe");
            return;
        } else if (existName(nombreP)) {
            System.out.println("El nombre de usuario ya existe");
            return;
        } else {
            Player player = new Player(nombreP, passwordP);
            ArrayList<User> users = GestionaFicheros.cargaUsers();
            users.add(player);
            GestionaFicheros.guardaUsers(users);
            System.out.println("Player registrado");
            return;
        }
    }

    /**
     * Método que registra un admin
     * @param sc
     */
    private static void registrarAdmin(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombreA = sc.next();
        System.out.println("Introduce tu contraseña");
        String passwordA = sc.next();
        System.out.println("Repite tu contraseña");
        String password2A = sc.next();
        if (!Objects.equals(passwordA, password2A)) {
            System.out.println("Las contraseñas no coinciden");
            return;
        }
        if (existUser(nombreA, passwordA)) {
            System.out.println("El admin ya existe");
        } else if (existName(nombreA)) {
            System.out.println("El nombre de usuario ya existe");
        } else {
            Admin admin = new Admin(nombreA, passwordA);
            ArrayList<User> users = GestionaFicheros.cargaUsers();
            users.add(admin);
            GestionaFicheros.guardaUsers(users);
            System.out.println("Admin registrado");
        }
    }

    /**
     * Método que inicia sesión
     * @param sc
     */
    private static void inicioSesion(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombre = sc.next();
        System.out.println("Introduce tu contraseña");
        String password = sc.next();
        if (existUser(nombre, password)) {
            if (tieneperimsos(nombre, password)) {
                System.out.println("Bienvenido admin " + nombre);
                TrivialAdmin administrador = new TrivialAdmin();
                administrador.administrar();
            } else {
                System.out.println("Bienvenido " + nombre);
                Player player = new  Player(nombre, password);
                Partida partida = new Partida(Date.from(java.time.Instant.now()), 0, player);
                ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
                TrivialJuego juego = new TrivialJuego(preguntas, player, partida);
                juego.jugar();
            }
        } else {
            System.out.println("El usuario no existe");
        }
    }

    /**
     * Método que comprueba si existe un usuario con el mismo nombre
     * @param name
     * @return true si existe, false si no existe
     */
    private static boolean existName(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que comprueba si existe un usuario con el mismo nombre y contraseña
     * @param name
     * @param pass
     * @return true si existe, false si no existe
     */
    private static boolean existUser(String name, String pass) {
        for (User user : users) {
            if (user.getPass().equals(pass) && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que comprueba si el usuario tiene permisos de admin
     * @param name
     * @param pass
     * @return true si tiene permisos, false si no tiene permisos
     */
    private static boolean tieneperimsos(String name, String pass) {
        for (User user : users) {
            if (user.getPass().equals(pass) && user.getName().equals(name)) {
                if (user.permisosAdmin()) {
                    return true;
                }
            }
        }
        return false;
    }
}
