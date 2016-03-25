============================================================================
 Name        : dj.c
 Author      : Sung Ho Kim
 Version     : Dec. 9. 2015
 Course      : CMSC481
 Description : Project 2 - Routing
 ============================================================================
1. Introduction
This program demonstrates Dijkstra's algorithm. It finds out the shortest path
from any node to any node defining in the input file.

2. Details
It needs to load the nodes definition from the file, input.txt. 
First of all, it determines the total number of nodes from the definition to create a dynamic array
by selecting a maximum node. And then, it initializes the cost matrix having edge weights. Finally, 
it calculates the minimum distance from the source to the destination using Dijkstra's algorithm.
After it is done, it shows routing tables for all nodes.

3. How to use
# Compile
gcc -o dj dj.c

# Run (input.txt must exist in the same folder. After it is done, output.txt will be created.)
./dj

# See the result
cat output.txt

 