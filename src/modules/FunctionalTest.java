package modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class FunctionalTest {
    private Inventory inventory;
    private Sales sales;
    private Reports reports;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        sales = new Sales(inventory);
        reports = new Reports(inventory, sales);
    }

    @Test
    void testFullSalesProcess() {
        // Agregar productos al inventario
        inventory.updateStock("Producto1", 50);
        inventory.updateStock("Producto2", 30);

        // Verificar el inventario inicial
        String initialReport = reports.generateReport();
        assertTrue(initialReport.contains("Producto1: 50"));
        assertTrue(initialReport.contains("Producto2: 30"));

        // Realizar ventas
        String input = "Producto1\n20\nProducto2\n15\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        sales.registerSale(scanner);
        sales.registerSale(scanner);

        // Verificar el inventario después de las ventas
        String afterSalesReport = reports.generateReport();
        assertTrue(afterSalesReport.contains("Producto1: 30"));
        assertTrue(afterSalesReport.contains("Producto2: 15"));
        assertTrue(afterSalesReport.contains("Producto1: 20 unidades vendidas"));
        assertTrue(afterSalesReport.contains("Producto2: 15 unidades vendidas"));

        // Intentar una venta que excede el stock
        input = "Producto1\n40\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        sales.registerSale(scanner);

        // Verificar que el inventario no ha cambiado después de la venta fallida
        String finalReport = reports.generateReport();
        assertTrue(finalReport.contains("Producto1: 30"));
        assertTrue(finalReport.contains("Producto2: 15"));
    }
}

