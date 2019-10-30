package assignment1;

import java.util.*;

public class TransferFinder {

    /**
     * @require The depot is non-null and contains exactly one item of a
     *          particular type. The depot may not contain any items of any
     *          other type and has a capacity of one. The set of warehouses is
     *          not null, does not contain null warehouses, and does not include
     *          the depot.
     * 
     * @ensure The state of the depot, and each of the warehouses in the given
     *         set must not NOT be modified in any way (e.g. items should not be
     *         added or removed from the depot or any of these warehouses).
     * 
     *         This method should return either:
     * 
     *         (a) a non-null list of transfers such that (i) each transfer is
     *         non-null and takes place either between two warehouses in the
     *         given set of warehouses, or between the given depot and a
     *         warehouse in the set of warehouses; (ii) given the initial state
     *         of the depot and warehouses, all the transfers can be performed
     *         in the order in which they appear in the list; and (iii) the
     *         depot is empty when all transfers in the list are completed (in
     *         order, starting from the initial state of the depot and
     *         warehouses).
     * 
     *         OR
     * 
     *         (b) the value null if and only if no such list of transfers, as
     *         described in part (a), exists.
     * 
     *         (See the assignment handout for details and examples.)
     */
    public static List<Transfer> findTransfers(Warehouse depot,
                                               Set<Warehouse> warehouses) {

        if (depot == null || warehouses == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (depot.getCapacity() != 1 || depot.availableItemTypes().size() != 1) {
            throw new IllegalArgumentException(
                    "The depot must contain exactly one item of any type and" +
                            "has a capacity of one.");
        }
        if (warehouses.contains(depot)) {
            throw new IllegalArgumentException(
                    "The set of warehouses cannot contain the depot.");
        }
        for (Warehouse warehouse : warehouses) {
            if (warehouse == null){
                throw new IllegalArgumentException(
                        "The set of warehouses cannot contain null warehouses.");
            }
        }

        WarehouseGraph connectedGraph = new WarehouseGraph(depot, warehouses);
        //connectedGraph.printGraph();

        for (Warehouse end : connectedGraph.getEndPoints()) {
            //System.out.println(String.format("Printing paths from %s", end.getName()));
            findAllPaths(end, warehouses, connectedGraph);
            if (connectedGraph.path != null) {
                //System.out.println("Final answer %s");
                //System.out.println(connectedGraph.path);
                break;
            }
        }
        return connectedGraph.path;
    }

    public static void findAllPaths(Warehouse s, Set<Warehouse> warehouses, WarehouseGraph connectedGraph)
    {
        HashMap<Warehouse, Boolean> isVisited = new HashMap<Warehouse, Boolean>();
        for (Warehouse warehouse: warehouses) {
            isVisited.put(warehouse, false);
        }
        List<Transfer> pathList = new ArrayList<Transfer>();
        //need to make a local connected graph and send it to algorithm
        //Call recursive utility
        findAllPathsUtil(s, isVisited, pathList, connectedGraph);
    }

    public static void findAllPathsUtil(Warehouse u, HashMap<Warehouse, Boolean> isVisited , List<Transfer> localPathList,
                                    WarehouseGraph connectedGraph) {
        //ignore depot
        if (u == connectedGraph.getDepot())
            return;

        //if the current node can add the item then weve found a valid transfer path so add the path and return
        //need to make sure the state of the warehouses was not changed
        if (u.canAddItem(connectedGraph.getDepotItem())) {
            Transfer finalTransfer = new Transfer(connectedGraph.getDepot(), u, connectedGraph.getDepotItem());
            localPathList.add(finalTransfer);
            connectedGraph.addTransferList(localPathList);
            //reverse local transfers
            reverse(localPathList);
            return;
        }

        //mark the node as visited
        isVisited.replace(u, true);


        for (Warehouse v : connectedGraph.get(u)) {
            if (!isVisited.get(v)) {
                for (Transfer t : checkEdge(u, v)) {
                    //add transaction to the local list
                    localPathList.add(t);
                    //Make transfer locally
                    t.makeTransfer();
                    //call recursively
                    findAllPathsUtil(v, isVisited, localPathList, connectedGraph);
                    //check if from the return weve found a path to avoid further searching
                    if (connectedGraph.path != null) {
                        return;
                    }
                    //take the transaction off the local list
                    localPathList.remove(t);
                    //remove transfer locally
                    reverse(t);
                }
            }
        }

        //if it gets to this stage the node should have explored all paths
    }

    public static void reverse (List<Transfer> transfers) {
        for (int i = transfers.size() - 1 ; i-- > 0; ) {
            Transfer t = transfers.get(i);
            //System.out.println(String.format("Reversing %s", t));
            Transfer reverseT = new Transfer(t.getDestination(), t.getSource(), t.getType());
            //System.out.println(String.format("New transfer %s", reverseT));
            reverseT.makeTransfer();
        }
    }

    public static void reverse (Transfer t) {
        Transfer reverseT = new Transfer(t.getDestination(), t.getSource(), t.getType());
        reverseT.makeTransfer();
    }

    public static List<Transfer> checkEdge(Warehouse u, Warehouse v) {
        List<Transfer> possiblePaths = new ArrayList<Transfer>();
        for (ItemType type : v.availableItemTypes()) {
            if (u.canAddItem(type)) {
                possiblePaths.add(new Transfer(v, u, type));
            }
        }
        return possiblePaths;
    }
}
