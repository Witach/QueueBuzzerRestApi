from rest_framework import serializers

from queueBuzzer.models import Point


class PointSerializer(serializers.ModelSerializer):
    class Meta:
        model = Point
        fields = ['id',
                  'name',
                  'image',
                  'logo_image']