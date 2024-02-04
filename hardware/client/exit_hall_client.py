import cv2
from pymata4 import pymata4
import time
import requests
import base64

# varialbes
# for auto report system
trigpin_section = 2
echopin_section = 3

# for hallway capture trigger
trigpin_hall = 5
echopin_hall = 6
# each parking lot's barricade servo
# need to change variable name or setting where it is
motor_hall_1 = 7
motor_hall_2 = 8

# for exit capture trigger
trigpin_exit = 9
echopin_exit = 10
# exit's barricade servo
motor_exit = 11

# set board
board = pymata4.Pymata4()

# webcam set for hallway
cap1 = cv2.VideoCapture()
cap1.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap1.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

# webcam set for exit
cap2 = cv2.VideoCapture(1)
cap2.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap2.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)


# image capture function
def capture_image(ret, frame):
    cv2.imwrite('image.jpg', frame)
    image_path = 'image.jpg'
    image = open(image_path, 'rb')
    base64_str = base64.b64encode(image.read())
    return base64.b64decode(base64_str)


"""
def hall(data):
    distance = data[2]
    print(distance)
    if distance <= 10:
        ret, frame = cap1.read()
        image_source = capture_image(ret, frame)
        hall_url = 'http://127.0.0.1:8000/api/v1/hall/'
        hall_response = requests.post(hall_url, data=image_source)
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
        ret, frame = cap2.read()
        image_source = capture_image(ret, frame)
        exit_url = 'http://127.0.0.1:8000/api/v1/exit-way/'
        exit_response = requests.post(exit_url, data=image_source)
        exit_result = exit_response.json()
        if exit_result:
            board.servo_write(motor_exit, 90)
            if exit_result['park_no'][0] == 1:
                board.servo_write(motor_hall_1, 0)
            else:
                board.servo_write(motor_hall_2, 0)
    else:
        board.servo_write(motor_exit, 0)
"""


# will add managing system after completing others
def bar():
    bar_url = 'http://127.0.0.1:8000/api/v1/admin/bar/'
    bar_response = requests.post(bar_url)
    bar_result = bar_response.json()
    park_no = bar_result['park_no'][0]
    if park_no:
        board.servo_write(f'motor_hall_{park_no}', 90)


# callback func for executing
def call_back(data):
    dis = data[2]

# hallway pin settings
board.set_pin_mode_sonar(trigpin_hall, echopin_hall, call_back)
board.set_pin_mode_servo(motor_hall_1)
board.set_pin_mode_servo(motor_hall_2)

# exit pin settings
board.set_pin_mode_sonar(trigpin_exit, echopin_exit, call_back)
board.set_pin_mode_servo(motor_exit)

# auto report system pin setting
board.set_pin_mode_sonar(trigpin_section, echopin_section, call_back)

# AIserver url
url = 'http://192.168.31.17:8000/api/v1/'
# backend server url
spring_url = 'http://192.168.31.251:8080/'

while True:
    try:
        time.sleep(1)
        hall_dis = board.sonar_read(trigpin_hall)
        exit_dis = board.sonar_read(trigpin_exit)
        sect_dis = board.sonar_read(trigpin_section)

        # hall way logic
        if hall_dis <= 3:
            # capture webcam
            ret, frame = cap1.read()
            # data processing
            image_source = capture_image(ret, frame)
            # request url
            hall_url = f'{url}hall/'
            # request to django
            hall_response = requests.post(hall_url, data=image_source)
            hall_result = hall_response.json()

            # need to change conditions after checking 
            if hall_result:
                if hall_result['park_no'][0] == 1:
                    board.servo_write(motor_hall_1, 90)
                else:
                    board.servo_write(motor_hall_2, 90)

        # exit logic
        if exit_dis <= 3:
            # capture webcam
            ret, frame = cap2.read()
            # data processing
            image_source = capture_image(ret, frame)
            # reuqest url
            exit_url = f'{url}exit-way/'
            # request to django
            exit_response = requests.post(exit_url, data=image_source)
            exit_result = exit_response.json()

            if exit_result:
                # open barricate for exit
                board.servo_write(motor_exit, 90)
                
                # check car's parking section
                # will change condition after deciding parking section number 1 to another
                section = exit_result['park_no'][0]
                if section == 1:
                    # check parking section is empty
                    # if it is not empty
                    if sect_dis <= 3:
                        report_url = f'{spring_url}park/auto?location={section}'
                        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
                        hall_response = requests.get(hall_url, headers=headers)
                        
                    # if it is empty
                    else:
                        board.servo_write(motor_hall_1, 0)
                else:
                    board.servo_write(motor_hall_2, 0)
        
    except Exception:
        board.shutdown()
        break