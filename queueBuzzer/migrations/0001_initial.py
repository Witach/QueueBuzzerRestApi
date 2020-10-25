# Generated by Django 3.0.6 on 2020-10-23 20:19

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Consumer',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('consumerCode', models.CharField(max_length=20)),
            ],
        ),
        migrations.CreateModel(
            name='ConsumerOrder',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('consumerCode', models.CharField(max_length=20)),
                ('consumer', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.Consumer')),
            ],
        ),
        migrations.CreateModel(
            name='Point',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=50)),
                ('image', models.FileField(upload_to='')),
                ('logoImage', models.FileField(upload_to='')),
                ('avgAwateTime', models.IntegerField()),
                ('color', models.CharField(max_length=20)),
            ],
        ),
        migrations.CreateModel(
            name='PointOwner',
            fields=[
                ('point', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, primary_key=True, serialize=False, to='queueBuzzer.Point')),
                ('email', models.CharField(max_length=50)),
                ('password', models.CharField(max_length=20)),
            ],
        ),
        migrations.CreateModel(
            name='State',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
                ('point', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.Point')),
            ],
        ),
        migrations.CreateModel(
            name='Product',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=40)),
                ('price', models.FloatField()),
                ('category', models.CharField(max_length=20)),
                ('availability', models.BooleanField(default=True)),
                ('consumerOrder', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.ConsumerOrder')),
                ('point', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.Point')),
            ],
        ),
        migrations.AddField(
            model_name='consumerorder',
            name='point',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.Point'),
        ),
        migrations.AddField(
            model_name='consumerorder',
            name='state',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='queueBuzzer.State'),
        ),
    ]
