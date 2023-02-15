import time
import io
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

import cv2


def show_contour(path):
    image = cv2.imread(path)
    img_raw = np.ones(image.shape) - image
    img = img_raw.copy().astype('uint8')

    images, contours, hierachy = cv2.findContours(img, cv2.RETR_TREE,cv2.CHAIN_APPROX_TC89_KCOS )
    x0, y0 = zip(*np.squeeze(contours[0]))
    plt.plot(x0, y0, c="b")
    plt.show()







# Usage
show_contour('../Resource/RemoveBGIMG/니트/1139100_5_220.jpg')