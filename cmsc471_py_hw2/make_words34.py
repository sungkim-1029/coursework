""" Produces words34.txt, a tab-spearated list of three and four
letters words and their inverse document frequency scores.  The words
must be found both in lists of allowable scrabble words and amon the
200K most frequent word-like tokens found in more than eight million
newswire document """

import gzip 

idffile = "idf.txt.gz"
freq_cutoff = 17.0

words3file = "three-letter-words.txt"
words4file = "four-letter-words.txt"
w34file = "words34.txt"

scrabble_words = set()

alpha = "abcdefghijklmnopqrstuvwxyz"

for line in open(words3file):
    scrabble_words.add(line.strip().lower())
for line in open(words4file):
    scrabble_words.add(line.strip().lower())    

with open(w34file, 'w') as out:
    for line in gzip.open(idffile):
        word, freq = line.strip().split('\t')
        freq = float(freq)
        if word in scrabble_words and freq < freq_cutoff:
            out.write(line)

