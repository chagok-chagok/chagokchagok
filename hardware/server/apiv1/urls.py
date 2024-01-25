from django.urls import path
from . import views

urlpatterns = [
    path('car-plate/image/', views.plate_recog),
]
