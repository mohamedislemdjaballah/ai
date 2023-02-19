import sys



class node():
    def __init__(self,state,parent,action):
        self.state =state
        self.parent = parent
        self.action = action





class stackFrontier():    
    #define an instant from the frontier \
    def __init__(self):
        self.frontier = []
    
    
    #add a node to the forntier 
    
    def add(self,add):
        self.frontier.append(node)
        
        
        
    #return the state of the node in the frontier 
    def containState(self,state):
        return any(node.state == state for node in self.frontier)
    
    
    #return true if the frontier is empty
    def empty(self):
        
        return len(self.frontier) == 0
    
    
    def remove(self):
        if self.empty():
            #if the frontier is empty then raise this exeption
            raise Exception("Frontier is Empty")
        else:
            #get the last node in the frontier and remove it
            node = self.frontier[-1]
            self.frontier = self.frontier[:-1]
            return node


#inheritance in pyhon 
# Queue class iherite all attributes and function from the stackFrontier     


class QueueFrontier(stackFrontier):
    def remove(self):
        if self.empty():
            raise Exception("Frontier is Empty")
        else:
            # first in first out // remove the first entuty fron the frontier
            node = self.frontier[0]
            self.frontier = self.frontier[1:]
            return node
        
class maze():
    def __init__(self,filename):
        #read file and set height and width of the maze
        with open(filename) as f:
            contents = f.read()
        
        #validate start and goal
        if contents.count("A") != 1:
            raise Exception("You must have only one start point \n")
        if contents.count("B"):
            raise Exception("You must have only one Goal")
        
        #determins height and width of the maze
        contents = contents.splitlines()
        self.height = len(contents)
        self.width = max(len(line) for lines in contents)
        
        
        #keep track of walls 
        self.wals = []
        for i in range(self.height):
            row = []
            for j in range(self.width):
                try:
                    if contents[i][j] == "A":
                        self.start = (i,j)
                        row.append(False)
                    elif contents[i][j] == "B":
                        self.goal = (i,j)
                        row.append(False)
                    elif contents[i][j] == " ":
                        row.append(False)
                    else:
                        row.append(True)
                except IndexError:
                    row.append(False)
            self.wals.append(row)
        
        self.solution = None
    
    
    def print(self):
        solution = self.solution[1] if self.solution is not None else None
        print()
        for i,row in enumerate(self.wals):
            for j,col in enumerate(row):
                if col :
                    print("||",end="")
                elif (i,j) == self.start: 
                    print("A",end="")
                elif (i,j) == self.goal:
                    print("B",end="")
                elif solution is not None and (i,j) in solution:
                    print("#",end="")
                else:
                    print("",end="")
            print()
        print()
    def neighbours(self,state):
        row,col = state
        #all possible actions 
        condidates = [
            ("up",row - 1 , col ),
            ("left",row,col - 1),
            ("down",row+1,col),
            ("right",row,col + 1)
        ]