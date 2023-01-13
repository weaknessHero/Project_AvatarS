
import time
import io
import numpy as np
import matplotlib.pyplot as plt
import os
import requests


from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True
from rembg.bg import remove as remove_bg



def RemoveBackground(file_path):
    fig = plt.figure(figsize=(10, 10))
    # show original image
    fig.add_subplot(1, 2, 1)
    orig_img = Image.open(file_path)
    
    # show bg removed image
    orig_img = Image.open(file_path)

    started = time.time()
    result = remove_bg(orig_img)
    elapsed = time.time() - started
    print(f'it takes {elapsed} seconds for removing bg.')
    result.show()


dir_path="./Resource/TestIMG"




#재귀탐색 폴더 내부의 폴더와 이미지 색인
for (root, directories, files) in os.walk(dir_path):
    for file in files:
        file_path = os.path.join(root, file)
        #배경 제거 프로세스
        RemoveBackground(file_path)
