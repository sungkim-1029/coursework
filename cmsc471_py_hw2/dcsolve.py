import argparse
import time

from dc import DC, dictionary
from aima.search import astar_search

usage = """
usage: python dcsolve word1 word2 [--cost scrabble|frequency ]

Find the lowest cost way to transform a three or four letter word into another by changing one letter at a time so that all of the intervening strings are legal English words.  The cost of a replacement letter is its Scrabble tile value (e.g., e:1, k:5, q:10).  Try transforming dog into cat.
"""

def dcsolver(initial, goal, cost='steps'):
    if initial not in dictionary:
        print "%s is not in my dictionary" % initial
        return
    if goal not in dictionary:
        print "%s is not in my dictionary" % goal
        return
    start = time.time()
    solution = astar_search(DC(initial, goal, cost))
    elapsed = time.time() - start
    if solution:
        path = ' '.join([node.state for node in solution.path()])
        path_cost = int(round(solution.path_cost))
    else:
        path = 'NO SOLUTION %s %s' % (initial, goal)
        path_cost = -1
    print "%s %s:\t%s (%.4f)" % (cost, path_cost, path, elapsed)

# if called from the command line, call main()
if __name__ == "__main__":

    p = argparse.ArgumentParser(description='solve dogcat prolems with several cost functions')
    p.add_argument('word1', type=str,  help='initial word')
    p.add_argument('word2', type=str,  help='goal word')
    p.add_argument('-c', '--cost', choices=['steps', 'scrabble', 'frequency'], default='steps', help='cost function')
    args = p.parse_args()
    
    dcsolver(args.word1, args.word2, args.cost)
        

