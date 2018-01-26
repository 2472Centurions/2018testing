import cv2
import numpy as np
from networktables import NetworkTable
NetworkTable.initialize(server='10.20.17.52')
table=NetworkTable.getTable('testTable')
cleanExit=0
i =0
while True:
	i=i+1
	table.putNumber('shootCx', i)
	print(i)
