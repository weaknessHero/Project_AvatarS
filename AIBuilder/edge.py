# -*- coding: utf-8 -*-
"""
Created on Wed Jan 18 02:59:24 2023

@author: user
엣지 따서 디자인 분할하기 

이미지 컨투어?



"""


import cv2

src = cv2.imread("./Resource/1.jpg", cv2.IMREAD_COLOR)
gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)

sobel = cv2.Sobel(gray, cv2.CV_8U, 1, 0, 3)
laplacian = cv2.Laplacian(gray, cv2.CV_8U, ksize=3)
canny = cv2.Canny(src, 100, 255)

cv2.imshow("sobel", sobel)
cv2.imshow("laplacian", laplacian)
cv2.imshow("canny", canny)
cv2.waitKey()
cv2.destroyAllWindows()

