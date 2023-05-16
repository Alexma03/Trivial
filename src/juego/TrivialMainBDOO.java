package juego;

import java.time.Instant;
import java.util.*;

import dao.UserDAOImpl;
import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Admin;
import users.Partida;
import users.Player;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class TrivialMainBDOO {
    private static final UserDAOImpl userDAO = new UserDAOImpl();
    public static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option;
        while (true) {
            users.addAll(userDAO.findAll());
            System.out.println("====== MENU ======");
            System.out.println("1. Registro player");
            System.out.println("2. Registro admin");
            System.out.println("3. Inicio de sesión");
            System.out.println("4. Buscar users por nombre");
            System.out.println("0. Salir");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 ->
                    // Registro player
                        registerPlayer(sc);
                case 2 ->
                    // Registro admin
                        registerAdmin(sc);
                case 3 ->
                    // Inicio de sesión
                        login(sc);
                case 4 ->
                    // Buscar usuarios por nombre
                        searchUserByName(sc);
                case 0 -> {
                    System.out.println("Adiós!");
                    return;
                }
                default -> System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    /**
     * Metodo para registrar un player en la BDOO el cual se encarga de pedir los datos necesarios para crear un player
     * y comprobar que los datos introducidos son correctos y no existen en la BDOO.
     * @param sc
     */
    private static void registerPlayer(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombreP = sc.next();
        if (existName(nombreP)) { // Comprueba si el nombre de usuario ya existe
            System.out.println("El nombre de usuario ya existe");
            return;
        }
        System.out.println("Introduce tu contraseña");
        String passwordP = sc.next();
        if (passwordP.length() < 8) { // Comprueba si la contraseña tiene al menos 8 caracteres
            System.out.println("La contraseña debe tener al menos 8 caracteres");
            return;
        }
        System.out.println("Repite tu contraseña");
        String password2P = sc.next();
        if (!Objects.equals(passwordP, password2P)) { // Comprueba si las contraseñas coinciden
            System.out.println("Las contraseñas no coinciden");
            return;
        }
        User user = new Player(nombreP, passwordP);
        if (users.contains(user)) { // Comprueba si el player ya existe
            System.out.println("El player ya existe");
        } else { // Si no existe el player ni el nombre de usuario, se crea el player
            Player player = new Player(nombreP, passwordP);
            users.add(player);
            userDAO.create(player);
            System.out.println("Player registrado");
        }
    }

    /**
     * Metodo que registra un admin en la BDOO el cual se encarga de pedir los datos necesarios para crear un admin
     * y comprobar que los datos introducidos son correctos y no existen en la BDOO.
     * @param sc
     */
    private static void registerAdmin(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombreA = sc.next();
        if (existName(nombreA)) { // Comprueba si el nombre de usuario ya existe
            System.out.println("El nombre de usuario ya existe");
            return;
        }
        System.out.println("Introduce tu contraseña");
        String passwordA = sc.next();
        if (passwordA.length() < 8) { // Comprueba si la contraseña tiene al menos 8 caracteres
            System.out.println("La contraseña debe tener al menos 8 caracteres");
            return;
        }
        System.out.println("Repite tu contraseña");
        String password2A = sc.next();
        if (!Objects.equals(passwordA, password2A)) { // Comprueba si las contraseñas coinciden
            System.out.println("Las contraseñas no coinciden");
            return;
        }
        User user = new Admin(nombreA, passwordA);
        if (users.contains(user)) { // Comprueba si el admin ya existe
            System.out.println("El admin ya existe");
        } else { // Si no existe el admin, se crea el admin
            Admin admin = new Admin(nombreA, passwordA);
            users.add(admin);
            userDAO.create(admin);
            System.out.println("Admin registrado");
        }
    }

    /**
     * Metodo que Inicia sesion buscando que exista un usuario con el nombre y contraseña introducidos y comprobando si
     * el usuario tiene permisos de admin o no.
     * @return
     */
    private static void login(Scanner sc) {
        System.out.println("Introduce tu nombre de usuario");
        String nombre = sc.next();
        System.out.println("Introduce tu contraseña");
        String password = sc.next();
        if (existUser(nombre, password)) { // Comprueba si el usuario existe usando el metodo existUser
            if (tieneperimsos(nombre, password)) { // Comprueba si el usuario tiene permisos de admin usando el metodo tienePermisos
                System.out.println("Bienvenido admin " + nombre);
                TrivialAdmin administrador = new TrivialAdmin();
                administrador.administrar();
            } else { // Si no tiene permisos de admin, se inicia el juego
                System.out.println("Bienvenido " + nombre);
                Player player = new Player(nombre, password);
                Partida partida = new Partida(Date.from(Instant.now()), 0, player);
                ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
                TrivialJuego juego = new TrivialJuego(preguntas, player, partida);
                juego.jugar();
            }
        } else { // Si el usuario no existe, se muestra un mensaje de error
            System.out.println("El usuario no existe");
        }
    }


    /**
     * Metodo que comprueba si el nombre de usuario introducido existe en la BDOO.
     * @param sc
     * @return
     */
    private static void searchUserByName(Scanner sc) {
        System.out.println("Introduce el nombre del usuario a buscar");
        String nombre = sc.next();
        if (existName(nombre)) { // Comprueba si el nombre de usuario existe
            EntityManager em = userDAO.em;
            TypedQuery query = em.createNamedQuery("User.findByName", User.class); // Crea una query para buscar el usuario por nombre
            query.setParameter("name", nombre); // Le pasa el parametro nombre a la query
            List<User> usuarios = query.getResultList(); // Ejecuta la query y guarda el resultado en una lista
            for (User user : usuarios){ // Recorre la lista y muestra los usuarios que coinciden con el nombre
                System.out.println(user);
            }
        } else { // Si el nombre de usuario no existe, se muestra un mensaje de error
            System.out.println("El usuario no existe");
        }

    }


    /**
     * Metodo que comprueba si el nombre y pass introducidos pertenecen a un usuario con permisos de admin o no.
     * @param name
     * @param pass
     * @return
     */
    private static boolean tieneperimsos(String name, String pass) {
        for (User user : users) { // Recorre la lista de usuarios
            if (user.getPass().equals(pass) && user.getName().equals(name)) { // Comprueba si el nombre y pass coinciden con algun usuario
                if (user.permisosAdmin()) { // Comprueba si el usuario tiene permisos de admin
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo que comprueba si el nombre de usuario introducido existe o no.
     * @param name
     * @return
     */
    private static boolean existName(String name) {
        for (User user : users) { // Recorre la lista de usuarios
            if (user.getName().equals(name)) { // Comprueba si el nombre de usuario coincide con algun usuario
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que comprueba si el nombre y pass introducidos pertenecen a un usuario o no.
     * @param name
     * @param pass
     * @return
     */
    private static boolean existUser(String name, String pass) {
        for (User user : users) {
            if (user.getPass().equals(pass) && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}

