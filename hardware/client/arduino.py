from glob import glob
import torch
import easyocr
import numpy as np
import cv2
import sys
from PIL import Image
from pymata4 import pymata4
import time
import requests
import base64


trigpin_entrance = 2
echopin_entrance = 3
motor_entrance = 4

trigpin_hall = 5
echopin_hall = 6
motor_hall_1 = 7
motor_hall_2 = 8

trigpin_exit = 9
echopin_exit = 10
motor_exit = 11

board = pymata4.Pymata4()

cap1 = cv2.VideoCapture(0)
cap1.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap1.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

cap2 = cv2.VideoCapture(1)
cap2.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap2.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

cap3 = cv2.VideoCapture(2)
cap3.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap3.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)


    


def entrance(data):
    distance = data[2]
    print(distance)
    if distance <= 10:
        ret, frame = cap1.read()
        cv2.imwrite('image.jpg', frame)
        image_path = 'image.jpg'
        image = open(image_path, 'rb')
        base64_str = base64.b64encode(image.read())
        base64_str = base64.b64decode(base64_str)
        ai_server_url = 'http://127.0.0.1:8000/api/v1/car-plate/image/'
        ai_response = requests.post(ai_server_url, data=base64_str)
        ai_result = ai_response.json()
        entrance_url = 'http://127.0.0.1:8000/api/v1/entrance/'
        entrance_response = requests.post(entrance_url, data=ai_result)
        entrance_result = entrance_response.json()
        if entrance_result:
            board.servo_write(motor_entrance, 90)
    else:
        board.servo_write(motor_entrance, 0)


def hall(data):
    distance = data[2]
    print(distance)
    if distance <= 10:
        ret, frame = cap2.read()
        cv2.imwrite('image.jpg', frame)
        image_path = 'image.jpg'
        image = open(image_path, 'rb')
        base64_str = base64.b64encode(image.read())
        base64_str = base64.b64decode(base64_str)
        ai_server_url = 'http://127.0.0.1:8000/api/v1/car-plate/image/'
        ai_response = requests.post(ai_server_url, data=base64_str)
        ai_result = ai_response.json()
        hall_url = 'http://127.0.0.1:8000/api/v1/hall/'
        hall_response = requests.post(hall_url, data=ai_result)
        hall_result = hall_response.json()
        if hall_result:
            board.servo_write(motor_entrance, 90)
            if hall_result['park_no'][0] == 1:
                board.servo_write(motor_hall_1, 90)
            else:
               board.servo_write(motor_hall_2, 90)



def exit_way(data):
    distance = data[2]
    print(distance)
    if distance <= 10:
        ret, frame = cap3.read()
        cv2.imwrite('image.jpg', frame)
        image_path = 'image.jpg'
        image = open(image_path, 'rb')
        base64_str = base64.b64encode(image.read())
        base64_str = base64.b64decode(base64_str)
        ai_server_url = 'http://127.0.0.1:8000/api/v1/car-plate/image/'
        ai_response = requests.post(ai_server_url, data=base64_str)
        ai_result = ai_response.json()
        exit_url = 'http://127.0.0.1:8000/api/v1/exit-way/'
        exit_response = requests.post(exit_url, data=ai_result)
        exit_result = exit_response.json()
        if exit_result:
            board.servo_write(motor_exit, 90)
            if exit_result['park_no'][0] == 1:
                board.servo_write(motor_hall_1, 0)
            else:
                board.servo_write(motor_hall_2, 0)
    else:
        board.servo_write(motor_exit, 0)


def bar():
    bar_url = 'http://127.0.0.1:8000/api/v1/admin/bar/'
    bar_response = requests.post(bar_url)
    bar_result = bar_response.json()
    park_no = bar_result['park_no'][0]
    if park_no:
        board.servo_write(f'motor_hall_{park_no}', 90)


board.set_pin_mode_sonar(trigpin_entrance, echopin_entrance, entrance)
board.set_pin_mode_servo(motor_entrance)

board.set_pin_mode_sonar(trigpin_hall, echopin_hall, hall)
board.set_pin_mode_servo(motor_hall_1)
board.set_pin_mode_servo(motor_hall_2)

board.set_pin_mode_sonar(trigpin_exit, echopin_exit, exit_way)
board.set_pin_mode_servo(motor_exit)


while True:
    try:
        time.sleep(1)
        board.sonar_read(trigpin_entrance)
        board.sonar_read(trigpin_hall)
        board.sonar_read(trigpin_exit)
        bar()
    except Exception:
        board.shutdown()