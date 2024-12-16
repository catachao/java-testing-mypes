package modules;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reports {
    private Inventory inventory;
    private Sales sales;

    public Reports(Inventory inventory, Sales sales) {
        this.inventory = inventory;
        this.sales = sales;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Reporte de Sistema ===\n");
        report.append("Fecha: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n\n");

        report.append("--- Inventario Actual ---\n");
        Map<String, Integer> products = inventory.getProducts();
        if (products.isEmpty()) {
            report.append("El inventario está vacío.\n");
        } else {
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        report.append("\n--- Resumen de Ventas ---\n");
        Map<String, Integer> salesSummary = new HashMap<>();
        for (Sales.Sale sale : sales.getSalesHistory()) {
            salesSummary.put(sale.getProductName(),
                    salesSummary.getOrDefault(sale.getProductName(), 0) + sale.getQuantity());
        }

        if (salesSummary.isEmpty()) {
            report.append("No se han registrado ventas.\n");
        } else {
            for (Map.Entry<String, Integer> entry : salesSummary.entrySet()) {
                report.append(entry.getKey()).append(": ").append(entry.getValue()).append(" unidades vendidas\n");
            }
        }

        report.append("\n--- Productos con Bajo Stock ---\n");
        boolean lowStockFound = false;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            if (entry.getValue() < 10) { // Consideramos bajo stock menos de 10 unidades
                report.append(entry.getKey()).append(": ").append(entry.getValue()).append(" (BAJO STOCK)\n");
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            report.append("No hay productos con bajo stock.\n");
        }

        return report.toString();
    }
}

