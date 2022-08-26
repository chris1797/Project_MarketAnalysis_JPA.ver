from flask import Flask
import cx_Oracle
import matplotlib as mpl # 그래프 옵션
import matplotlib.pyplot as plt # 그래프 출력

# 1. 터미널에서 flask run 실행
# 2. http://127.0.0.1:5000 주소창에서 접속

app = Flask(__name__)

# localhost/home 연결 시 flask_server.html 로 연결
@app.route('/')
def hello():
    return render_template('flask_server.html')
