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
import dao.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
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

    private static void registerPlayer(Scanner sc) {
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
        User user = new Player(nombreP, passwordP);
        if (users.contains(user)) {
            System.out.println("El player ya existe");
            return;
        } else {
            Player player = new Player(nombreP, passwordP);
            users.add(player);
            userDAO.create(player);
            System.out.println("Player registrado");
        }
    }

    private static void registerAdmin(Scanner sc) {
        // Implementación del registro de Admin
        // Utiliza el DAO de User para almacenar el nuevo usuario en la BDOO
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
        User user = new Admin(nombreA, passwordA);
        if (users.contains(user)) {
            System.out.println("El admin ya existe");
        } else {
            Admin admin = new Admin(nombreA, passwordA);
            users.add(admin);
            userDAO.create(admin);
            System.out.println("Admin registrado");
        }
    }

    private static void login(Scanner sc) {
        // Implementación del inicio de sesión
        // Utiliza el DAO de User para buscar al usuario en la BDOO
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
                Player player = new Player(nombre, password);
                Partida partida = new Partida(Date.from(Instant.now()), 0, player);
                ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
                TrivialJuego juego = new TrivialJuego(preguntas, player, partida);
                juego.jugar();
            }
        } else {
            System.out.println("El usuario no existe");
        }
    }


    private static void searchUserByName(Scanner sc) {
        // Implementación de la búsqueda de usuarios por nombre
        // Utiliza el DAO de User para buscar al usuario en la BDOO
        System.out.println("Introduce el nombre del usuario a buscar");
        String nombre = sc.next();
        if (existName(nombre)) {
            EntityManager em = userDAO.em;
            TypedQuery query = em.createNamedQuery("User.findByName", User.class);
            query.setParameter("name", nombre);
            List<User> usuarios = query.getResultList();
            for (User user : usuarios){
                System.out.println(user);
            }
        } else {
            System.out.println("El usuario no existe");
        }

    }


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

    private static boolean existName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean existUser(String name, String pass) {
        for (User user : users) {
            if (user.getPass().equals(pass) && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}

