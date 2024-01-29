import cv2
import numpy as np
import requests

def main():
    image_path = "./image2.jpg"
    # img = np.fromfile(image_path, dtype=np.uint8)
    img = cv2.imencode(image_path, cv2.IMREAD_COLOR)

    server_url = 'http://127.0.0.1:8000/api/v1/car-plate/image/' # 서버 요청 url
    response = requests.request('GET', server_url, data={'image':img}) # 2번 서버로 요청한 값을 response에 저장
    # response = requests.request('GET', server_url) # 2번 서버로 요청한 값을 response에 저장
    result = response.json() # json형태 확인

    print(result)

if __name__ == '__main__':
    main()