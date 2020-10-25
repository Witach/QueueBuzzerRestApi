from django.db import models

class Consumer(models.Model):
    consumerCode = models.CharField(max_length=20)

class Point(models.Model):
    name = models.CharField(max_length=50)
    image = models.ImageField(upload_to="images/")
    logoImage = models.ImageField(upload_to="images/")
    avgAwateTime = models.IntegerField()
    color = models.CharField(max_length=20)

    def __str__(self):
        return self.name

class State(models.Model):
    name = models.CharField(max_length=30)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.name

class ConsumerOrder(models.Model):
    consumerCode = models.CharField(max_length=20)
    consumer = models.ForeignKey(Consumer,on_delete=models.CASCADE)
    state = models.ForeignKey(State, on_delete=models.CASCADE)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.consumerCode

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
    consumerOrder = models.ForeignKey(ConsumerOrder,on_delete=models.CASCADE)
    point = models.ForeignKey(Point, on_delete=models.CASCADE)

    def __str__(self):
        return self.name




