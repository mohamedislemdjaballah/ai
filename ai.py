import statistics
from tkinter import *
import random
root = Tk()

def randomPlace(X,Y):
    randomBot.destroy()
    dimensions.destroy()
    startGame.pack(side='bottom')
    label.config(text='Click at Goal target')
    Matrix[X][Y].config(text='Bot')
    howfar(X,Y)
    
def howfar(x,y):
    far =0
    goal = x + y
    print(f"Goal ::{x},{y}")
    for row in range(3):
        for col in range(3):            
            far = goal - (row + col)
            if far > 0:
                Distance[row][col] = far
            else:
                Distance[row][col] = far*-1
    
    print(Distance)
def reachedGoal():
    pass

def makePathWithObstacls():
    makepathbtn.destroy()
    randomBot.pack(side='top')
    x = int(dimensions.get())
    # initialise path Matrix
    MatrixP = [[''
    for i in range(x)] for y in range(x)]
    
    for row in range(x):
        for col in range(x):
            
            # Path Matrix
            MatrixP[row][col] = random.choice(obstacles)
            print(MatrixP)
            
            # Gui Matrix
            Matrix[row][col].config(text=MatrixP[row][col]
                                    )
            description = Matrix[row][col]['text']
            if( description == 'fire'):
                Matrix[row][col].config(text='',
                                        bg='orange',
                                        borderwidth=0)
            elif( description == 'wall'):
                Matrix[row][col].config(text='',
                                    bg='grey',
                                    borderwidth=0)
            elif( description == 'sand'):
                Matrix[row][col].config(text='',
                                    bg='yellow',
                                    borderwidth=0)
            elif( description == 'river'):
                Matrix[row][col].config(text='',
                                    bg='blue',
                                    borderwidth=0)
            elif( description == 'road'):
                Matrix[row][col].config(text='',
                                    bg='white',
                                    borderwidth=0)
             
                    
                

            Matrix[row][col].grid(row=row,column=col)
            
            
def nextMove():
    pass

root.title('Robot')


entries = Frame(root)
entries.pack(side='top')

board = Frame(root)
board.pack(side='bottom')

# variables
obstacles = ('fire','wall','river','sand','road')

label = Label(entries,
              text='Enter Dimension',
              font=('Arial,25,solid'))
label.grid(row=0,column=0)
dimensions = Spinbox(entries,
                  from_=3,
                  to=9,
                  width=2,
                  font=("Arial,15,bold")
                  )

dimensions.grid(row=0,column=1)


x = int(dimensions.get())
cordinateX = IntVar
cordinateY = IntVar
dist = IntVar
Matrix = [[Button(board,text='',
                    font=('Arial,25,solid'),
                    bd=None,
                    width=5,
                    height=3
                    )
for i in range(x)] for y in range(x)]
Distance = [[ 0
             for i in range(x)] for y in range(x)]





makepathbtn = Button(board,text='make path',command=makePathWithObstacls)
makepathbtn.pack(side='bottom')



randomBot = Button(root,text="%",
                   font=("Arial,15,bold"),
                   command=lambda x=random.randrange(2),y=random.randrange(2):randomPlace(x,y)
                       )


startGame = Button(root,
                   text="Start Game",
                   font=("Arial,15,bold"))
root.mainloop()