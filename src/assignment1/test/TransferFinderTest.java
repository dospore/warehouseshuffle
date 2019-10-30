package assignment1.test;

import assignment1.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link TransferFinder} implementation class.
 * 
 * We will use a much more comprehensive test suite to test your code, so you
 * should add your own tests to this test suite to help you to debug your
 * implementation.
 */

public class TransferFinderTest {
    /**
     * Mega Test - 500 Warehouses, each only able to transfer ot one other
     */
    @Test(timeout = 500)
    public void customTest5() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 501;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        //Generate 99999 warehouses where each one can only transfer to the next one
        for (int i = 1; i <= 499; i++) {
            // Wi: 1/1 : (Ti,1), (Ti+1,0)
            capacity = 1;
            inventory = new HashMap<ItemType, Integer>();
            inventory.put(types[i], 0);
            inventory.put(types[i+1], 1);
            capacities.add(capacity);
            inventories.add(inventory);
        }

        // W100000  (T1000000, 0)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[500], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);

    }
    /**
     * Test from handout: a valid list of transfers exists and should be
     * returned by the TransferFinder.findTransfers method.
     */
    @Test(timeout = 500)
    public void handoutTest1() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T8, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W1: 0/2 : (T8,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W2: 2/2 : (T3,0), (T6,1), (T7,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 0);
        inventory.put(types[6], 1);
        inventory.put(types[7], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 1/1: (T1,0), (T2,1), (T9,0)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        inventory.put(types[9], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 2/2: (T4,1), (T6,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[6], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 3/3: (T1,1), (T3,2), (T6,0), (T8,0)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[3], 2);
        inventory.put(types[6], 0);
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 1/3: (T4,0), (T8,1)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 0);
        inventory.put(types[8], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W7: 4/4: (T8,4)
        capacity = 4;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 4);
        capacities.add(capacity);
        inventories.add(inventory);

        // W8: 3/3: (T2,0), (T7,0), (T8,0), (T9,3)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[2], 0);
        inventory.put(types[7], 0);
        inventory.put(types[8], 0);
        inventory.put(types[9], 3);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);

    }

    /**
     * Test full connectivity
     */
    @Test(timeout = 500)
    public void customTest4() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W1: 2/2 : (T1,1)(T2,1)(T3,0)(T4,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[2], 1);
        inventory.put(types[3], 0);
        inventory.put(types[4], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W2: 1/1 : (T1,0)(T2,1)(T3,0)(T4,0)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        inventory.put(types[3], 0);
        inventory.put(types[4], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 3/3 : (T1,2)(T2,0)(T3,0)(T4,1)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 2);
        inventory.put(types[2], 0);
        inventory.put(types[3], 0);
        inventory.put(types[4], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 2/2 : (T1,0)(T2,1)(T3,1)(T4,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        inventory.put(types[3], 1);
        inventory.put(types[4], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 1/2 : (T2,0)(T3,1)(T4,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        //inventory.put(types[1], 0);
        inventory.put(types[2], 0);
        inventory.put(types[3], 1);
        inventory.put(types[4], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 4/4 : (T1,1)(T2,1)(T3,1)(T4,1)
        capacity = 4;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[2], 1);
        inventory.put(types[3], 1);
        inventory.put(types[4], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);
    }

    /**
     * Test time on a big one with dead ends
     */
    @Test(timeout = 500)
    public void customTest3() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 1000;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        //W100

        //Generate 499 warehouses where each one can only transfer to the next one
        for (int i = 1; i < 499; i++) {
            // Wi: 3/3 : (Ti,1), (Ti+1,0) (Ti+2,0) (Ti+3,0)
            capacity = 3;
            inventory = new HashMap<ItemType, Integer>();
            inventory.put(types[i], 0);
            inventory.put(types[i+1], 1);
            inventory.put(types[i+2], 1);
            inventory.put(types[i+3], 1);
            capacities.add(capacity);
            inventories.add(inventory);
        }

        //connect the last one to the 499
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[500], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);
    }


    /**
     * Test time on a big one with no path
     */
    @Test(timeout = 500)
    public void customTest6() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 1000;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        //W100

        //Generate 499 warehouses where each one can only transfer to the next one
        for (int i = 1; i < 499; i++) {
            // Wi: 3/3 : (Ti,1), (Ti+1,0) (Ti+2,0) (Ti+3,0)
            capacity = 3;
            inventory = new HashMap<ItemType, Integer>();
            inventory.put(types[i], 0);
            inventory.put(types[i+1], 1);
            inventory.put(types[i+2], 1);
            inventory.put(types[i+3], 1);
            capacities.add(capacity);
            inventories.add(inventory);
        }

        //connect the last one to the 499
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[500], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that returned value is correct.
        Assert.assertTrue(transfers == null);
    }



    /**
     * Tests where there is only 1 free space */
    @Test(timeout = 500)
    public void handoutTest2() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W1: 0/2 : (T8,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 2);
        capacities.add(capacity);
        inventories.add(inventory);

        // W2: 2/2 : (T3,0), (T6,1), (T7,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 0);
        inventory.put(types[6], 1);
        inventory.put(types[7], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 1/1: (T1,0), (T2,1), (T9,0)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        inventory.put(types[9], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 2/2: (T4,1), (T6,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[6], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 3/3: (T1,1), (T3,2), (T6,0), (T8,0)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[3], 2);
        inventory.put(types[6], 0);
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 1/3: (T4,0), (T8,1)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[8], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W7: 4/4: (T8,4)
        capacity = 4;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 4);
        capacities.add(capacity);
        inventories.add(inventory);

        // W8: 3/3: (T2,0), (T7,0), (T8,0), (T9,3)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[2], 0);
        inventory.put(types[7], 0);
        inventory.put(types[8], 0);
        inventory.put(types[9], 3);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));
        //System.out.println(warehouses);
        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);
    }

    /**
     * Test to ensure that the graph correctly forms different non-connected components, and can
     * search both
     */
    @Test(timeout = 500)
    public void customTest2() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);


        // W1: 1/1 : (T1,0), (T2,1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        capacities.add(capacity);
        inventories.add(inventory);


        // W2: 1/1: (T3,1), (T2,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 1);
        inventory.put(types[2], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 1/2: (T3,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 1/1: (T6,1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[6], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 1/1: (T4,0), (T5,1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[5], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 0/1: (T5,1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[5], 0);
        capacities.add(capacity);
        inventories.add(inventory);


        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);

    }

    /**
     * Test from handout: a valid list of transfers exists and should be
     * returned by the TransferFinder.findTransfers method.
     */
    @Test(timeout = 500)
    public void customTest1() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W1: 0/2 : (T8,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W2: 2/2 : (T3,0), (T6,1), (T7,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 0);
        inventory.put(types[6], 1);
        inventory.put(types[7], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 1/1: (T2,1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[2], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 2/2: (T4,1), (T6,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[6], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 3/3: (T1,1), (T3,2), (T6,0), (T8,0)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[3], 2);
        inventory.put(types[6], 0);
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 1/3: (T4,0), (T8,1)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 0);
        inventory.put(types[8], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W7: 4/4: (T8,4)
        capacity = 4;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 4);
        capacities.add(capacity);
        inventories.add(inventory);

        // W8: 3/3: (T2,0), (T7,0), (T8,0), (T9,3)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[2], 0);
        inventory.put(types[7], 0);
        inventory.put(types[8], 0);
        inventory.put(types[9], 3);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));
        //System.out.println(warehouses);
        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that the sequence of transfers is valid.
        testTransfers(transfers, warehouses);
    }


    /**
     * Test from handout: no valid list of transfers exists. The value null
     * should be returned by the TransferFinder.findTransfers method.
     */
    @Test(timeout = 500)
    public void handoutTest3() {
        /* Initialise parameters to the test. */

        // Initialise the types that will be used.
        int numTypes = 10;
        ItemType[] types = new ItemType[numTypes];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ItemType(i);
        }

        // The capacities and inventories of each respective warehouse.
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Map<ItemType, Integer>> inventories = new ArrayList<>();
        Map<ItemType, Integer> inventory;
        int capacity;

        // W0 is the depot
        // W0: 1/1: (T1, 1)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W1: 0/2 : (T8,0)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W2: 2/2 : (T3,0), (T6,1), (T7,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[3], 0);
        inventory.put(types[6], 1);
        inventory.put(types[7], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W3: 1/1: (T1,0), (T2,1), (T9,0)
        capacity = 1;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 0);
        inventory.put(types[2], 1);
        inventory.put(types[9], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W4: 2/2: (T4,1), (T6,1)
        capacity = 2;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 1);
        inventory.put(types[6], 1);
        capacities.add(capacity);
        inventories.add(inventory);

        // W5: 3/3: (T1,1), (T3,2), (T6,0), (T8,0)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[1], 1);
        inventory.put(types[3], 2);
        inventory.put(types[6], 0);
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W6: 3/3: (T4,3), (T8,0)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[4], 3);
        inventory.put(types[8], 0);
        capacities.add(capacity);
        inventories.add(inventory);

        // W7: 4/4: (T8,4)
        capacity = 4;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[8], 4);
        capacities.add(capacity);
        inventories.add(inventory);

        // W8: 3/3: (T2,0), (T7,0), (T8,0), (T9,3)
        capacity = 3;
        inventory = new HashMap<ItemType, Integer>();
        inventory.put(types[2], 0);
        inventory.put(types[7], 0);
        inventory.put(types[8], 0);
        inventory.put(types[9], 3);
        capacities.add(capacity);
        inventories.add(inventory);

        // Create warehouses from capacities and inventories:
        // the first warehouse in the list is the depot.
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (int i = 0; i < capacities.size(); i++) {
            warehouses.add(new Warehouse("W" + i, capacities.get(i),
                    inventories.get(i)));
        }

        /* Run method on inputs and test result. */
        List<Transfer> transfers = TransferFinder
                .findTransfers(warehouses.get(0), new HashSet<Warehouse>(
                        warehouses.subList(1, warehouses.size())));

        // Test that the depot and warehouses have not been modified.
        testWarehouseState(warehouses, capacities, inventories);
        // Test that returned value is correct.
        Assert.assertTrue(transfers == null);
    }

    /*---Helper methods--------------------*/

    /**
     * Tests that each warehouse, warehouses.get(i), has capacity
     * capacities.get(i) and inventory inventories.get(i);
     */
    private void testWarehouseState(List<Warehouse> warehouses,
            ArrayList<Integer> capacities,
            ArrayList<Map<ItemType, Integer>> inventories) {
        for (int i = 0; i < capacities.size(); i++) {
            Warehouse w = warehouses.get(i);
            Assert.assertEquals((int) capacities.get(i), w.getCapacity());
            //System.out.println(String.format("%s and %s", w.getInventory(), inventories.get(i)));
            Assert.assertTrue(w.getInventory().equals(inventories.get(i)));
        }
    }

    /**
     * Takes a list of transfers, and (i) tests that it is not null; that all of
     * the transfers are non-null and are between the given warehouses; (ii)
     * performs all the transfers in sequence order, testing that each one is
     * possible as it is performed; (iii) tests that the depot
     * (warehouses.get(0)) is empty when all transfers are completed.
     */
    private void testTransfers(List<Transfer> transfers,
            List<Warehouse> warehouses) {
        // (i) Test that the list of transfers is not null, has no null
        // transfers.
        Assert.assertTrue(transfers != null);
        Assert.assertTrue(!transfers.contains(null));
        // Test that the transfers are between the given depot and warehouses.
        for (Transfer t : transfers) {
            Assert.assertTrue(warehouses.contains(t.getSource()));
            Assert.assertTrue(warehouses.contains(t.getDestination()));
        }
        // (ii) Perform all transfers in sequence order, testing that they are
        // possible as they are performed.
        for (Transfer t : transfers) {
            Assert.assertTrue(t.tranferCurrentlyPossible());
            t.makeTransfer();
        }
        // (iii) Test that the depot is empty at the end.
        Assert.assertEquals(0, (warehouses.get(0)).getNumberOfItems());
    }

}
