package modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class SalesTest {
    private Inventory inventory;
    private Sales sales;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        sales = new Sales(inventory);
        inventory.updateStock("Producto1", 10);
    }

    @Test
    void testRegisterValidSale() {
        String input = "Producto1\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        sales.registerSale(new Scanner(System.in));
        assertEquals(5, inventory.getStock("Producto1"));
        assertEquals(1, sales.getSalesHistory().size());
    }

    @Test
    void testRegisterSaleInsufficientStock() {
        String input = "Producto1\n15\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        sales.registerSale(new Scanner(System.in));
        assertEquals(10, inventory.getStock("Producto1"));
        assertEquals(0, sales.getSalesHistory().size());
    }

    @Test
    void testRegisterSaleNonExistentProduct() {
        String input = "ProductoInexistente\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        sales.registerSale(new Scanner(System.in));
        assertEquals(0, sales.getSalesHistory().size());
    }

    @Test
    void testRegisterSaleInvalidQuantity() {
        String input = "Producto1\nabc\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        sales.registerSale(new Scanner(System.in));
        assertEquals(10, inventory.getStock("Producto1"));
        assertEquals(0, sales.getSalesHistory().size());
    }
}

