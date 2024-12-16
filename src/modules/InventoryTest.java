package modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testUpdateAndGetStock() {
        inventory.updateStock("Producto1", 10);
        assertEquals(10, inventory.getStock("Producto1"));
    }

    @Test
    void testGetStockNonExistentProduct() {
        assertEquals(0, inventory.getStock("ProductoInexistente"));
    }

    @Test
    void testUpdateExistingStock() {
        inventory.updateStock("Producto2", 5);
        inventory.updateStock("Producto2", 8);
        assertEquals(8, inventory.getStock("Producto2"));
    }

    @Test
    void testUpdateStockWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> inventory.updateStock("Producto3", -1));
    }

    @Test
    void testAddProductWithValidInput() {
        String input = "NuevoProducto\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        inventory.manageInventory(new Scanner(System.in));
        assertEquals(5, inventory.getStock("NuevoProducto"));
    }

    @Test
    void testAddProductWithInvalidQuantity() {
        String input = "ProductoInvalido\nabc\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        inventory.manageInventory(new Scanner(System.in));
        assertEquals(0, inventory.getStock("ProductoInvalido"));
    }
}

