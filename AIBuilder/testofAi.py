"""

옷을 선정하여 누끼를 따는 작업 실시

1번 옷을 인지한다.
2번 옷의 색상 데이터를 받는다.




1번 구겨진 상태를 받아서 인식하는 방법(퀄리티 높음 난이도 어려움) 
2번 기본 옷의 셰이프를 인식해서 남는 부분을 맞추는 방법(퀄리티 중간 난이도 중간)
안보이는 디자인 부분을 받을 방법생각
유사 이미지 셋으로 받는등의 방식
3번 디자인 포지션을 인식하고, 색상이나 포켓등의 디자인을 받아올 작업 실시
4번 받아온 값을 통해서 옷에 재배치 실시 
5번 렌더링 실시

"""


"""
선행작업
pip install [numpy, opencv-python, matplotlib ]
"""


import numpy as np
import cv2
from matplotlib import pyplot as plt
import os


"""

배경 이미지 제거 (누끼작업 )

img - 입력 이미지
mask - 배경, 전경 또는 가능한 배경/전경 등의 영역을 지정하는 마스크 이미지입니다. cv2.GC_BGD, cv2.GC_FGD, cv2.GC_PR_BGD, cv2.GC_PR_FGD 플래그로 수행 하거나 단순히 전달합니다. 0,1,2,3 이미지.
rect - (x,y,w,h) 형식의 전경 개체를 포함하는 사각형의 좌표입니다.
bdgModel , fgdModel - 알고리즘이 내부적으로 사용하는 배열입니다. 크기가 (1,65)인 두 개의 np.float64 유형 0 배열을 생성하기만 하면 됩니다.
iterCount - 알고리즘이 실행해야 하는 반복 횟수입니다.
모드 - cv2.GC_INIT_WITH_RECT 또는 cv2.GC_INIT_WITH_MASK 이거나 사각형을 그릴지 아니면 마지막 터치업 스트로크를 그릴지 결정하는 조합이어야 합니다.
"""






def RemoveBackground(file_path):
    print(file_path)
    #이미지 인식 Numpy array 형태 반환
    img_BGR = cv2.imread(file_path)

    #이미지 양식 BGR에서 RGB로 변경
    img = cv2.cvtColor(img_BGR, cv2.COLOR_BGR2RGB)
  
    #이미지 사이즈인 Y*X 크기의 0 행렬 반환 uint8은 숫자 데이터 타입(양수 0~255)
    mask = np.zeros(img.shape[:2],np.uint8)

    #1*65 크기의 0 행렬 반환 float64데이터타입        
    bgdModel = np.zeros((1,65),np.float64)
    fgdModel = np.zeros((1,65),np.float64)

    #시작점 x,y,width, height 
    rect = (10,10,450,500)

    #그랩컷 실행 1.원본 이미지, 2. 마스크. 3. 범위 지정 사각형 4. 배경용 임시배열 5. 전경용 임시배열 6. 반복 횟수 7번 사각형을 위한 초기화
    cv2.grabCut(img,mask,rect,bgdModel,fgdModel,5,cv2.GC_INIT_WITH_RECT)


    #배경 (2, 0)인곳은 0으로 그외에는 1로 설정한 마스크 생성
    mask2 = np.where((mask==2)|(mask==0),0,1).astype('uint8')

    #새 마스크를 통해 배경 제외
    img = img*mask2[:,:,np.newaxis]

    plt.imshow(img),plt.colorbar(),plt.show()


dir_path="./Resource/TestIMG"

#재귀탐색 폴더 내부의 폴더와 이미지 색인
for (root, directories, files) in os.walk(dir_path):
    for file in files:
        file_path = os.path.join(root, file)

        #배경 제거 프로세스
        RemoveBackground(file_path)




