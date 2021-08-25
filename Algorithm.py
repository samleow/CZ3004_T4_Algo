import math

class Obstacle:
    def __init__(self,x,y,direction): #initialise the positions of the obstacle
        self.x = x
        self.y = y
        self.direction = direction
    
    def positions(self): #Performs translation to find the position that the car should be for each obstacle
        if self.direction == 'S':
            return ({'x': self.x+0.5,'y':self.y-3,'orientation': (math.pi)/2,'visited': False}) 
        elif self.direction == 'N':     #change to center
            return({'x':self.x+0.5, 'y':self.y+4, 'orientation':-(math.pi)/2, 'visited': False})
        elif self.direction == 'E':
            return ({'x':self.x+4,'y':self.y+0.5,'orientation':0, 'visited': False})
        elif self.direction == 'W':
            return ({'x':self.x-3,'y':self.y+0.5,'orientation':math.pi, 'visited': False})
        else:
            print("Error Occured, direction not valid.")



def Hamiltonian_path(): #use option 2 20x20 grid
    a = Obstacle(1,1,'W')
    b = Obstacle(8,5,'N')
    all = []
    all.append(a)
    all.append(b)
    unvisited = {}
    for obj in range(len(all)):
        unvisited[obj] = all[obj].positions()
    U = {'x':1.5,'y':1.5,'orientation':(math.pi)/2}        #Initial Point
    queue = []                                         #Queue for path
    
    for item in unvisited:
        traveldist = math.sqrt(2*math.pow(20,2))        #This is the slightly more than the maximum distance of an obstacle in a 20x20 grid
        for nodes in unvisited.items():
            Pythagoras = math.sqrt(math.pow(U['x']-nodes[1]['x'],2) + math.pow(U['y']-nodes[1]['y'],2))
            if Pythagoras < traveldist and nodes[1]['visited'] == False:    #may need account for turns based on
                traveldist = Pythagoras
                nearest = nodes[0]
        queue.append(nearest)
        unvisited[nearest]['visited'] = True
        U = unvisited[nearest]
        print(queue)

        



Hamiltonian_path()