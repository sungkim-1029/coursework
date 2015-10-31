#6. stringSearch.PY
import re
source = "We the People of the United States, in Order to form a more perfect Union, establish Justice, insure domestic Tranquility, provide for the common defence, promote the general Welfare, and secure the Blessings of Liberty to ourselves and our Posterity, do ordain and establish this Constitution for the United States of America."

pattern1 = re.findall(r"\b\w*ty\b", source)
pattern2 = re.findall(r"\bp\w+\b", source, re.I)
pattern3 = re.findall(r"\bthe\b", source)

print(pattern1)
print(pattern2)
print(pattern3)
