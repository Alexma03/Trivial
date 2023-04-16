package juego;

import ficheros.GestionaFicheros;
import users.Admin;
import users.Player;
import users.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TrivialMain {
    private ArrayList<User> users;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Registro player \n2. Registro admin \n3. Inicio de sesión \n4. Salir");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
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
                if (GestionaFicheros.cargaUsers().contains(new Player(nombreP, passwordP))) {
                    System.out.println("El player ya existe");
                    break;
                } else {
                    GestionaFicheros.guardaUsers(GestionaFicheros.cargaUsers());
                    System.out.println("Player registrado");
                }
            case 2:
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
                if (GestionaFicheros.cargaUsers().contains(new Admin(nombreA, passwordA))) {
                    System.out.println("El admin ya existe");
                    break;
                } else {
                    GestionaFicheros.guardaUsers(GestionaFicheros.cargaUsers());
                    System.out.println("Admin registrado");
                    break;
                }
            case 3:
                System.out.println("Introduce tu nombre de usuario");
                String nombre = sc.next();
                System.out.println("Introduce tu contraseña");
                String password = sc.next();
                Player user = new Player(nombre, password);
                if (GestionaFicheros.cargaUsers().contains(user)) {
                    if (user.permisosAdmin()) {
                        System.out.println("Bienvenido admin" + nombre);
                        TrivialAdmin admin = new TrivialAdmin();
                        admin.administrar();
                        break;
                    } else {
                        System.out.println("Bienvenido " + nombre);
                        TrivialJuego juego = new TrivialJuego();
                        juego.jugar();
                        break;
                    }
                } else {
                    System.out.println("El usuario no existe");
                    break;
                }
            case 4:
                System.out.println("Hasta pronto");
                break;
        }
    }
}
