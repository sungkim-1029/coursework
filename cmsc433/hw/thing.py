# 5. Create a class and assign a value to an Attribute
# and print it
class Thing():
    def __init__(self, strParam):
        self.beginning = strParam

thing = Thing("We the People")
print(thing.beginning)

