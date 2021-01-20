#include <iostream>
#include <stdio.h>
#include <opencv2/opencv.hpp>

using namespace cv;


int iLowH = 0;
int iHighH = 255;

int iLowS = 0; 
int iHighS = 255;

int iLowV = 0;
int iHighV = 255;

Mat process(Mat frameMat) {
    Mat resizeMat;
    resize(frameMat, resizeMat, Size(640, 480));

    Mat blurMat;
    boxFilter(resizeMat, blurMat, -1, Size(20, 20));

    Mat thresholdMat;
    inRange(blurMat, Scalar(iLowH, iLowS, iLowV), Scalar(iHighH, iHighS, iHighV), thresholdMat);
    cvtColor(thresholdMat, thresholdMat, COLOR_GRAY2BGR);
    
    Mat maskMat;
    bitwise_and(blurMat, thresholdMat, maskMat);


    return maskMat;
}


int main(int, char**) {
    

    VideoCapture camera(0);

    if (!camera.isOpened()) {
        printf("Couldn't open camera");
        return 1;
    }

    namedWindow("Webcam", WINDOW_AUTOSIZE);
    namedWindow("Control", WINDOW_AUTOSIZE);


    createTrackbar("LowH", "Control", &iLowH, 255);
    createTrackbar("HighH", "Control", &iHighH, 255);

    createTrackbar("LowS", "Control", &iLowS, 255);
    createTrackbar("HighS", "Control", &iHighS, 255);

    createTrackbar("LowV", "Control", &iLowV, 255);
    createTrackbar("HighV", "Control", &iHighV, 255);

    int whitebal = 0;
    createTrackbar("WhiteBal", "Control", &whitebal, 100);
    camera.set(CAP_PROP_AUTO_WB, false);

    Mat frame;

    while (true)
    {
        camera >> frame;
        Mat output = process(frame);
        imshow("Webcam", output);

        if (waitKey(10) >= 0)
            break;
    }
    


}


