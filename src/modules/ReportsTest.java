package modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class ReportsTest {
    private Inventory inventory;
    private Sales sales;
    private Reports reports;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        sales = new Sales(inventory);
        reports = new Reports(inventory, sales);
        inventory.updateStock("Producto1", 10);
        inventory.updateStock("Producto2", 20);
    }

    @Test
    void testGenerateReport() {
        String report = reports.generateReport();
        assertTrue(report.contains("Producto1: 10"));
        assertTrue(report.contains("Producto2: 20"));
        assertTrue(report.contains("No se han registrado ventas."));
    }

    @Test
    void testGenerateReportAfterSale() {
        String input = "Producto1\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        sales.registerSale(new Scanner(System.in));

        String report = reports.generateReport();
        assertTrue(report.contains("Producto1: 5"));
        assertTrue(report.contains("Producto2: 20"));
        assertTrue(report.contains("Producto1: 5 unidades vendidas"));
    }

    @Test
    void testGenerateReportWithLowStock() {
        inventory.updateStock("ProductoBajo", 5);
        String report = reports.generateReport();
        assertTrue(report.contains("ProductoBajo: 5 (BAJO STOCK)"));
    }
}

