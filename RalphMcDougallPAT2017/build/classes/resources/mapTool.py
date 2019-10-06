fileName = input("Map name: ")

com = 1

while com != 0:

    com = int(input("""Choose a tool:
0) Exit
1) New blank canvas
2) Rectangle select
"""))

    if com == 0:
        break

    if com == 1:
        file = open(fileName + ".txt", "w")
        width = int(input("Width: "))
        height = int(input("Height: "))

        lines = [("~" * width + "\n") for i in range(height)]
        file.write( str(width) + " " + str(height) + "\n" )
        file.writelines(lines)

        file.close()

    if com == 2:
        fileIn = open(fileName + ".txt", "r")
        lines = fileIn.readlines()
        temp = lines[0].split()
        lines = lines[1:]
        #print(len(lines))
        width = int(temp[0])
        height = int(temp[1])
        #print(lines)
        fileIn.close()

        x1 = int(input("X1: "))
        y1 = int(input("Y1: "))
        x2 = int(input("X2: "))
        y2 = int(input("Y2: "))
        s = input("Symbol: ")[0]

        for x in range(x1, x2 + 1):
            for y in range(y1, y2 + 1):
                ts = list(lines[y])
                ts[x] = s
                lines[y] = "".join(ts)
        
        file = open(fileName + ".txt", "w")
        
        file.write( str(width) + " " + str(height) + "\n" )
        file.writelines(lines)

        file.close()
        
