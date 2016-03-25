""" CMSC471 01 hw3 spring 2016
    Sung Ho Kim, ks7
"""   

""" A subclass of a constraint problem to solve an n-queens problem of
    a given size with a given solver """
from constraint import *

class NQ(Problem):

    def __init__(self, n=8, solver=None):

        """N is the size of the board, solver is the CSP solver
           that will be used to solve the problem """

        # call the base class init method
        super(NQ, self).__init__(solver=solver)

        sizeN = n
        
        """ CSP variables with their domains """
        self.addVariables(range(1, sizeN + 1), range(1, sizeN + 1))
        
        """ CSP constraints """
        self.addConstraint(AllDifferentConstraint(), range(1, sizeN + 1))
        
        for col1 in range(1, sizeN + 1):
            for col2 in range(1, sizeN + 1):
                if col1 < col2:
                    self.addConstraint(lambda row1, row2, col1=col1, col2=col2: abs(row1-row2) != abs(col1-col2) and row1 != row2, (col1, col2))
