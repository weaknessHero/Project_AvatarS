# -*- coding: utf-8 -*-
"""
크롤링된 파일의 배경 제거 작업

선행조건 crawling.py

"""

import os
import cv2



import numpy as np
import onnxruntime as ort

from multiprocessing import Pool, freeze_support
from functools import partial
from PIL import Image


import onnx
from rembg import remove
from PIL import Image


data_dir="../Resource/AIInputIMG"
save_dir="../Resource/RemoveBGIMG"

    
#배경 제거 과정
def RemoveBackground(img_path):
   
    
    DirName, fileLoc = img_path
    #확장자 확인 및 일원화 과정
    file_Name, ext = os.path.splitext(fileLoc)
    

    
    file_path= data_dir + '/'+DirName+'/'+file_Name + ext
    save_path= save_dir + '/'+DirName+'/'+file_Name+'.png'
    
    if not os.path.exists(save_path) :
        input = Image.open(file_path)
        output = remove(input)
        output.save(save_path)



if __name__=='__main__':

    '''
    4코어 멀티프로세스로 동작
    '''
    #윈도우 멀티 프로세싱시 필요작
    #4코어



    freeze_support() 
    pool = Pool(processes=4)
    
    
    image_paths = []

    for dir_name in os.listdir(data_dir):
        dir_path = os.path.join(data_dir, dir_name)
        if os.path.isdir(dir_path):
            for file_name in os.listdir(dir_path):
                if file_name.endswith('.jpg') or file_name.endswith('.png'):
                    image_paths.append((os.path.basename(dir_path) , file_name ))

    print(image_paths)

    #재귀탐색 폴더 내부의 폴더와 이미지 색인 
    pool.map(RemoveBackground, image_paths)

    pool.close()
    pool.join()


