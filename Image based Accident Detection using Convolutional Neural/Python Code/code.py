from ctypes import *
import math
import random
import os
import cv2
import numpy as np
import time
import threading
import darknet
from itertools import combinations
from time import sleep
import tensorflow as tf
from tensorflow.python.keras.preprocessing.image import img_to_array
import pygame
#from keras.applications.inception_v3 import preprocess_input

def is_close(p1, p2):
    """
    #================================================================
    # 1. 차량 중심점 간의 유클리드 거리 계산
    #================================================================
    :param:
    p1, p2 = 중심점 p1과 p2

    :return:
    dst = 계산한 거리
    """
    dst = math.sqrt(p1**2 + p2**2)
    #=================================================================#
    return dst


def convertBack(x, y, w, h):
    """
    ================================================================
     2. 중심 감지 좌표를 직사각형 좌표로 변환 < 이건 왜 하는지 모르겠음.
    ================================================================
    :param:
    x, y = 박스의 중간점.
    w, h = 박스의 너비와 높이.

    :return:
    xmin, ymin, xmax, ymax
    """
    xmin = int(round(x - (w / 2)))
    xmax = int(round(x + (w / 2)))
    ymin = int(round(y - (h / 2)))
    ymax = int(round(y + (h / 2)))
    return xmin, ymin, xmax, ymax


def cvDrawBoxes(detections, img):
    """
    :param:
    detections = 1 프레임 당 감지된 모든 것
    img = 다크넷을 통해 가져온 이미지

    :return:
    img with bbox
    """
    #================================================================
    # 3.1 탐지를 통해 객체 클래스를 필터링하고 각 객체에 대한 경계 상자 중심을 가져옴.
    #================================================================
    if len(detections) > 0:  						# 탐지된 객체가 1개라도 있다면
        centroid_dict = dict() 						# 중심점을 저장할 빈 딕셔너리 생성
        centroid_dict2 = dict() 						# 중심점을 저장할 빈 딕셔너리 생성
        roi_dict = dict()
        global centroid_list
        global before_centroid_list
        global sec
        global tm
        global model1
        car_roi=img.copy()

        objectId = 0	# 딕셔너리 키 값, 오브젝트 아이디

        for label, confidence, bbox in detections: # detections == { label, confidence, bbox=[] }
            name_tag =label
            if name_tag=='person':
                x, y, w, h = (bbox[0],
                     bbox[1],
                     bbox[2],
                     bbox[3])
                xmin, ymin, xmax, ymax = convertBack(float(x), float(y), float(w), float(h))   # 중앙 좌표에서 직사각형 좌표로 변환
                centroid_dict2[objectId] = (int(x), int(y), xmin, ymin, xmax, ymax) #int(x),int(y)=중심점이다. 해당 라인에서 초기화.  (746, 190, 616, 88, 877, 293)
                objectId += 1
                pt1 = (xmin, ymin)
                pt2 = (xmax, ymax)

                # 감지된 객체 판단 및 정확도 표시
                cv2.putText(img,"["+str(objectId)+"]"+
                            label+
                            " [" + str(confidence) + "%]",
                            (pt1[0], pt1[1] - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5,
                            (246,86,86), 2)

                for idx, box in centroid_dict2.items():
                    cv2.rectangle(img, (box[2], box[3]), (box[4], box[5]), (255, 0, 0), 2) # 빨간색 경계사각형
                    location = (150,55)


            if name_tag == 'car' or name_tag =='truck' or name_tag=='bicycle' or name_tag=='motorbike':
                x, y, w, h = (bbox[0],
                     bbox[1],
                     bbox[2],
                     bbox[3])
                xmin, ymin, xmax, ymax = convertBack(float(x), float(y), float(w), float(h))   # 중앙 좌표에서 직사각형 좌표로 변환

                # 5초마다 중심점 복사
                if sec%5==0:
                    before_centroid_list=centroid_list.copy()

                # 경계 사각형에 중심점 추가.
                centroid_dict[objectId] = (int(x), int(y), xmin, ymin, xmax, ymax) #int(x),int(y)=중심점이다. 해당 라인에서 초기화.  (746, 190, 616, 88, 877, 293)
                centroid_list[objectId] = ((int(x),int(y)))
                roi_dict[objectId]=(int(y), int(y+h), int(x), int(x+w)) # y1 , y2 , x1, x2  (190, 394, 746, 1007)

                objectId += 1
                pt1 = (xmin, ymin)
                pt2 = (xmax, ymax)

                # 감지된 객체 판단 및 정확도 표시
                cv2.putText(img,"["+str(objectId)+"]"+
                            label+
                            " [" + str(confidence) + "%]",
                            (pt1[0], pt1[1] - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5,
                            (246,86,86), 2)

                # 중심점을 지속적으로 찍음
                cv2.line(img, tuple(((int(x),int(y)))), tuple(((int(x),int(y)))), (255, 0, 0), 5)

                # 5초 전 중심점을 찍고, 임계값 사이에 있으면 스톱 텍스트 표시
                for i in range(len(before_centroid_list)-1):
                    cv2.line(img, before_centroid_list[i], before_centroid_list[i], (0, 0, 255), 5)
                    if tuple(((int(x-1),int(y-1)))) <= before_centroid_list[i] and tuple(((int(x+1),int(y+1)))) >= before_centroid_list[i]:
                        #시간쪽
                        #if sec%5==0:
                        cv2.putText(img,"Stop!", before_centroid_list[i] , cv2.FONT_HERSHEY_SIMPLEX, 1, (255,255,255), 1)
                        today=str(tm.tm_year)+"."+str(tm.tm_mon)+"."+str(tm.tm_mday)+"-"+str(tm.tm_hour-3)+" "+str(tm.tm_min)+" "+str(tm.tm_sec)

                        #print(roi_dict)
                        #print(centroid_dict)

                        #차량 ROI
                        for box in roi_dict.items():
                            car_roi2=car_roi.copy()
                            car_roi2=cv2.cvtColor(car_roi2, cv2.COLOR_BGR2RGB)
                            # (box[2], box[3]), (box[4], box[5]), (0, 255, 0), 2
                            car_roi2=car_roi2[int(y-h/2):int(y+h/2), int(x-w/2):int(x+w/2)] # y1:y2 , x1:x2

                            cv2.imwrite("./accident/collision/collision%s.png" % today, car_roi2)
                            file_path="./accident/collision/collision"+str(today)+".png"
                            run_predict1(file_path,model1,"stopped",objectId,today)


    #=================================================================#

    #=================================================================
    # 3.2 교통 정체 확인
    #=================================================================
        red_zone_list = [] # 거리 조건에 맞지 않은 개체 ID를 포함하는 리스트
        red_line_list = []
        for (id1, p1), (id2, p2) in combinations(centroid_dict.items(), 2): # 인접한 중심점의 위치에 대한 모든 조합을 얻음.
            dx, dy = p1[0] - p2[0], p1[1] - p2[1]  	# 중심점 사이의 차이를 확인함  x: 0, y :1
            distance = is_close(dx, dy) 			# 유클리드 거리 계산
            if distance>10 and distance <100:		# 교통 정체 확인.
                if id1 not in red_zone_list:
                    red_zone_list.append(id1)
                    red_line_list.append(p1[0:2])
                if id2 not in red_zone_list:
                    red_zone_list.append(id2)
                    red_line_list.append(p2[0:2])

        for idx, box in centroid_dict.items():  # dict (1(key):red(value), 2 blue)  idx - key  box - value
            if idx in red_zone_list:   # id가 레드존 목록에 있는 경우
                cv2.rectangle(img, (box[2], box[3]), (box[4], box[5]), (255, 0, 0), 2) # 빨간색 경계사각형
            else:
                cv2.rectangle(img, (box[2], box[3]), (box[4], box[5]), (0, 255, 0), 2) # 초록색 경계사각형
		#=================================================================#



		#=================================================================
    	# 3.3 위험분석 및 위험지표 표시 <> 가까움 정도 표시
    	#=================================================================
        text = "Traffic: %s" % str(len(red_zone_list)) 			# Count People at Risk
        location = (10,25)
        if len(red_zone_list)<3:
            cv2.putText(img, text, location, cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)  # Display Text
        else:
            cv2.putText(img, text, location, cv2.FONT_HERSHEY_SIMPLEX, 1, (255,0,0), 2, cv2.LINE_AA)  # Display Text

        for check in range(0, len(red_line_list)-1):					# 빨간색 목록 항목을 반복하여 근처의 상자 사이에 선을 그림.
            start_point = red_line_list[check]
            end_point = red_line_list[check+1]
            check_line_x = abs(end_point[0] - start_point[0])   		# x에 대한 선 좌표 계산
            check_line_y = abs(end_point[1] - start_point[1])			# y에 대한 선 좌표 계산
            if (check_line_x < 100) and (check_line_y < 100):			# 위에서 지정한 임계값보다 작을경우
                cv2.line(img, start_point, end_point, (255, 0, 0), 2)   # 선을 표시함.
                cv2.putText(img,str(check_line_y), (start_point), cv2.FONT_HERSHEY_SIMPLEX, 0.3, (246,86,86), 1)
        #=================================================================#


    return img


netMain = None
metaMain = None
altNames = None


def run_predict1(file_path,model,fom,objectId,today):
    image = cv2.imread(file_path)
    image = cv2.resize(image,dsize=(224,224))
    fimage=image
    img = image / 255.
    image = np.expand_dims(img, axis=0)
    # image = img_to_array(image)
    # image = image.reshape((1, image.shape[0],image.shape[1],image.shape[2]))

    #image = preprocess_input(image)
    yhat = model.predict(image)
    print("-----------------------> "+"accident:"+fom)
    print("-----------------------> objectid:"+str(objectId))
    print("accident_accuray : ",yhat[0][0])
    print("normal_accuray : ",yhat[0][1])
    #print("fire_accuray : ",yhat[0][2])

    inID = np.argmax(yhat[0])
    if(inID==0):
        file_path2="./accident_discrimination/collision/"+str(today)+" collision"+"-"+str(round(yhat[0][0],2))+".jpg"
        cv2.imwrite(file_path2, fimage)
    elif (inID==1):
        file_path2="./accident_discrimination/normal/"+str(today)+" normal"+"-"+str(round(yhat[0][1],2))+".jpg"
        cv2.imwrite(file_path2, fimage)
    # elif (inID==2):
    #     file_path2="./accident_discrimination/fire/"+str(today)+" fire"+"-"+str(round(yhat[0][2],2))+".jpg"
    #     cv2.imwrite(file_path2, fimage)
    # file_path="./accident/fire/fire"+str(today)+".png"
    # cv2.imwrite(, image)

    #print("sparse_traffic_accuray : ",yhat[0][3])

def run_predict2(file_path,model,fom,objectId,today):
    image = cv2.imread(file_path)
    image = cv2.resize(image,dsize=(224,224))
    fimage=image
    img = image / 255.
    image = np.expand_dims(img, axis=0)
    # image = img_to_array(image)
    # image = image.reshape((1, image.shape[0],image.shape[1],image.shape[2]))

    #image = preprocess_input(image)
    yhat = model.predict(image)
    print("-----------------------> "+"accident:"+fom)
    print("-----------------------> objectid:"+str(objectId))
    print("fire_accuray : ",yhat[0][0])
    print("normal_accuray : ",yhat[0][1])

    inID = np.argmax(yhat[0])
    if(inID==0):
        file_path2="./fire_discrimination/fire/"+str(today)+" fire"+"-"+str(round(yhat[0][0],2))+".jpg"
        cv2.imwrite(file_path2, fimage)
    elif (inID==1):
        file_path2="./accident_discrimination/normal/"+str(today)+" normal"+"-"+str(round(yhat[0][1],2))+".jpg"
        cv2.imwrite(file_path2, fimage)


class_dictionary = {}
class_dictionary[0] = 'not a fire'
class_dictionary[1] = 'not a fire'
class_dictionary[2] = 'fire'

def make_prediction(image, model, class_dictionary):
    image = cv2.resize(image, (224, 224))
    img = image / 255.

    # convert to 4D tensor
    image = np.expand_dims(img, axis=0)

    # train
    class_predicted = model.predict(image)
    inID = np.argmax(class_predicted[0])
    label = class_dictionary[inID]
    return label

def nothing(x):
    #any operation
    pass

def YOLO():
    """
    Perform Object detection
    """
    global metaMain, netMain, altNames
    global centroid_list
    global before_centroid_list
    global sec
    global tm
    global model1

    configPath = "./cfg/yolov4-tiny.cfg"
    weightPath = "./yolov4-tiny.weights"
    metaPath = "./cfg/coco.data"

    # model_load
    model1 = tf.keras.models.load_model('model/tunnel_VGG16model2.h5', compile=False)
    model2 = tf.keras.models.load_model('model/tunnel_VGG16model3.h5', compile=False)

    #새로 추가함 .
    network, class_names, class_colors = darknet.load_network(configPath,  metaPath, weightPath, batch_size=1)

    #cap = cv2.VideoCapture(0)
    cap = cv2.VideoCapture("./test/test41.mp4")
    frame_width = int(cap.get(3))
    frame_height = int(cap.get(4))
    #new_height, new_width = frame_height // 2, frame_width // 2
    new_height, new_width = frame_height //2 , frame_width //2
    # print("Video Reolution: ",(width, height))

    # print("Starting the YOLO loop...")

    # Create an image we reuse for each detect
    darknet_image = darknet.make_image(new_width, new_height, 3)

    centroid_list = dict()
    before_centroid_list=dict()

    cv2.namedWindow("Trackbars")
    cv2.createTrackbar("L-H", "Trackbars", 20, 255, nothing)
    cv2.createTrackbar("L-S", "Trackbars", 12, 255, nothing)
    cv2.createTrackbar("L-V", "Trackbars", 255, 255, nothing)
    cv2.createTrackbar("U-H", "Trackbars", 180, 180, nothing)
    cv2.createTrackbar("U-S", "Trackbars", 30, 255, nothing)
    cv2.createTrackbar("U-V", "Trackbars", 255, 255, nothing)


    while True:
        l_h = cv2.getTrackbarPos("L-H", "Trackbars")
        l_s = cv2.getTrackbarPos("L-S", "Trackbars")
        l_v = cv2.getTrackbarPos("L-V", "Trackbars")
        U_h = cv2.getTrackbarPos("U-H", "Trackbars")
        U_s = cv2.getTrackbarPos("U-S", "Trackbars")
        U_v = cv2.getTrackbarPos("U-V", "Trackbars")



        # 시간 쪽
        tm=time.gmtime(time.time())
        sec=tm.tm_sec
        prev_time = time.time()
        ret, frame_read = cap.read()
        # Check if frame present :: 'ret' returns True if frame present, otherwise break the loop.
        if not ret:
            break

        frame_rgb = cv2.cvtColor(frame_read, cv2.COLOR_BGR2RGB)
        frame_resized = cv2.resize(frame_rgb,
                                   (new_width, new_height),
                                   interpolation=cv2.INTER_LINEAR)

        darknet.copy_image_from_bytes(darknet_image,frame_resized.tobytes())

        #detections = darknet.detect_image(netMain, metaMain, darknet_image, thresh=0.25)
        detections = darknet.detect_image(network, class_names, darknet_image, thresh=0.25)
        image = cvDrawBoxes(detections, frame_resized)
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        #print(1/(time.time()-prev_time))

        #화염감지
        _, frame = cap.read()
        frame = cv2.resize(frame, (800, 600))
        frame = cv2.GaussianBlur(frame, (3, 3), 1)
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    	# Mask making
        l_m = np.array([l_h, l_s, l_v])
        u_m = np.array([U_h, U_s, U_v])
        #l_m = np.array([20, 10, 255])
        #u_m = np.array([180, 30, 255])
        mask = cv2.inRange(hsv, l_m, u_m)


    	# Image morphology operation
        kernel1 = np.ones((15, 15), np.uint8)
        mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernel1)
        cv2.imshow("img2", mask)

        res = cv2.bitwise_and(frame, frame, mask=mask)
        cv2.imshow("aa",res)

        img = frame.copy()
        ret, thresh = cv2.threshold(mask, 0, 255, cv2.THRESH_BINARY)
        contours, hierarchy = cv2.findContours(
        	thresh,
        	cv2.RETR_EXTERNAL,
        	cv2.CHAIN_APPROX_NONE
        )
        for cnt in contours:
            l = cv2.arcLength(cnt, True)
            if l > 500:
                x, y, w, h = cv2.boundingRect(cnt)

    			# CNN data input
                img_test = frame[y:y + h, x:x + w]
                #img_test = cv2.resize(img_test, (224, 224))
                today=str(tm.tm_year)+"."+str(tm.tm_mon)+"."+str(tm.tm_mday)+"-"+str(tm.tm_hour)+" "+str(tm.tm_min)+" "+str(tm.tm_sec)
                cv2.imwrite("./accident/fire/fire%s.png" % today, img_test)
                file_path="./accident/fire/fire"+str(today)+".png"
                run_predict2(file_path,model2,"firedetected",-1,today)
                label = make_prediction(img_test, model2, class_dictionary)
                if label == 'fire':
                    img = cv2.rectangle(
    	                img,
    	                (x, y),
    	                (x + w, y + h),
    	                (0, 0, 255), 2)
                    cv2.putText(
                        img,
                        "Fire", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX,
                        0.7, (0, 0, 255), 2)
                    cv2.imshow("img33", img)

        cv2.imshow("img", img)
        #a=cv2.resize(image,(1200,800))
        cv2.imshow('Demo2', image)
        cv2.waitKey(3)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    out.release()
    print(":::Video Write Completed")

if __name__ == "__main__":



    YOLO()
