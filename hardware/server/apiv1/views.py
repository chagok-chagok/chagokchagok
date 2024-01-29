from django.http import JsonResponse
from io import BytesIO
from PIL import Image
from . import utils
import cv2
import numpy as np


# Create your views here.
def plate_recog(request):
    if request.method == 'POST':
        img_out = Image.open(BytesIO(request.body))
        img_out = np.array(img_out)
        img_out = cv2.cvtColor(img_out, cv2.COLOR_BGR2RGB)
        
        cv2.imwrite('./image.png', img_out)
        text = utils.main('./image.png') # 여기에 번호판 인식 함수랑 집어 넣고 돌려서 보내
        return JsonResponse({'text':text})
    else:
        return JsonResponse({'error':'error'})

