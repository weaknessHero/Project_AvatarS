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




"""
blur: 배경과 전경 사이 구분선의 "부드러움"에 영향을 줍니다.
min_area: 전경의 윤곽선이 차지할 수 있는 최소 영역입니다. 0과 1 사이의 값으로 취합니다.
max_area: 전경의 윤곽선이 차지할 수 있는 최대 영역. 0과 1 사이의 값으로 취합니다.
dilate_iter: 확장 반복 횟수가 마스크에서 발생합니다.
erode_iter: 침식 반복 횟수가 마스크에서 발생합니다.
mask_color: 배경이 제거된 후의 색상.
"""

# 매개변수 
blur = 21 

min_area = 0.0005 
max_area = 0.95 
dilate_iter = 10 
erode_iter = 10 
mask_color = (0.0,0.0,0.0)





#이미지 읽기
inputIMG = cv2.imread("../Resource/RemoveBGIMG/니트/1778905_1_220.jpg")

# 흑백화를 통해 바이너리 이미지로 변환
imgray = cv2.cvtColor(inputIMG, cv2.COLOR_BGR2GRAY)

canny_low = 50
canny_high = 200 
#흑백 이미지를 명확화 Hyteresis Thresholding을 통한 결과이다. 
#canny_low: 낮은 부분은 엣지가 아니다라고 판단.
#canny_high: 높은 부분은 엣지임을 판단. 그 사이의 값은 연결구조를 통해 판단한다.
edges = cv2.Canny(imgray, canny_low, canny_high)

cv2.imshow("canny result", edges)

"""
#커널을 통한 선 외각선의 사이즈 증가 또는 감소 
edges = cv2.dilate(edges, None) 
edges = cv2.erode(edges, None)
"""      

#컨투어의 결과와 크기를 반환
#for c in findContours [( c = ([[검출된 외각선 좌표, 계층 정보]]), 면적,)] 형태의 결과

contour_info = [(c, cv2.contourArea(c),) for c in cv2.findContours(edges, cv2.RETR_LIST, cv2.CHAIN_APPROX_NONE)[0]]

print(contour_info)

image_area = inputIMG.shape[0] * inputIMG.shape[1]   
  
max_area = max_area * image_area 
min_area = min_area * image_area

mask = np.zeros(edges.shape, dtype = np.uint8)
#각 배경상에 위치에 값을 입력해서 
for contour in contour_info:

    if contour[1] > min_area and contour[1] < max_area:
        mask = cv2.fillConvexPoly(mask, contour[0], (255))

            


"""
mask = cv2.dilate(mask, None, iterations=mask_dilate_iter)
mask = cv2.erode(mask, None, iterations=mask_erode_iter)
mask = cv2.GaussianBlur(mask, (blur, blur), 0)
"""


            
mask_stack = np.dstack([mask]*3)



mask_stack = mask_stack.astype('float32') / 255.0            
inputIMG = inputIMG.astype('float32') / 255.0
        
masked = (mask_stack * inputIMG) + ((1-mask_stack) * mask_color)
masked = (masked * 255).astype('uint8')
cv2.imshow("Foreground", masked)
cv2.waitKey(0)

             

  
