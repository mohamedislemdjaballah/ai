from tkinter import * 
from PIL import Image, ImageTk
root = Tk()

def change():
    mybtn.config(image=sea,borderwidth=0,)
wall = PhotoImage(file='wall.png')
sea = PhotoImage(file='sea.png')
mybtn = Button(text='clk me',command=change)
mybtn.grid(row=0,column=0)
root.mainloop(
)