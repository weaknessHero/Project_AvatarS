# -*- coding: utf-8 -*-
"""
무신사를 통한 이미지 크롤링

작동시 요구사항(anaconda3 기준)
pip install [beautifulsoup4]

다운로드 크기 약 110MB
작동시 약 8~20 분 이내의 시간이 걸릴수 있음 
다운로드시 중복이 제거된채로 
"""

'''
시간 효율비


받아오기 + 확인 + 저장 시간
싱글시 --- 921.3232777118683 seconds ---
멀티시 --- 324.3662214279175 seconds ---

받아오기 + 확인
멀티시 --- 27.65297770500183 seconds ---
'''


from multiprocessing import Pool, freeze_support
import requests
from bs4 import BeautifulSoup as bs
from urllib.request import urlopen
import urllib.parse
import time
import os


page = 'https://www.musinsa.com/search/musinsa/goods?q='
searchKeyword = ['코트' , '패딩', '자켓', '원피스', '티셔츠', '니트', '바지','반바지', '스커트']
dataA = '&list_kind=small&sortCode=pop&sub_sort=&page='
dataB = '&display_cnt=0&saleGoods=&includeSoldOut=&setupGoods=&popular=&category1DepthCode=&category2DepthCodes=&category3DepthCodes=&selectedFilters=&category1DepthName=&category2DepthName=&brandIds=&price=&colorCodes=&contentType=&styleTypes=&includeKeywords=&excludeKeywords=&originalYn=N&tags=&campaignId=&serviceType=&eventType=&type=popular&season=&measure=&openFilterLayout=N&selectedOrderMeasure=&shoeSizeOption=&groupSale=&d_cat_cd=&attribute='
path = './resource/TestIMG/'    


def findIMG(keyword):

    for pageNum in range(1,16):
        encodingKeyword = urllib.parse.quote_plus(keyword)
        url = page + encodingKeyword + dataA + str(pageNum) + dataB
        
        #url 조합해서 만들기
        html = urlopen(url)
        soup = bs(html, 'html.parser')
        codes = soup.find_all(class_="lazyload lazy")

        for code in codes: #한 페이지 내의 다중 이미지 처리
            fileName = code.attrs['data-original'].split('/')[-1]
            filepath = path + keyword+'/'+ fileName
            
            #존재할경우 저장하지 않음
            if not os.path.exists(filepath) :

            
                IMGurl = 'https:' + code.attrs['data-original']
                with urlopen(IMGurl) as img:
                    IMG = img.read()
                    with open(filepath,'wb') as file: # w - write b - binary   
                        file.write(IMG)
            

def createFolder(directory):
    try:
        if not os.path.exists(directory):
            os.makedirs(directory)
    except OSError:
        print ('Error: Creating directory. ' +  directory)
 




if __name__=='__main__':

    '''
    4코어 멀티프로세스로 동작
    '''
    
    #폴더 제작
    for keyword in searchKeyword:
        createFolder(path+keyword)
        
        
        
    #시간측정용
    start_time = time.time()
    
    freeze_support() #윈도우 멀티 프로세싱시 필요작
    pool = Pool(processes=4)#4코어

    #리스트의 값을 함수에 매핑해 멀티프로세스로 실행
    pool.map(findIMG, searchKeyword)
        
    pool.close()
    pool.join()
    print("--- %s seconds ---" % (time.time() - start_time))
    

