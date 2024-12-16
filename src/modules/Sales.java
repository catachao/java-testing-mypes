package modules;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Sales {
    private Inventory inventory;
    private List<Sale> salesHistory;

    public Sales(Inventory inventory) {
        this.inventory = inventory;
        this.salesHistory = new ArrayList<>();
    }

    public void registerSale(Scanner scanner) {
        System.out.println("\n--- Registro de Venta ---");
        System.out.print("Ingrese el nombre del producto: ");
        String productName = scanner.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("Error: El nombre del producto no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese la cantidad vendida: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("Error: La cantidad debe ser un número positivo.");
                return;
            }

            int currentStock = inventory.getStock(productName);
            if (currentStock == 0) {
                System.out.println("Error: El producto no existe en el inventario.");
                return;
            }
            if (currentStock < quantity) {
                System.out.println("Error: Stock insuficiente. Stock actual: " + currentStock);
                return;
            }

            inventory.updateStock(productName, currentStock - quantity);
            salesHistory.add(new Sale(productName, quantity));
            System.out.println("Venta registrada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido para la cantidad.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Sale> getSalesHistory() {
        return new ArrayList<>(salesHistory); // Return a copy to prevent direct modification
    }

    public static class Sale {
        private String productName;
        private int quantity;
        private long timestamp;

        public Sale(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
            this.timestamp = System.currentTimeMillis();
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}

