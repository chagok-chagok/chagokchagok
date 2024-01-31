from django.http import JsonResponse
from io import BytesIO
from PIL import Image
from . import utils
import requests


""" utils 에 있는 plate_recog 함수로 대체
# Create your views here.
def plate_recog(request):
    if request.method == 'POST':
        img_out = Image.open(BytesIO(request.body))
        img_out = np.array(img_out)
        img_out = cv2.cvtColor(img_out, cv2.COLOR_BGR2RGB)
        
        cv2.imwrite('./image.png', img_out)
        text = utils.main('./image.png')
        return JsonResponse({'text':text})
    else:
        return JsonResponse({'error':'error'})
"""


def entrance(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        entrance_url = 'http://localhost:8080/park/allocation/'
        response = requests.post(entrance_url, data=car_data)
        result = response.json()
        return JsonResponse()


def hall(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        park_url = 'http://localhost:8080/park/validation/'
        response = requests.post(park_url, data=car_data)
        result = response.json()
        park_id = result['park_id'][0]
        return JsonResponse({'park_id':park_id})


def exit_way(request):
    if request.method == 'POST':
        car_data = utils.plate_recog(request.body)
        exit_url = 'http://localhost:8080/park/out/'
        response = requests.post(exit_url, data=car_data)
        result = response.json()
        return JsonResponse()



park_list = []
def bar(request):
    if request.method == 'POST':
        result = utils.plate_recog(request.body)
        park_no = result['park_no'][0]
        park_list.append(park_no)
        return JsonResponse({'okay': 'okay'})
    

def bar_open(request):
    if request.method == 'POST':
        if park_list:
            park_no = park_list.pop(0)
            return JsonResponse({'park_no': park_no})