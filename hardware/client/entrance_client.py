import cv2
from pymata4 import pymata4
import time
import requests
import base64


trigpin_entrance = 2
echopin_entrance = 3
motor_entrance = 4

board = pymata4.Pymata4()

cap1 = cv2.VideoCapture(0)
cap1.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap1.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)


def capture_image(ret, frame):
    cv2.imwrite('image.jpg', frame)
    image_path = 'image.jpg'
    image = open(image_path, 'rb')
    base64_str = base64.b64encode(image.read())
    return base64.b64decode(base64_str)


"""
def entrance(data):
    distance = data[2]
    print(distance)
    if distance <= 10:
        ret, frame = cap1.read()
        image_source = capture_image(ret, frame)
        entrance_url = 'http://192.168.31.17:8000/api/v1/entrance/'
        entrance_response = requests.post(entrance_url, data=image_source)
        entrance_result = entrance_response.json()
        print(entrance_result)
        if entrance_result:
            board.servo_write(motor_entrance, 90)
        time.sleep(10)
    else:
        board.servo_write(motor_entrance, 0)
"""


def call_back(data):
    dis = data[2]


board.set_pin_mode_sonar(trigpin_entrance, echopin_entrance, call_back)
board.set_pin_mode_servo(motor_entrance)

while True:
    try:
        time.sleep(1)
        dist = board.sonar_read(trigpin_entrance)
        if dist[0] <= 5:
            ret, frame = cap1.read()
            image_source = capture_image(ret, frame)
            entrance_url = 'http://192.168.31.17:8000/api/v1/entrance/'
            entrance_response = requests.post(entrance_url, data=image_source)
            entrance_result = entrance_response.json()

            if entrance_result:
                board.servo_write(motor_entrance, 90)
            time.sleep(5)
        board.servo_write(motor_entrance, 0)
    except Exception:
        board.shutdown()