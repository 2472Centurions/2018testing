import cv2
import numpy as np
from imutils.video import WebcamVideoStream
from networktables import NetworkTable
try:
	cap=WebcamVideoStream(src=0).start()
except:
	print('a')
cleanExit=0
NetworkTable.initialize(server='10.24.72.2')
table=NetworkTable.getTable('testTable')
cleanExit=0
while True:
	image=cap.read()
	blur=cv2.GaussianBlur(image,(5,5),5)
	hsv=cv2.cvtColor(blur,cv2.COLOR_BGR2HSV)
	lower_green=np.array([25,80,200])
	upper_green=np.array([35,255,255])

	mask=cv2.inRange(hsv,lower_green,upper_green)
	a,contours,b=cv2.findContours(mask,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
	if len(contours)>0:
		biggestContour=max(contours,key=cv2.contourArea)
		m=cv2.moments(biggestContour)
		if m['m00']!=0:
			largestPoint=((m['m10']/m['m00']),(m['m01']/m['m00']))
			print(largestPoint)
			table.putNumber('shootCx',largestPoint[0])
			table.putNumber('shootCy',largestPoint[1])

	else:
		table.putnumber('shootCx',-1.0)
		table.putnumber('shootCy',-1.0)	
	key=cv2.waitKey(1)
	if(key==27):
		break
	if(cleanExit==1):
        	break
cv2.destroyAllWindows()
cap.release()
