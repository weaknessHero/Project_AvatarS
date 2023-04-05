import os
from concurrent.futures import ThreadPoolExecutor, as_completed
from PIL import Image
import numpy as np
from rembg import remove


def remove_background(image_path, save_path):
    """
    이미지 경로에서 배경을 제거하고 새로운 파일로 저장하는 함수
    """
    if os.path.exists(save_path):
        return
    with Image.open(image_path) as img:
        img_array = np.array(img)
        alpha = remove(img_array)
        result = Image.fromarray(alpha)
        result.save(save_path)


def process_images_in_folder(folder_path):
    """
    지정된 폴더 내의 모든 이미지에 대해 백그라운드 제거 작업을 수행하는 함수
    """
    images = [f for f in os.listdir(folder_path) if f.lower().endswith(('.jpg', '.jpeg', '.png'))]
    for image in images:
        image_path = os.path.join(folder_path, image)
        save_path = os.path.join('output', os.path.basename(folder_path), os.path.splitext(image)[0] + '.png')
        remove_background(image_path, save_path)


def process_folders_in_directory(root_directory):
    """
    지정된 디렉토리 내의 모든 폴더에 대해 이미지 처리 작업을 수행하는 함수
    """
    with ThreadPoolExecutor(max_workers=4) as executor:
        futures = [executor.submit(process_images_in_folder, os.path.join(root_directory, folder))
                   for folder in os.listdir(root_directory) if os.path.isdir(os.path.join(root_directory, folder))]
        for future in as_completed(futures):
            future.result()


if __name__ == '__main__':
    data_dir = 'data'
    if not os.path.exists('output'):
        os.makedirs('output')

    process_folders_in_directory(data_dir)