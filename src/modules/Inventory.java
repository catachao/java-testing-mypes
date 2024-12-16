package modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
    private Map<String, Integer> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public void manageInventory(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Gestión de Inventario ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Ver Inventario");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opción: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addProduct(scanner);
                        break;
                    case 2:
                        printInventory();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        System.out.print("Ingrese el nombre del producto: ");
        String productName = scanner.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("Error: El nombre del producto no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese la cantidad: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity < 0) {
                System.out.println("Error: La cantidad no puede ser negativa.");
                return;
            }
            updateStock(productName, quantity);
            System.out.println("Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido para la cantidad.");
        }
    }

    public void printInventory() {
        if (products.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            System.out.println("\n--- Inventario ---");
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public int getStock(String productName) {
        return products.getOrDefault(productName, 0);
    }

    public void updateStock(String productName, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        products.put(productName, newQuantity);
    }

    public Map<String, Integer> getProducts() {
        return new HashMap<>(products); // Return a copy to prevent direct modification
    }
}

