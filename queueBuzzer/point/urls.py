from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns

from queueBuzzer.point import views

urlpatterns = [
    path('', views.PointList.as_view()),
    path('<int:pk>/', views.PointDetail.as_view()),
]
urlpatterns = format_suffix_patterns(urlpatterns)