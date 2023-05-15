package juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dao.UserDAOImpl;
import users.User;

public class TrivialMainBDOO {

    private static final UserDAOImpl USER_DAO = new UserDAOImpl();
    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // Lectura inicial de todos los usuarios desde la BDOO
        users = USER_DAO.findAll();

        Scanner sc = new Scanner(System.in);
        int option;
        while (true) {
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
        // Implementación del registro de Player
        // Utiliza el DAO de User para almacenar el nuevo usuario en la BDOO
    }

    private static void registerAdmin(Scanner sc) {
        // Implementación del registro de Admin
        // Utiliza el DAO de User para almacenar el nuevo usuario en la BDOO
    }

    private static void login(Scanner sc) {
        // Implementación del inicio de sesión
        // Utiliza el DAO de User para buscar al usuario en la BDOO
    }

    private static void searchUserByName(Scanner sc) {
        System.out.print("Introduce el nombre del usuario que deseas buscar: ");
        String name = sc.nextLine();

        // Hacer la búsqueda utilizando una named query parametrizada
        List<User> result = USER_DAO.getEntityManager()
                .createNamedQuery("User.findByName", User.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();

        if (result.isEmpty()) {
            System.out.println("No se encontraron usuarios con el nombre especificado.");
            return;
        }

        System.out.println("Resultado de la búsqueda:\n");
        result.forEach(System.out::println);
    }

}
