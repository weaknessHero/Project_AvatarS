# -*- coding: utf-8 -*-
"""
Created on Wed Jan 18 02:59:24 2023

@author: user
엣지 따서 디자인 분할하기 

이미지 컨투어?


초기 데이터 2D 옷 사진


3D 모델에 맞는 의상을 위한 텍스쳐

목적 3D 모델에 맞는 의상 목표 




"""


import cv2
import numpy as np

img = cv2.imread("../Resource/AIInputIMG/티셔츠/903340_7_220.jpg")

# 바이너리 이미지로 변환
imgray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

ret, imthres  = cv2.threshold(imgray, 127,255,cv2.THRESH_BINARY_INV)
# 가장 바깥쪽 컨투어에 대해 모든 좌표 반환 ---③
contour, hierachy = cv2.findContours(imthres, cv2.RETR_EXTERNAL, \
                                                 cv2.CHAIN_APPROX_NONE)


    
print(contour)    

     
for cnt in contour:    
    # 모든 좌표를 갖는 컨투어 그리기, 초록색  ---⑥
    cv2.drawContours(img, [cnt], -1, (0,255,0), 4)


# 결과 출력 ---⑩
cv2.imshow('result', img)
cv2.waitKey(0)
cv2.destroyAllWindows()