package assignment1;

/**
 * An class representing the transfer of one item of a given type from a source
 * warehouse to a destination warehouse.
 * 
 * The source and destination warehouses of a transfer must be different.
 * 
 * DO NOT MODIFY THIS FILE IN ANY WAY.
 */
public class Transfer {

    // the warehouse where the item is transferred from
    private final Warehouse source;
    // the warehouse where the item is transferred to
    private final Warehouse destination;
    // the type of the item that is transferred
    private final ItemType type;

    /*
     * class invariant: source != null && destination != null && type != null &&
     * source != destination
     */

    /**
     * @require source != null && destination != null && type != null && source
     *          != destination
     * @ensure Creates a new transfer of an item of the given type from the
     *         given source to the given destination warehouse.
     */
    public Transfer(Warehouse source, Warehouse destination, ItemType type) {
        if (source == null || destination == null || type == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        if (source == destination) {
            throw new IllegalArgumentException(
                    "The source and destination must be different warehouses.");
        }
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

    /**
     * @ensure Returns the source warehouse.
     */
    public Warehouse getSource() {
        return source;
    }

    /**
     * @ensure Returns the destination warehouse.
     */
    public Warehouse getDestination() {
        return destination;
    }

    /**
     * @ensure Returns the type of the item being transferred from the source to
     *         destination.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * @ensure Returns true if and only if this transfer could currently be made
     *         (given the current state of the source and destination
     *         warehouses).
     */
    public boolean tranferCurrentlyPossible() {
        return (source.canRemoveItem(type) && destination.canAddItem(type));
    }

    /**
     * @requires That this transfer can currently occur (given the current state
     *           of the source and destination warehouses).
     * @ensures Transfers one item of the type of this transfer from the source
     *          to the destination warehouse.
     */
    public void makeTransfer() {
        if (!source.canRemoveItem(type) || !destination.canAddItem(type)) {
            throw new RuntimeException(
                    "Cannot currently perform this transfer.");
        }
        source.removeItem(type);
        destination.addItem(type);
    }

    @Override
    public String toString() {
        return "(" + source.getName() + ", " + destination.getName() + ", "
                + type + ")";
    }

}
