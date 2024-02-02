from django.http import JsonResponse
from . import utils
import requests
import json


# test
def plate_recog(request):
    car_data = utils.plate_recog(request.body)
    return JsonResponse({'cardata':car_data})


def entrance(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
        entrance_url = 'http://192.168.31.251:8080/park/validate/carnum'
        json_data = json.dumps({'car_no': car_data[0]})
        response = requests.post(entrance_url, data=json_data, headers=headers)
        result = response.json()
        return JsonResponse({'response': result})
    else:
        return JsonResponse({'response': 'fail'})


def hall(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
        park_url = 'http://192.168.31.251:8080/park/validation/'
        response = requests.post(park_url, data=car_data, headers=headers)
        result = response.json()
        park_id = result['park_id'][0]
        return JsonResponse({'park_id':park_id})
    else:
        return JsonResponse({'response': 'fail'})


def exit_way(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
        exit_url = 'http://192.168.31.251:8080/park/out/'
        response = requests.post(exit_url, data=car_data, headers=headers)
        result = response.json()
        return JsonResponse()
    else:
        return JsonResponse({'response': 'fail'})


### 예외처리하는 파트 추후 수정할거임
park_list = []
def bar(request):
    if request.method == 'POST':
        result = utils.plate_recog(request.body)
        park_no = result['park_no'][0]
        park_list.append(park_no)
        return JsonResponse({'okay': 'okay'})
    else:
        return JsonResponse({'response': 'fail'})
    

def bar_open(request):
    if request.method == 'POST':
        if park_list:
            park_no = park_list.pop(0)
            return JsonResponse({'park_no': park_no})
    else:
        return JsonResponse({'response': 'fail'})
