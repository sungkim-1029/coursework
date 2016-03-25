""" CMSC471 01 hw2 spring 2016
    Sung Ho Kim, ks7
"""    

import aima.search as a  # AIMA module for search problems
import string

dict_file = "words34.txt"

dictionary = {}

for line in open(dict_file):
    word, n = line.strip().split('\t')
    dictionary[word] = float(n)

class DC(a.Problem):

    def __init__(self, initial='dog', goal='cat', cost='steps'):
        self.initial = initial
        self.goal = goal
        self.cost = cost

    def actions(self, state):
        """ generates legal actions for state """
        for i in range(len(state)):
            temp_text = state
            if state[i] == self.goal[i]:
                continue
            else:
                for ch in string.ascii_lowercase:
                    if ch != state[i]:
                        temp_text = temp_text[:i] + ch + temp_text[i + 1:]
                        if temp_text in dictionary:
                            yield (temp_text, ch)
                    else:
                        continue

    def result(self, state, action):
        """ Returns the successor of state after doing action """
        return action[0]

    def goal_test(self, state):
        """ Returns true if state is a goal state """
        return state == self.goal

    def path_cost(self, c, state1, action, state2):
        """ Cost of path from start node to state1 assuming cost c to
        get to state1 and doing action to get to state2 """
        temp_cost = c
        if self.cost == 'scrabble':
            temp_cost += self.getScrabbleCost(action[1])
        elif self.cost == 'frequency':
            temp_cost = temp_cost + (1 + dictionary[state2])
        else: 
            """ self.cost == steps """
            temp_cost += 1
            
        return temp_cost

    def __repr__(self):
        """ returns a string to represent a dc problem """
        return "DC({},{},{})".format(self.initial, self.goal, self.steps)

    def h(self, node):
        temp_h = 0
        if self.cost == 'scrabble':
            """ Heuristic: the sum of the costs for the replacement letters
            that ultimately have to be added """
            sumCosts = 0
            for (x, y) in zip(node.state, self.goal):
                if x!= y:
                    sumCosts += self.getScrabbleCost(y)
                    
            temp_h = sumCosts
 
        elif self.cost == 'frequency':
            """ Heuristic: minimum of next two values - the number of letters need to be changed and 
            the difference between the frequency of the goal word 
            and the minimum frequency of any word in the current solution
            """
            
            """ The number of letters need to be changed """
            h1 = [x != y for (x, y) in zip(node.state, self.goal)].count(True)
            
            """ The difference between the frequency of the goal word 
            and the minimum frequency of any word in the current solution.
            (Default: goal word)
            """
            h2 = dictionary[self.goal]
            l = [dictionary[n[0]] for n in node.solution()]
            if len(l) != 0:
                h2 = abs(h2 - min(l))
                
            temp_h = min(h1, h2)
            
        else:
            """ self.cost == steps """
            """ Heuristic: the number of letters need to be changed """
            temp_h = [x != y for (x, y) in zip(node.state, self.goal)].count(True)
        return temp_h

# add more functions here as needed
    """ Gets a cost of a designated character """
    def getScrabbleCost(self, ch):
        costRule = {'a': 1, 'e': 1, 'i': 1, 'o': 1, 'u': 1, 'l': 1, 'n': 1, 's': 1, 't': 1, 'r': 1, \
                    'd': 2, 'g': 2, \
                    'b': 3, 'c': 3, 'm': 3, 'p': 3, \
                    'f': 4, 'h': 4, 'v': 4, 'w': 4, 'y': 4, \
                    'k': 5, \
                    'j': 6, 'x': 6, \
                    'q': 10, 'z': 10}
        return costRule[ch]
