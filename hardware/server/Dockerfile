From python:3.9.13

RUN pip install --upgrade pip

WORKDIR /usr/src/app

COPY . .

WORKDIR ./server
RUN pip install -r requirements.txt

CMD ["python3", "manage.py", "runserver", "0.0.0.0:8080"]

EXPOSE 8080