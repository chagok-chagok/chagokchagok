from django.http import JsonResponse
from rest_framework.response import Response
from rest_framework import status
from django.conf import settings
from . import utils
import requests
import json
from collections import deque


# for testing
def plate_recog(request):
    car_data = utils.plate_recog(request.body)
    return JsonResponse({'cardata':car_data})


def entrance(request):
    if request.method == 'POST':
        try:
            # data pre-processing
            car_data = utils.plate_recog(request.body)
            print(car_data)

            # request to spring server
            # header
            headers = {'Content-type': 'application/json', 'charset': 'utf8'}
            # url
            entrance_url = f'{settings.SPRING_URL}park/validation/carnum'
            # data
            json_data = json.dumps({'car_num': car_data[0]})
            # request
            response = requests.post(entrance_url, data=json_data, headers=headers)

            # convert response data to json for responsing to rasp
            result = response.json()
            return JsonResponse({'response': 'ok'})
        except:
            return JsonResponse({'response': 'fail to recog or via spring'})
    else:
        return JsonResponse({'response': 'fail'})


def hall(request):
    if request.method == 'POST':
        try:
            # data pre-processing
            car_data = utils.plate_recog(request.body)
            print(car_data)

            # request to spring server
            # header
            headers = {'Content-type': 'application/json', 'charset': 'utf8'}
            # url
            park_url = f'{settings.SPRING_URL}park/validation?car_no={car_data[0]}&area=A'
            # data
            # fixed section as A
            # json_data = json.dumps({'car_no': car_data[0], 'area': 'A'})
            # request
            response = requests.get(park_url, headers=headers)

            # convert response data to json for responsing to rasp
            result = response.json()

            print(result)
            return JsonResponse({'park_id': result['park_full_name']})
        except:
            return JsonResponse({'response': 'fail to recog or via spring'})
    else:
        return JsonResponse({'response': 'fail'})


def exit_way(request):
    if request.method == 'POST':
        try:
            # data pre-processing
            car_data = utils.plate_recog(request.body)
            print(car_data)

            # request to spring server
            # header
            headers = {'Content-type': 'application/json', 'charset': 'utf8'}
            # url
            exit_url = f'{settings.SPRING_URL}park/out'
            # data
            json_data = json.dumps({'car_no': car_data[0]})
            # request
            response = requests.post(exit_url, data=json_data, headers=headers)
            # convert response data to json for responsing to rasp
            result = response.text
            # if available, result should be processed
            print(result)
            return JsonResponse({'response': result})
        except:
            return JsonResponse({'response': 'fail to recog or via spring'})
    else:
        return JsonResponse({'response': 'fail'})



"""
# will make later
def auto_report(request):
    if request.method == 'POST':
        # request to spring server
        # header
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}

    return
"""


### will add managing system after completing others 
need2open_area_list = deque()
def bar(request, area):
    global need2open_area_list
    
    if request.method == 'GET':
        try:
            need2open_area_list.append(area)
            return Response(status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_405_METHOD_NOT_ALLOWED)
    

def bar_open(request):
    global need2open_area_list

    if request.method == 'GET':
        if need2open_area_list:
            try:
                area_no = need2open_area_list.popleft()
                return JsonResponse({'area_no': area_no})
            except:
                return JsonResponse({'area_no': 'empty'})
    else:
        return JsonResponse({'response': 'fail'})
