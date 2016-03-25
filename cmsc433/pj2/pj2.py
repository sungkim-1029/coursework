#!/usr/bin/python

import os, sys, re
from math import log

# Reads a file name from the arguments (@ARGV) in command line.
if (len(sys.argv) != 2):
    print "Usage: %s FILENAME\n" % (os.path.basename(sys.argv[0]));
    sys.exit(2)

fileName = sys.argv[1]

# Reads a file and makes a list of lines
def openFile(fileName):
    contents = []
    
    # Read each line in the file and make a list of lines
    try:
        fin = open(fileName, 'r')
    except:
        (eType, detail) = sys.exc_info()[:2]
        print "\n*** %s: %s: %s ***" % (fileName, eType, detail)

    for line in fin:
        contents.append(line)
    fin.close()
    return contents

# Reads configuration (key/value) in a file and return it as a hash
def getConfig(fileName):
    result = {}
    commands = []
    contents = openFile(fileName)
    commentRegx = r"\A\s*\#|^\s*$"

    for line in contents:
        line = line.rstrip()
        if (len(re.findall(commentRegx, line, re.DOTALL)) != 0):
            continue
        commands = line.split(':')
        result[commands[0]] = commands[1]
    
    return result

# Gets a list of documents in a directory
def getDocuments(path):
    result = []
    
    # Now change the directory
    os.chdir( path )
    
    for fileName in os.listdir(path):
        if not fileName.startswith('.'):
            result.append(fileName)
    return result

def writeFile(fileName, contents):
    fout = open(fileName,'w')
    for item in contents:
        fout.write(item)
    fout.close()

# Adds key/value pairs into a hash
pairs = getConfig(fileName)

# Creates a list of file names in the directory
documents = getDocuments(pairs["filedir"])

docs = {}
termToDoc = {}
# Reads a file, process its contents, and make a hash: doc -> term -> TF
for fileName in documents:
    contents = openFile(fileName)
    
    terms = {}
    count = {}
    size = 0
    for line in contents:
        line = line.rstrip()
        words = []
        commentRegx = r'[^\s|\.|\!|\?|\-|,|"]+'
        words = re.findall(commentRegx, line, re.DOTALL)

        for term in words:
            # Makes a term case insensitive
            term = term.lower()
            # Counts a number of time a term appears in the document
            if not term in count:
                count[term] = 1
            else: 
                count[term] += 1
                
            # Counts a total number of terms in the document
            size += 1
    
    # Calculates TF and creates a hash for term -> TF & term -> docs
    for term in count.keys():
        # Calculates TF for each term and creates a hash: term -> TF
        terms[term] = count[term] / float(size);
        # Creates a hash: term -> documents with a term
        docsWithTerm = []
        if not term in termToDoc:
            docsWithTerm.append(fileName)
            termToDoc[term] = docsWithTerm
        else:
            termToDoc[term].append(fileName)
            
    # Creates a hash: document -> term -> TF
    docs[fileName] = terms;

# Makes a format to print out for TF
tfs = []
dKeys = docs.keys()
dKeys.sort()
for fileName in dKeys:
    keys = docs[fileName].keys()
    keys.sort()
    for term in keys:
        TF = docs[fileName][term]
        printFormat = "%s,%s,%.10f\n" % (fileName, term, TF) 
        tfs.append(printFormat)

# Total number of documents
numDocs = len(docs.keys());

# Calculates IDF
termToIDF = {}
ttdKeys = termToDoc.keys()
ttdKeys.sort()
for term in ttdKeys:
    numDocsWithTerm = len(termToDoc[term])
    termToIDF[term] = log(numDocs / float(numDocsWithTerm))

# Makes a format to print out for IDF
idfs = []
ttiKeys = termToIDF.keys()
ttiKeys.sort()
for term in ttiKeys:
    IDF = termToIDF[term]
    printFormat = "%s,%.10f\n" % (term, IDF) 
    idfs.append(printFormat)

# Calculates TF-IDF and makes a format to print out for it
tfidfs = []
tfidKeys = docs.keys()
tfidKeys.sort()
for fileName in tfidKeys:
    tempKeys2 = docs[fileName].keys()
    tempKeys2.sort()
    for term in tempKeys2:
        TFIDF = docs[fileName][term] * termToIDF[term]
        stringFormat = "%s,%s,%.10f\n" % (fileName, term, TFIDF) 
        tfidfs.append(stringFormat)

# Prints TF, IDF and TF-IDF to files
writeFile(pairs["tfoutput"], tfs)
writeFile(pairs["idfoutput"], idfs)
writeFile(pairs["tf-idfoutput"], tfidfs)
    
sys.exit(0)

