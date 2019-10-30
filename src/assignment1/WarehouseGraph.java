package assignment1;

import java.util.*;

public class WarehouseGraph {
    public static HashMap<Warehouse, HashSet<Warehouse>> connectedGraph;
    public static Warehouse depot;
    public static ArrayList<Warehouse> endPoints;
    public static List<Transfer> path;

    public WarehouseGraph(Warehouse depot, Set<Warehouse> warehouses) {
        this.depot = depot;
        warehouses.add(depot);
        endPoints = findEndpoints(warehouses);
        path = null;
        connectedGraph = mapWarehouses(warehouses);
    }


    public Set<Warehouse> getWarehouses() {
        return connectedGraph.keySet();
    }

    public void addTransferList(List<Transfer> transferList) {
        this.path = transferList;
    }

    public static ArrayList<Warehouse> findEndpoints(Set<Warehouse> warehouses) {
        ArrayList<Warehouse> endPoints = new ArrayList<Warehouse>();
        for (Warehouse warehouse : warehouses) {
            if (!warehouse.full()) {
                endPoints.add(warehouse);
            }
        }
        return endPoints;
    }

    public ArrayList<Warehouse> getEndPoints() {
        ArrayList<Warehouse> endPointsCopy = new ArrayList<Warehouse>();
        for (Warehouse warehouse : this.endPoints) {
            endPointsCopy.add(warehouse);
        }
        return endPointsCopy;
    }

    public static HashSet<Warehouse> getCandidateWarehouses() {
        return connectedGraph.get(depot);
    }

    public HashSet<Warehouse> get(Warehouse warehouse) {
        return connectedGraph.get(warehouse);
    }

    public Warehouse getDepot() {
        return depot;
    }

    public ItemType getDepotItem() {
            ItemType type = depot.availableItemTypes().iterator().next();
            return type;
     }

    /**
        Maps the each ware house to a list of adjacent warehouses
        when warehouse n is added to warehouse p's list,  p is added to n's and removed from g
        This runs in O(itemtypes*warehouses)
        */
    public static HashMap<Warehouse, HashSet<Warehouse>> mapWarehouses(Set<Warehouse> warehouses) {
        HashMap<ItemType, ArrayList<Warehouse>> g = new HashMap<ItemType, ArrayList<Warehouse>>();
        //this maps the item types to a list of warehouses that hold this type
        for (Warehouse warehouse:warehouses) {
                    for (ItemType type:warehouse.getValidTypes()) {
                        if (g.containsKey(type)) {
                            g.get(type).add(warehouse);
                        } else {
                            g.put(type, new ArrayList<Warehouse>());
                            g.get(type).add(warehouse);
                        }
                    }
                }
        HashMap<Warehouse, HashSet<Warehouse>> connectedGraph = new HashMap<Warehouse, HashSet<Warehouse>>();
        for (ItemType type :g.keySet()) {
            for (Warehouse warehouse: g.get(type)) {
                if (connectedGraph.containsKey(warehouse)) {
                    connectedGraph.get(warehouse).addAll(g.get(type));
                    connectedGraph.get(warehouse).remove(warehouse);
                } else {
                    //create a new set of warehouses
                    connectedGraph.put(warehouse, new HashSet<Warehouse>((g.get(type))));
                    connectedGraph.get(warehouse).remove(warehouse);
                }
            }
        }
        return connectedGraph;
    }


    public static void printGraph() {
        for (Warehouse warehouse: connectedGraph.keySet()) {
            System.out.println(String.format("Warehouse %s is connected to", warehouse.getName()));
            for (Warehouse w: connectedGraph.get(warehouse)) {
                System.out.print(String.format(" %s, ", w.getName()));
            }
            System.out.println();
        }
        return;
    }
}