package assignment1;

import java.util.*;

/**
 * A class representing a warehouse and its current inventory of items.
 * 
 * DO NOT MODIFY THIS FILE IN ANY WAY.
 */
public final class Warehouse {

    // the name of warehouse
    private final String name;
    // the maximum number of items that can be stored in the warehouse
    private final int capacity;
    // the total number of items current stored in the warehouse
    private int totalItems;
    // a mapping from the types of items that can be stored in this particular
    // warehouse, to the number of each such item that is currently stored
    private Map<ItemType, Integer> inventory;

    /*
     * class invariant: name != null && capacity > 0 && inventory != null &&
     * inventory.size() > 0 && for each type of item in the inventory,
     * inventory.get(type) >= 0 && totalItems is the sum of the number of each
     * type of item in inventory && totalItems <= capacity
     */

    /**
     * @require name != null && capacity > 0 && validTypes != null &&
     *          !validTypes.contains(null) && validTypes.size() > 0
     * @ensure Creates a new empty warehouse with the given name, capacity, and
     *         set of items that can be stored at the warehouse.
     */
    public Warehouse(String name, int capacity, Set<ItemType> validTypes) {
        if (name == null) {
            throw new IllegalArgumentException(
                    "The warehouse name cannot be null.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "The capacity of the warehouse must be positive.");
        }
        if (validTypes == null || validTypes.size() == 0
                || validTypes.contains(null)) {
            throw new IllegalArgumentException(
                    "The types of items that can be stored in the warehouse "
                            + "must be non-null and must contain at least "
                            + "one type of item.");
        }
        this.name = name;
        this.capacity = capacity;
        this.totalItems = 0;
        this.inventory = new HashMap<ItemType, Integer>();
        for (ItemType type : validTypes) {
            inventory.put(type, 0);
        }
    }

    /**
     * @require name != null && capacity > 0 && inventory != null &&
     *          inventory.size() > 0 && the total number of items stored in the
     *          inventory does not exceed the capacity.
     * 
     * @ensure Creates a new warehouse with the given name and capacity, that
     *         can store the type of items in inventory.keySet(), and currently
     *         stores the the number of each item specified by the inventory
     *         parameter.
     */
    public Warehouse(String name, int capacity,
            Map<ItemType, Integer> inventory) {
        if (name == null) {
            throw new IllegalArgumentException(
                    "The warehouse name cannot be null.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "The capacity of the warehouse must be positive.");
        }
        if (inventory.isEmpty()) {
            throw new IllegalArgumentException(
                    "The warehouse must be able to store at least"
                            + " one type of item.");
        }
        this.name = name;
        this.capacity = capacity;
        this.totalItems = 0;
        this.inventory = new HashMap<ItemType, Integer>(inventory);
        for (ItemType type : inventory.keySet()) {
            totalItems = totalItems + inventory.get(type);
        }
        if (totalItems > capacity) {
            throw new IllegalArgumentException(
                    "The warehouse cannot contain more items than "
                            + "its capacity.");
        }
    }

    /**
     * @ensure Returns name of the warehouse.
     */
    public String getName() {
        return name;
    }

    /**
     * @ensure Returns the total number of items that can be stored in the
     *         warehouse.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @ensure Returns the total number of items currently being stored in the
     *         warehouse.
     */
    public int getNumberOfItems() {
        return totalItems;
    }

    /**
     * @ensures Returns true if the warehouse is currently full, and false
     *          otherwise.
     */
    public boolean full() {
        return capacity == totalItems;
    }

    /**
     * @ensure Returns the types of items that can be stored in this warehouse.
     */
    public Set<ItemType> getValidTypes() {
        return new HashSet<ItemType>(inventory.keySet());
    }

    /**
     * @ensure Returns true if and only if this warehouse can store items of the
     *         given type.
     */
    public boolean canStore(ItemType type) {
        return inventory.containsKey(type);
    }

    /**
     * @ensure Returns a mapping from the types of items that can be stored in
     *         this particular warehouse, to the number of each such item that
     *         is currently stored. (Changing the returned map won't change the
     *         current state of the warehouse in any way. The state of the
     *         warehouse can only be modified using the other methods available
     *         in this class.)
     */
    public Map<ItemType, Integer> getInventory() {
        return new HashMap<ItemType, Integer>(inventory);
    }

    /**
     * @ensure Returns the types of items that are currently being stored in
     *         this warehouse.
     */
    public Set<ItemType> availableItemTypes() {
        Set<ItemType> available = new HashSet<>();
        for (ItemType type : inventory.keySet()) {
            if (inventory.get(type) > 0) {
                available.add(type);
            }
        }
        return available;
    }

    /**
     * @ensure Returns true if and only if the warehouse is able to add items of
     *         the given type, and it is not currently full.
     */
    public boolean canAddItem(ItemType type) {
        return (type != null && inventory.containsKey(type)
                && capacity != totalItems);
    }

    /**
     * @require The warehouse is able to add items of the given type, and it is
     *          not currently full.
     * @ensure Adds a single item of the given type to the warehouse.
     */
    public void addItem(ItemType type) {
        if (type == null || !inventory.containsKey(type)) {
            throw new IllegalArgumentException(
                    "An item of this type cannot to added to the warehouse.");
        }
        if (capacity == totalItems) {
            throw new IllegalArgumentException(
                    "Cannot add another item: the warehouse is currently full.");
        }
        inventory.put(type, inventory.get(type) + 1);
        totalItems = totalItems + 1;
    }

    /**
     * @ensure Returns true if and only if the warehouse currently contains at
     *         least one item of this type.
     */
    public boolean canRemoveItem(ItemType type) {
        if (type == null || !inventory.containsKey(type)) {
            return false;
        }
        return (inventory.get(type) > 0);
    }

    /**
     * @require The warehouse currently contains at least one item of this type.
     * @ensure Removes a single item of the given type from the warehouse.
     */
    public void removeItem(ItemType type) {
        if (type == null || !inventory.containsKey(type)
                || inventory.get(type) == 0) {
            throw new IllegalArgumentException(
                    "The warehouse does not currently "
                            + "contain an item of this type.");
        }
        inventory.put(type, inventory.get(type) - 1);
        totalItems = totalItems - 1;
    }

    @Override
    public String toString() {
        return name + ": (" + totalItems + "/" + capacity + ") : "
                + inventory.toString();
    }

}
