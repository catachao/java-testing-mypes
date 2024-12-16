package modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class PerformanceTest {
    private Inventory inventory;
    private Sales sales;
    private Reports reports;
    private Random random;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        sales = new Sales(inventory);
        reports = new Reports(inventory, sales);
        random = new Random();
    }

    @Test
    void testLargeInventoryPerformance() {
        int numProducts = 10000;
        long startTime = System.nanoTime();

        for (int i = 0; i < numProducts; i++) {
            inventory.updateStock("Producto" + i, random.nextInt(1000));
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

        System.out.println("Tiempo para agregar " + numProducts + " productos: " + duration + " ms");
        assertTrue(duration < 1000, "La operación de agregar productos al inventario tomó más de 1 segundo");
    }

    @Test
    void testMultipleSalesPerformance() {
        int numSales = 1000;
        String[] products = new String[100];

        // Preparar el inventario
        for (int i = 0; i < 100; i++) {
            String product = "Producto" + i;
            products[i] = product;
            inventory.updateStock(product, 1000);
        }

        long startTime = System.nanoTime();

        for (int i = 0; i < numSales; i++) {
            String product = products[random.nextInt(products.length)];
            inventory.updateStock(product, inventory.getStock(product) - (random.nextInt(10) + 1));
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

        System.out.println("Tiempo para procesar " + numSales + " ventas: " + duration + " ms");
        assertTrue(duration < 500, "La operación de procesar ventas tomó más de 500 milisegundos");
    }

    @Test
    void testReportGenerationPerformance() {
        int numProducts = 5000;

        // Preparar un inventario grande
        for (int i = 0; i < numProducts; i++) {
            inventory.updateStock("Producto" + i, random.nextInt(1000));
        }

        long startTime = System.nanoTime();

        String report = reports.generateReport();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

        System.out.println("Tiempo para generar reporte con " + numProducts + " productos: " + duration + " ms");
        assertTrue(duration < 200, "La generación del reporte tomó más de 200 milisegundos");
        assertTrue(report.length() > 0, "El reporte generado está vacío");
    }
}

