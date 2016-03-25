/*
 ============================================================================
 Name        : dj.c
 Author      : Sung Ho Kim
 Version     : Dec. 9. 2015
 Course      : CMSC481
 Description : Project 2 - Routing (Dijsktra's algorithm)
 ============================================================================
 */
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define INFIN 999
#define INPUT_FILE_NAME "input.txt"
#define OUTPUT_FILE_NAME "output.txt"
#define FILE_BUF 128
#define NODE_NAME 0
#define CONNECTING_NODE 1
#define EDGE_WEIGHT 2
#define NEXT_HOP 2

int dj(int **cost, int numNodes, int source, int destination, int *path);
int pickNextHop(int *path, int length);
void reverse_array(int *path, int n);
int split(char *string, char *delim, char *tokens[]);
int getNumNodesFromFile();
void initCost(int **cost, int numNodes, int *source, int *targe);
void printToFile(int ** cost, int source, int destination, int length);

// Calculate Dijsktra's algorithm
int dj(int **cost, int numNodes, int source, int destination, int *path) {
	int * dist;
	int * prev;
	int * s;
	int v, u, min, w, d, j;

	dist = (int *) malloc(sizeof(int) * (numNodes + 1));
	prev = (int *) malloc(sizeof(int) * (numNodes + 1));
	s = (int *) malloc(sizeof(int) * (numNodes + 1));

	// Initialization
	for (v = 1; v <= numNodes; v++) {
		dist[v] = INFIN;
		prev[v] = -1;
	}

	w = source;
	s[w] = 1;
	dist[w] = 0;

	// Until the destination node is in s
	while (s[destination] == 0) {
		min = INFIN;
		u = 0;
		// Compare with all nodes to find w
		for (v = 1; v <= numNodes; v++) {

			// update D(v)
			d = dist[w] + cost[w][v];

			// Find min {D(v), D(w) + C(w,v)} when v is not in S
			if (d < dist[v] && s[v] == 0) {
				dist[v] = d;
				prev[v] = w;
			}
			// when a new minimum (w) is found for v is not in S
			if (min > dist[v] && s[v] == 0) {
				min = dist[v];
				u = v;
			}
		}
		// Add w to s
		w = u;
		s[w] = 1;
	}

	// Make a traversal
	w = destination;
	j = 0;
	while (w != -1) {
		path[j++] = w;
		w = prev[w];
	}
	reverse_array(path, numNodes + 1);

	return dist[destination];
}

int pickNextHop(int *path, int length) {
	int i;
	int cnt = 0;
	int nextHop = 0;
	for (i = 0; i < length; i++) {
		if (path[i] != 0) {
			// When the source are the same with the destination
			if (i == (length - 1)) {
				nextHop = path[i];
				break;
			}

			cnt++;
			// Next hop is the second one from the source
			if (cnt == NEXT_HOP) {
				nextHop =  path[i];
				break;
			}
		}
	}
	return nextHop;
}

void reverse_array(int *path, int n)
{
   int *temp, i, j;

   temp = (int*)malloc(sizeof(int) * n);

   if( temp == NULL )
      exit(EXIT_FAILURE);

   for ( i = n - 1, j = 0 ; i >= 0 ; i--, j++ )
      *(temp+j) = *(path + i);

   for ( i = 0 ; i < n ; i++ )
      *(path + i) = *(temp + i);

   free(temp);
}

int split(char *string, char *delim, char *tokens[])
{
    int count = 0;
    char *token;
    char *stringp;

    stringp = string;
    while (stringp != NULL) {
        token = strsep(&stringp, delim);
        tokens[count] = token;
        count++;
    }
    return count;
}

int getNumNodesFromFile() {
	FILE *fp;
	char str[FILE_BUF];
	char *tokens[3];

	int numNodes = 0;
	char * fname = INPUT_FILE_NAME;

	fp = fopen(fname, "r");
	if (fp == NULL) {
		printf("Error opening %s\n", fname);
		exit(-1);
	}

	while (!feof(fp)) {
		if (fgets(str, FILE_BUF, fp) != NULL) {
			int count = split(str, "$", tokens);
			if (count == 3) {
				if (numNodes < atoi(tokens[CONNECTING_NODE]))
					numNodes = atoi(tokens[CONNECTING_NODE]);
			}
		}
	}
	fclose(fp);

	return numNodes;
}

void initCost(int **cost, int numNodes, int *source, int*target) {
	FILE *fp;
	char str[FILE_BUF];
	char *tokens[3];
	int i, j;

	char *fname = INPUT_FILE_NAME;

	// Initiate cost
	for (i = 1; i <= numNodes; i++)
		for (j = 1; j <= numNodes; j++)
			cost[i][j] = INFIN;

	fp = fopen(fname, "r");
	if (fp == NULL) {
		printf("Error opening %s\n", fname);
		exit(-1);
	}

	while (!feof(fp)) {
		if (fgets(str, FILE_BUF, fp) != NULL) {

			// Remove '\n'
			int max_len = strlen(str);
			if (max_len > 0) {
				str[max_len - 1] = '\0';
			}

			int count = split(str, "$", tokens);

			if (count == 3) {
				cost[atoi(tokens[NODE_NAME])][atoi(tokens[CONNECTING_NODE])] = cost[atoi(tokens[CONNECTING_NODE])][atoi(tokens[NODE_NAME])] = atoi(tokens[EDGE_WEIGHT]);
			} else {
				if (strcasecmp (tokens[NODE_NAME], "Source") == 0) {
					*source = atoi(tokens[CONNECTING_NODE]);
				} else {
					*target = atoi(tokens[CONNECTING_NODE]);
				}
			}
		}
	}
	fclose(fp);
}

void printToFile(int **cost, int source, int destination, int length) {
	int i, j, distance, nextHop;
	FILE *fp;
	char *fname = OUTPUT_FILE_NAME;
	int *path;

	// Try to open the file for writing, check if successful
	fp = fopen(fname, "w");
	if (fp == NULL) {
		printf("Error opening %s\n", fname);
		exit(-1);
	}

	path = (int *) calloc(length, sizeof(int));
	// Call a method to calculate Dijsktra's algorithm
	distance = dj(cost, (length - 1), source, destination, path);
	fprintf(fp, "------------------------------------------------------------------------------\n");
	fprintf(fp, "Source: %d -> Destination: %d\n", source, destination);

	// Print a traversal
	for (i = 0; i < length; i++) {
		if (path[i] != 0) {
			fprintf(fp, "%d", path[i]);
			if (i != (length - 1))
				fprintf(fp, "%s", " -> ");
			else
				fprintf(fp, "%s", "\n");
		}
	}
	fprintf(fp, "Total distance: %d\n", distance);
	fprintf(fp, "------------------------------------------------------------------------------\n");
	free(path);

	// Print routing tables for all nodes
	for (i = 1; i < length; i++) {
		fprintf(fp, "\nRouting table of node %d\n", i);
		fprintf(fp, "Dest\t Next hop\t Distance\t\n");
		for (j = 1; j < length; j++) {
			path = (int *) calloc(length, sizeof(int));
			distance = dj(cost, (length - 1), i, j, path);
			nextHop = pickNextHop(path, length);
			fprintf(fp, "%d\t\t %d\t\t %d\n", j, nextHop, distance);
			free(path);
		}
	}
}

int main()
{
	int i;
	int source, destination;
	int numNodes = 0;

	// Find the number of nodes from the input file
	numNodes = getNumNodesFromFile();

	// Create a score matrix
	int ** cost = (int**) malloc(sizeof(int*) * (numNodes + 1));
	for (i = 0; i <= numNodes; i++)
		cost[i] = (int*) malloc(sizeof(int) * (numNodes + 1));

	// Initialization
	initCost(cost, numNodes, &source, &destination);

	// Print out the result to the output file
	printToFile(cost, source, destination, numNodes + 1);

	// Clean memory
	for (i = 0; i < numNodes + 1; i++)
		free(cost[i]);
	free(cost);
}
