from django.shortcuts import get_object_or_404, render
from .models import Point, Product


def index(request):
    point = get_object_or_404(Point)
    product = Product.objects.all()
    context = {
        'image': point.image,
        'logo': point.logo_image,
        'product': product,
    }

    return render(request, 'queueBuzzer/index.html', context)
# Create your views here.
