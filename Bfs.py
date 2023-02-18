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
    def empty(self):
        #return true if the frontier is empty
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