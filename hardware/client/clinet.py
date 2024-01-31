import requests, json
from PIL import Image
from io import BytesIO
import cv2
import base64


def main():
    image_path = './image/image3.jpg' # 이미지 경로
    image = open(image_path, 'rb')
    base64_str = base64.b64encode(image.read())
    base64_str = base64.b64decode(base64_str)
    
    server_url = 'http://127.0.0.1:8000/api/v1/car-plate/image/' # 서버 요청 url
    # response = requests.request('GET', server_url, data={'image':base64_str}) # 2번 서버로 요청한 값을 response에 저장
    response = requests.post(server_url, data=base64_str) # 2번 서버로 요청한 값을 response에 저장
    # print(base64_str, len(base64_str))
    result = response.json() # json형태 확인

    print(result['text'][0])
    
    

if __name__ == '__main__':
    main()