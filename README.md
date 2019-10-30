# Warehouse Shuffle Problem

This was initially an assignment problem giving to me in my advanced algorithms course. Space and time complexity was a constraint. This is a java implementation of the problem. The problem was, given a depot (which is a warehouse only capable of containing 1 item), find a series of transfers between a set of warehouses, which allows the depot to get rid of its item. 

Warehouses are constrained with a capacity as well as a defined list of item types it is able to contain.

# My Implementation
In order to run in reasonable time (preferably O(n)) I thought of depth first search techniques which would only visit each node once. Furthermore, I wanted to use hashsets to make accessing the warehouses and item types much faster. Starting at a warehouse which has a capacity (room for an item), I then visit adjacent neighbours by first picking any one of the neighbours. The neighbour will only be chosen if:

        * The neighbour shares similar item types
        * The neighbour has one of those items to send
         
From here the neighbour procedes to send that item to the initial warehouse freeing up space. This is then done recursively marking each warehouse as visited, as to not visit the same warehouse, or infinitely loop, as well as base case returning when free space is made at a warehouse which shares item types with the depot


