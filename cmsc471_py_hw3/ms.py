""" CMSC471 01 hw3 spring 2016
    Sung Ho Kim, ks7
"""   

""" A subclass of a constraint problem to find a magic square for a
    nxn grid and sum n*(n*n+1)/2. """
from constraint import *

class MS(Problem):

    def __init__(self, n=3, solver=None):

        """N is the size of the magic square, solver is the CSP solver
           that will be used to sove the problem """
        # call the base class init method
        super(MS, self).__init__(solver=solver)

        """ MS instance variables """
        if n <= 2:
            raise ValueError("Size of the magic square (n) must be greater than 2.")
        sizeN = n
        # N*(N**2+1)/2 for n>2
        magicSum = sizeN * (sizeN ** 2 + 1) / 2
        
        """ CSP variables with their domains """
        self.addVariables(range(sizeN ** 2), range(1, sizeN ** 2 + 1))
        
        """ CSP constraints """
        self.addConstraint(AllDifferentConstraint(), range(0, sizeN ** 2))
        self.addConstraint(ExactSumConstraint(magicSum), [(sizeN - sizeN) + i * (sizeN + 1) for i in range(sizeN)])
        self.addConstraint(ExactSumConstraint(magicSum), [(sizeN - 1) + i * (sizeN - 1) for i in range(sizeN)])

        for row in range(sizeN):
            self.addConstraint(ExactSumConstraint(magicSum), [row * sizeN + i for i in range(sizeN)])
        
        for col in range(sizeN):
            self.addConstraint(ExactSumConstraint(magicSum), [col + sizeN * i for i in range(sizeN)])
            