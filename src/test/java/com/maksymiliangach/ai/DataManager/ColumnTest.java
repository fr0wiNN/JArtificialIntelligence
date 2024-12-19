package com.maksymiliangach.ai.DataManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {

    private Column column;

    @BeforeEach
    void setUp() {
        column = new Column("column_name");
    }

    @Test
    void columnNameTest() {
        column = new Column("column_name");
        assertEquals("column_name", column.getName());
        column.setName("new_column_name");
        assertEquals("new_column_name", column.getName());
    }

    @Test
    void addTest() {
        column.add(0.0);
        column.add(10.5);
        column.add(-1.0);
        assertEquals(0.0, column.getRow(0));
        assertEquals(10.5, column.getRow(1));
        assertEquals(-1.0, column.getRow(2));
    }

    @Test
    void getRowTest() {
        column.add(10.5);
        column.add(20.5);
        assertEquals(10.5, column.getRow(0));
        assertEquals(20.5, column.getRow(1));
        assertThrows(IndexOutOfBoundsException.class, () -> column.getRow(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> column.getRow(2));
        column.removeRow(0);
        assertEquals(20.5, column.getRow(0));
    }

    @Test
    void removeRowTest() {
        column.add(10.5);
        column.add(20.5);
        column.add(30.5);
        assertThrows(IndexOutOfBoundsException.class, () -> column.removeRow(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> column.removeRow(3));
        assertEquals(10.5, column.getRow(0));
        column.removeRow(0);
        assertEquals(20.5, column.getRow(0));
        assertEquals(30.5, column.getRow(1));
        assertThrows(IndexOutOfBoundsException.class, () -> column.removeRow(2));
    }

    @Test
    void getSizeTest() {
        assertEquals(0, column.size());
        column.add(10.5);
        assertEquals(1, column.size());
        column.add(20.5);
        assertEquals(2, column.size());
        column.removeRow(0);
        assertEquals(1, column.size());
        column.removeRow(0);
        assertEquals(0, column.size());
    }

    @Test
    void toArrayTest() {
        column.add(-1.5);
        column.add(10.5);
        column.add(20.5);
        double[] expected1 = {-1.5, 10.5, 20.5};
        assertArrayEquals(expected1, column.toArray());

        column.removeRow(0);
        double[] expected2 = {10.5, 20.5};
        assertArrayEquals(expected2, column.toArray());

        column.removeRow(0);
        double[] expected3 = {20.5};
        assertArrayEquals(expected3, column.toArray());

        column.removeRow(0);
        double[] expected4 = {};
        assertArrayEquals(expected4, column.toArray());
    }

    @Test
    void copyTest() {
        column.add(10.5);
        column.add(20.5);
        Column copy = column.copy();

        // Check if column was copied successfully
        assertEquals(copy.getName(), column.getName());
        assertEquals(copy.getRow(0), column.getRow(0));
        assertArrayEquals(copy.toArray(), column.toArray());
        assertEquals(copy.size(), column.size());

        // Alter copy and look for any reference issues between original and copy column
        copy.removeRow(0);
        copy.setName("copy_column");
        assertNotEquals(copy, column);
        assertNotEquals(copy.getName(), column.getName());
        assertNotEquals(copy.getRow(0), column.getRow(0));
        assertNotEquals(copy.toArray(), column.toArray());
        assertNotEquals(copy.size(), column.size());
    }

    @Test
    void deepCopyTest() {
        column.add(10.5);
        column.add(20.5);
        Column copy = column.copy();
        Column copyOfCopy = copy.copy();

        // Check if copy of copy was created successfully
        assertEquals(copyOfCopy.getName(), copy.getName());
        assertArrayEquals(copyOfCopy.toArray(), copy.toArray());
        assertEquals(copyOfCopy.size(), copy.size());

        // Alter copyOfCopy and ensure no reference issues with original and copy
        copyOfCopy.removeRow(0);
        copyOfCopy.setName("copy_of_copy_column");
        assertNotEquals(copyOfCopy, copy);
        assertNotEquals(copyOfCopy, column);
        assertNotEquals(copyOfCopy.getName(), copy.getName());
        assertNotEquals(copyOfCopy.getRow(0), copy.getRow(0));
        assertNotEquals(copyOfCopy.toArray(), copy.toArray());
        assertNotEquals(copyOfCopy.size(), copy.size());
    }
}
