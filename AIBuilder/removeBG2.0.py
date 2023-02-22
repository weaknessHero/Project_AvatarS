# -*- coding: utf-8 -*-
"""
Created on Tue Feb 21 03:40:49 2023

@author: user
"""


import numpy as np
import imutils
import cv2


def RemoveIMG(Contour) :
    #조건이 맞는지 확인하고 맞을경우 True

    return Contour==-1



image = cv2.imread("../Resource/RemoveBGIMG/니트/1778905_1_220.jpg")
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
edged = cv2.Canny(gray, 20, 100)
cv2.imshow("Original", image)

c, h = cv2.findContours(edged.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)


mask = np.ones(image.shape[:2], dtype="uint8") * 255

# loop over the contours

for i in range(len(c)) :
    #if the contour is bad, draw it on the mask
    if RemoveIMG(h[0,i,3]):
        cv2.drawContours(mask, [c[i]], -1, 0, -1)
        
# remove the contours from the image and show the resulting images
image = cv2.bitwise_and(image, image, mask=mask)
cv2.imshow("Mask", mask)
cv2.imshow("After", image)
cv2.waitKey(0)

