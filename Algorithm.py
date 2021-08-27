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
    """X1 = input("Please enter a X1:\n")
    Y1 = input("Please enter a Y1:\n")
    Orientation1 = input("Please enter a Orientation1(N,S,E,W):\n")
    X2 = input("Please enter a X2:\n")
    Y2 = input("Please enter a Y2:\n")
    Orientation2 = input("Please enter a Orientation2(N,S,E,W):\n")
    X3 = input("Please enter a X3:\n")
    Y3 = input("Please enter a Y3:\n")
    Orientation3 = input("Please enter a Orientation3(N,S,E,W):\n")
    X4 = input("Please enter a X4:\n")
    Y4 = input("Please enter a Y4:\n")
    Orientation4 = input("Please enter a Orientation4(N,S,E,W):\n")
    X5 = input("Please enter a X5:\n")
    Y5 = input("Please enter a Y5:\n")
    Orientation5 = input("Please enter a Orientation5(N,S,E,W):\n")"""  # GUI input component may require error handling
    a = Obstacle(1,1,'W')
    b = Obstacle(8,5,'N')
    c = Obstacle(7,3,'E')
    d = Obstacle(3,3,'W')
    all = []
    all.append(a)
    all.append(b)
    unvisited = {}
    for obj in range(len(all)):
        unvisited[obj] = all[obj].positions()
    U = {'x':1.5,'y':1.5,'orientation':(math.pi)/2}        #Initial Point
    queue = []                                         #Queue for path
    ShortestPathCost = 0
    
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
        ShortestPathCost += traveldist

    print(queue)
    print(ShortestPathCost)

    d = []  #make neighbours variable in case got 8 obstacles
    d.append({'x':1.5,'y':1.5,'neighbours':[0,1,2,3],'path':[],'cost':0}) #exhaustive search neighbour may need change
    while len(d) != 0:
        temp = d.pop(0)
        if len(temp['neighbours']) == 0:
            d.insert(0,temp)
            for items in d:
                if len(items['path']) == len(queue) and items['cost'] < ShortestPathCost: 
                    ShortestPathCost = items['cost']
                    queue = items['path']
            break
        for i in range(len(temp['neighbours'])):
            new_cost = temp['cost'] + math.sqrt(math.pow(temp['x']-unvisited[temp['neighbours'][i]]['x'],2) + math.pow(temp['y']-unvisited[temp['neighbours'][i]]['y'],2))
            new_neighbours = temp['neighbours'][:i] + temp['neighbours'][i+1:]
            new_path = temp['path'] +[temp['neighbours'][i]]
            
            d.append({'x':unvisited[temp['neighbours'][i]]['x'], 'y':unvisited[temp['neighbours'][i]]['y'],'neighbours':new_neighbours,'path':new_path,'cost':new_cost})
    
    print(queue)
    print(ShortestPathCost)

    



Hamiltonian_path()