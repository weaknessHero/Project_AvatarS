# -*- coding: utf-8 -*-
"""
크롤링된 파일의 배경 제거 작업

선행조건 crawling.py

"""

import os
import numpy as np
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

from rembg.bg import remove as remove_bg
import cv2
from multiprocessing import Pool, freeze_support
from functools import partial

data_dir="../Resource/AIInputIMG"
save_dir="../Resource/RemoveBGIMG"


#배경 제거 과정
def RemoveBackground(file_Name, DirName):
    #확장자 확인 및 일원화 과정
    Name, ext = os.path.splitext(file_Name)
    
    save_path= '/'+DirName+'/'+Name+'.jpg'
    file_path= '/'+DirName+'/'+file_Name
    
    #파일 존재여부 확인 후 실행
    if not os.path.exists(save_dir+save_path) :
        orig_img = Image.open(data_dir+file_path)
        
        #배경 지우기 및 파일 저장 양식인 np.array 화
        img = np.array(remove_bg(orig_img))
        result = cv2.cvtColor(img, cv2.COLOR_BGRA2RGBA)
        
        try:
            #저장용
            cv2.imwrite(save_dir+save_path, result)
        except:
            #저장시 에러확인
            print(save_dir+save_path)
    

            
"""
단순화
그림자 주름등의 렌더링에 불필요한 데이터 제거
""" 


if __name__=='__main__':

    '''
    4코어 멀티프로세스로 동작
    '''
     
    freeze_support() #윈도우 멀티 프로세싱시 필요작
    #4코어
    pool = Pool(processes=4)
    #재귀탐색 폴더 내부의 폴더와 이미지 색인
    for directory in os.listdir(data_dir):    
        pool.map(partial(RemoveBackground, DirName=directory), os.listdir(data_dir+'/'+directory))
   
    pool.close()
    pool.join()
