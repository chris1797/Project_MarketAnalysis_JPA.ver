from flask_cors import CORS
from flask import Flask, request, Response, jsonify, render_template
import json
import pandas as pd
import csv
import cx_Oracle

app = Flask(__name__)

CORS(app, resources={r'*': {'origins': '*'}})

cx_Oracle.init_oracle_client(
    lib_dir=r"/Users/ijaehun/Downloads/instantclient_19_8")
connection = cx_Oracle.connect(
    memberDTO='admin', password='Chldudcjf12!', dsn='db20220709105045_low')
cursor = connection.cursor()


@app.route("/dataTable", methods=["POST"])
def connect():
    # 1. 쿼리문 넣고
    dataKind = request.form['dataKind']

    if dataKind == "sales":

        year = request.form['year']
        quarter = request.form['quarter']
        svc_name = request.form['svc_name']
        trdar_name = request.form['trdar_name']
        check_co = request.form['check_co']

        cursor.execute(
            f"select * from sales where stdr_yy_cd={str(year)} AND STDR_QU_CD={str(quarter)} AND SVC_INDUTY_CD_NM='{svc_name}' AND TRDAR_CD_NM='{trdar_name}'")

        rows = cursor.fetchall()

        colname = cursor.description
        col = []

        for i in colname:
            col.append(i[0])

        data2 = pd.DataFrame(rows, columns=col)

        # 2. 데이터 분기하고
        if check_co == '금액':
            # 금액
            data3 = data2[['STDR_YY_CD', 'STDR_QU_CD', 'TRDAR_SE_CD_NM', 'MDWK_SELNG_AMT', 'WKEND_SELNG_AMT',
                           'MON_SELNG_AMT', 'TUES_SELNG_AMT', 'WED_SELNG_AMT', 'THUR_SELNG_AMT', 'FRI_SELNG_AMT',
                           'SAT_SELNG_AMT', 'SUN_SELNG_AMT', 'TMZON_00_06_SELNG_AMT', 'TMZON_06_11_SELNG_AMT',
                           'TMZON_11_14_SELNG_AMT', 'TMZON_14_17_SELNG_AMT', 'TMZON_17_21_SELNG_AMT', 'TMZON_21_24_SELNG_AMT',
                           'ML_SELNG_AMT', 'FML_SELNG_AMT', 'AGRDE_10_SELNG_AMT', 'AGRDE_20_SELNG_AMT', 'AGRDE_30_SELNG_AMT',
                           'AGRDE_40_SELNG_AMT', 'AGRDE_50_SELNG_AMT', 'AGRDE_60_ABOVE_SELNG_AMT', 'STOR_CO']]
        elif check_co == '건수':
            # 건수
            data3 = data2[['STDR_YY_CD', 'STDR_QU_CD', 'TRDAR_SE_CD_NM', 'MDWK_SELNG_CO', 'WKEND_SELNG_CO',
                           'MON_SELNG_CO', 'TUES_SELNG_CO', 'WED_SELNG_CO', 'THUR_SELNG_CO', 'FRI_SELNG_CO',
                           'SAT_SELNG_CO', 'SUN_SELNG_CO', 'TMZON_00_06_SELNG_CO', 'TMZON_06_11_SELNG_CO',
                           'TMZON_11_14_SELNG_CO', 'TMZON_14_17_SELNG_CO', 'TMZON_17_21_SELNG_CO',
                           'TMZON_21_24_SELNG_CO', 'ML_SELNG_CO', 'FML_SELNG_CO', 'AGRDE_10_SELNG_CO',
                           'AGRDE_20_SELNG_CO', 'AGRDE_30_SELNG_CO', 'AGRDE_40_SELNG_CO', 'AGRDE_50_SELNG_CO', 'AGRDE_60_ABOVE_SELNG_CO',
                           'STOR_CO']]

        data3.index = ['rowData']
        #week_data = data3.iloc[:,5:12]

        js = data3.to_json(orient='index')  # columns도 사용가능, index, records
        # real_js=js[1:-1] records 사용시

        return js

    elif dataKind == "population":

        year = request.form['year']
        quarter = request.form['quarter']
        trdar_name = request.form['trdar_name']

        cursor.execute(
            f"select * from population where stdr_yy_cd={str(year)} AND STDR_QU_CD={str(quarter)} AND TRDAR_CD_NM='{trdar_name}'")
        rows = cursor.fetchall()
        colname = cursor.description
        col = []
        for i in colname:
            col.append(i[0])
        data2 = pd.DataFrame(rows, columns=col)
        data2.index = ['rowData']
        js = data2.to_json(orient='index')
        return js

    elif dataKind == "store":
        year = request.form['year']
        quarter = request.form['quarter']
        svc_name = request.form['svc_name']
        trdar_name = request.form['trdar_name']

        cursor.execute(
            f"select * from store where stdr_yy_cd={str(year)} AND STDR_QU_CD={str(quarter)} AND SVC_INDUTY_CD_NM='{svc_name}' AND TRDAR_CD_NM='{trdar_name}'")
        rows = cursor.fetchall()
        colname = cursor.description
        col = []
        for i in colname:
            col.append(i[0])
        data2 = pd.DataFrame(rows, columns=col)
        data2.index = ['rowData']
        js = data2.to_json(orient='index')
        return js


if __name__ == '__main__':
    app.run(debug=True)
