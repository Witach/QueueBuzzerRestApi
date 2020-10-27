from django.conf.urls.static import static
from django.urls import path, include

from chmura import settings
from queueBuzzer import views

app_name = 'queueBuzzer'
urlpatterns = [
    path('', views.index, name="index"),
    path('point/', include('queueBuzzer.point.urls'))
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

