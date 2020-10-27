from django.db import models

class Consumer(models.Model):
    consumerCode = models.CharField(max_length=20)

class Point(models.Model):
    name = models.CharField(max_length=50)
    image = models.ImageField(upload_to="images/")
    logo_image = models.ImageField(upload_to="images/")
    avg_await_time = models.IntegerField()
    color = models.CharField(max_length=20)

    def __str__(self):
        return self.name

class State(models.Model):
    name = models.CharField(max_length=30)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.name

class ConsumerOrder(models.Model):
    consumer_code = models.CharField(max_length=20)
    consumer = models.ForeignKey(Consumer, on_delete=models.CASCADE)
    state = models.ForeignKey(State, on_delete=models.CASCADE)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.consumer_code

class PointOwner(models.Model):
    point = models.ForeignKey(Point, on_delete=models.CASCADE)
    email = models.CharField(max_length=50)
    password = models.CharField(max_length=20)

    def __str__(self):
        return self.email

class Product(models.Model):
    name = models.CharField(max_length=40)
    price = models.FloatField()
    category = models.CharField(max_length=20)
    availability = models.BooleanField(default=True)
    consumer_order = models.ForeignKey(ConsumerOrder, on_delete=models.CASCADE)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.name




