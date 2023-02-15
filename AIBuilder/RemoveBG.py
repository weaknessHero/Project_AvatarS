# -*- coding: utf-8 -*-
"""
크롤링된 파일의 배경 제거 작업

선행조건 crawling.py

"""

import os
from rembg import remove
import cv2

from multiprocessing import Pool, freeze_support
from functools import partial


data_dir="../Resource/AIInputIMG"
save_dir="../Resource/RemoveBGIMG"


#배경 제거 과정
def RemoveBackground(path, DirName):
   
    #확장자 확인 및 일원화 과정
    file_Name, ext = os.path.splitext(path)
    
    
    file_path= data_dir + '/'+DirName+'/'+file_Name + ext
    save_path= save_dir + '/'+DirName+'/'+file_Name+'.jpg'
    
    if not os.path.exists(save_path) :
        input = cv2.imread(file_path)
        output = remove(input)
        cv2.imwrite(save_path, output)
            
            
"""
단순화
그림자 주름등의 렌더링에 불필요한 데이터 제거
""" 


if __name__=='__main__':

    '''
    4코어 멀티프로세스로 동작
    '''
    #윈도우 멀티 프로세싱시 필요작
    #4코어
    
    freeze_support() 
    pool = Pool(processes=4)

    #재귀탐색 폴더 내부의 폴더와 이미지 색인 
    
    for dirName in os.listdir(data_dir) :
        pool.map(partial(RemoveBackground, DirName=dirName), os.listdir(data_dir+'/'+dirName))

    pool.close()
    pool.join()


