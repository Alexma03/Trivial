package juego;

import ficheros.GestionaFicheros;
import users.Admin;
import users.Partida;
import users.Player;
import users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class TrivialMain {
    private ArrayList<User> users;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("1. Registro player \n2. Registro admin \n3. Inicio de sesión \n4. Salir");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1 -> {
                    System.out.println("Introduce tu nombre de usuario");
                    String nombreP = sc.next();
                    System.out.println("Introduce tu contraseña");
                    String passwordP = sc.next();
                    System.out.println("Repite tu contraseña");
                    String password2P = sc.next();
                    if (!Objects.equals(passwordP, password2P)) {
                        System.out.println("Las contraseñas no coinciden");
                        break;
                    }
                    if (existUser(nombreP, passwordP)) {
                        System.out.println("El player ya existe");
                        break;
                    } else if (existName(nombreP)) {
                        System.out.println("El nombre de usuario ya existe");
                        break;
                    } else {
                        Player player = new Player(nombreP, passwordP);
                        ArrayList<User> users = GestionaFicheros.cargaUsers();
                        users.add(player);
                        GestionaFicheros.guardaUsers(users);
                        System.out.println("Player registrado");
                        break;
                    }
                }
                case 2 -> {
                    System.out.println("Introduce tu nombre de usuario");
                    String nombreA = sc.next();
                    System.out.println("Introduce tu contraseña");
                    String passwordA = sc.next();
                    System.out.println("Repite tu contraseña");
                    String password2A = sc.next();
                    if (!Objects.equals(passwordA, password2A)) {
                        System.out.println("Las contraseñas no coinciden");
                        break;
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
                case 3 -> {
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
                            TrivialJuego juego = new TrivialJuego(GestionaFicheros.cargaPreguntas(), player, partida);
                            juego.jugar();
                        }
                    } else {
                        System.out.println("El usuario no existe");
                    }
                }
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 4);

    }

    private static boolean existName(String name) {
        for (int i = 0; i < GestionaFicheros.cargaUsers().size(); i++) {
            if (GestionaFicheros.cargaUsers().get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean existUser(String name, String pass) {
        for (int i = 0; i < GestionaFicheros.cargaUsers().size(); i++) {
            if (GestionaFicheros.cargaUsers().get(i).getPass().equals(pass) && GestionaFicheros.cargaUsers().get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean tieneperimsos(String name, String pass) {
        for (int i = 0; i < GestionaFicheros.cargaUsers().size(); i++) {
            if (GestionaFicheros.cargaUsers().get(i).getPass().equals(pass) && GestionaFicheros.cargaUsers().get(i).getName().equals(name)) {
                if (GestionaFicheros.cargaUsers().get(i).permisosAdmin()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Admin admin(int key) {
        return (Admin) GestionaFicheros.cargaUsers().get(key);
    }

    private static Player player(int key) {
        return (Player) GestionaFicheros.cargaUsers().get(key);
    }
}
