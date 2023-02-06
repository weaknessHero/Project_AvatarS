import time
import io
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True
from rembg.bg import remove as remove_bg

import cv2


def show_rembg(path):

    with open(path, 'rb') as i:
        with open('BBB.jpg', 'wb') as o:
            input = i.read()
            output = remove_bg(input)
            o.write(output)
            
            
# Usage
show_rembg('../Resource/AIInputIMG/니트/1139100_5_220.jpg')