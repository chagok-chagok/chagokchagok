from django.urls import path
from . import views

urlpatterns = [
    path('car-plate/image/', views.plate_recog),
    path('entrance/', views.entrance),
    path('hall/', views.hall),
    path('exit-way/', views.exit_way),
    path('bar', views.bar),
]
