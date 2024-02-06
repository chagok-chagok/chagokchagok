from django.http import JsonResponse
from django.conf import settings
from . import utils
import requests
import json


# for testing
def plate_recog(request):
    car_data = utils.plate_recog(request.body)
    return JsonResponse({'cardata':car_data})


def entrance(request):
    if request.method == 'POST':
        # data pre-processing
        car_data = utils.plate_recog(request.body)

        # request to spring server
        # header
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
        # url
        entrance_url = f'{settings.SPRING_URL}park/validate/carnum'
        # data
        json_data = json.dumps({'car_no': car_data[0]})
        # request
        response = requests.post(entrance_url, data=json_data, headers=headers)

        # convert response data to json for responsing to rasp
        result = response.json()
        return JsonResponse({'response': result})
    else:
        return JsonResponse({'response': 'fail'})


def hall(request):
    if request.method == 'POST':
        # data pre-processing
        car_data = utils.plate_recog(request.body)

        # request to spring server
        # header
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}
        # url
        park_url = f'{settings.SPRING_URL}park/validation'
        # data
        # fixed section as A
        json_data = json.dumps({'car_no': car_data[0], 'area': 'A'})
        # request
        response = requests.post(park_url, data=json_data, headers=headers)

        # convert response data to json for responsing to rasp
        result = response.json()

        # need to check ####
        park_id = result['park_id'][0]
        return JsonResponse({'park_id':park_id})
    else:
        return JsonResponse({'response': 'fail'})


def exit_way(request):
    if request.method == 'POST':
        # data pre-processing
        car_data = utils.plate_recog(request.body)

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
        result = response.json()
        # if available, result should be processed
        return JsonResponse({'response': result})
    else:
        return JsonResponse({'response': 'fail'})


# will make later
def auto_report(request):
    if request.method == 'POST':
        # request to spring server
        # header
        headers = {'Content-type': 'application/json', 'charset': 'utf8'}

    return


### will add managing system after completing others
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
