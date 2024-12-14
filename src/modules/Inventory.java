package modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
    private Map<String, Integer> products;

    public Inventory() {
        // Error: No se inicializan correctamente los productos.
        products = new HashMap<>();
    }

    public void manageInventory() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Gestión de Inventario ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Ver Inventario");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opción: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Ingrese el nombre del producto: ");
                    String productName = scanner.nextLine();
                    System.out.print("Ingrese la cantidad: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    products.put(productName, quantity);
                    System.out.println("Producto agregado correctamente.");
                    break;
                case "2":
                    System.out.println("\n--- Inventario ---");
                    for (Map.Entry<String, Integer> entry : products.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    public void printInventory() {
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public int getStock(String productName) {
        return products.getOrDefault(productName, 0);
    }

    public void updateStock(String productName, int newQuantity) {
        products.put(productName, newQuantity);
    }
}
