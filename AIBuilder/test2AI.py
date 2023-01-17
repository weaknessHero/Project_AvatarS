"""
완료
1번 배경 제거 

진행
2번 옷의 구김 제거
- 옷의 메인컬러보다. 일정하게 RGB값이 높은 값은 제거(그림자 혹은 주름)
3번 옷의 색상 데이터를 받는다.
3-1번 많이 쓰이는 색상을 통해서 옷을 전체적으로 인식 시키는 방법 (퀄리티 낮음 난이도 쉬움, 데이터 적어도 됨) 
3-2번 기본 옷의 셰이프를 인식해서 남는 부분을 맞추는 방법(퀄리티 높음 난이도 높음 데이터 많이 필요)
3번 디자인 포지션을 인식하고, 색상이나 포켓등의 디자인을 받아올 작업 실시
4번 받아온 값을 통해서 옷에 재배치 실시 
5번 렌더링 실시




"""


"""
선행작업
pip install [numpy, opencv-python, matplotlib ]
"""




import time
import io
import numpy as np
import matplotlib.pyplot as plt
import os
import requests
import cv2



import tensorflow as tf


import tensorflow_datasets as tfds
from tensorflow_examples.models.pix2pix import pix2pix

import os
import time
import matplotlib.pyplot as plt
from IPython.display import clear_output

AUTOTUNE = tf.data.AUTOTUNE



"""
cycleGAN
https://www.youtube.com/watch?v=Fkqf3dS9Cqw

X 도메인 데이터와 Y 도메인 데이터를 통해서 
두 도메인 간의 이미지 변환하는 법 학습


목적 이미지를 인식 받아서 데이터 정보로 출력하는 방향을 목적으로 한다. 

 input : 의상 이미지
 output : 3D 모델에 입히기 적합한 데이터셋
  
"""


from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True
from rembg.bg import remove as remove_bg



#배경 제거
def RemoveBackground(file_path):
    orig_img = Image.open(file_path)
      
    #이미지 확인용
    fig = plt.figure(figsize=(10, 10))
    fig.add_subplot(1, 2, 1)
    plt.imshow(orig_img)

    #이간 측정
    started = time.time()
    
    #백그라운드 제거
    result = remove_bg(orig_img)
       
    #이전 이미지 확인용 
    fig.add_subplot(1, 2, 2)
    plt.imshow(result)
    plt.show()

    #시간 측정
    elapsed = time.time() - started
    print(f'it takes {elapsed} seconds for removing bg.')

    return np.asarray(result) # a는 읽기 전용입니다. 
    
"""
단순화
그림자 주름등의 렌더링에 불필요한 데이터 제거
""" 



dir_path="./Resource/TestIMG"

#재귀탐색 폴더 내부의 폴더와 이미지 색인
for (root, directories, files) in os.walk(dir_path):
    for file in files:
        #이미지 주소 받기
        file_path = os.path.join(root, file)
        #배경 제거 프로세스
        IMG = RemoveBackground(file_path)
        Simplification(IMG)
