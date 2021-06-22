#include <iostream>
#include <stdio.h>

#include <opencv2/objdetect/objdetect.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/features2d.hpp>

using namespace std;
using namespace cv;


int iLowH = 0;
int iHighH = 255;

int iLowS = 0; 
int iHighS = 255;

int iLowV = 0;
int iHighV = 255;

int contrast = 2;
int brightness = 20;

vector<KeyPoint> blobs;

Mat process(Mat frameMat) {
    Mat resizeMat;
    resize(frameMat, resizeMat, Size(640, 480));

    Mat blurMat;
    boxFilter(resizeMat, blurMat, -1, Size(20, 20));

    Mat brightenMat;
    blurMat.convertTo(brightenMat, -1, 1, brightness - 50);

    Mat contrastMat;
    brightenMat.convertTo(contrastMat, -1, contrast, 0);

    Mat thresholdMat;
    inRange(contrastMat, Scalar(iLowH, iLowS, iLowV), Scalar(iHighH, iHighS, iHighV), thresholdMat);
    cvtColor(thresholdMat, thresholdMat, COLOR_GRAY2BGR);
    
    Mat maskMat;
    bitwise_and(contrastMat, thresholdMat, maskMat);


    return maskMat;
}

void detect(Mat frame) {

    SimpleBlobDetector::Params params;

    // Threshold, (write what this does)
    params.thresholdStep = 10;
    params.minThreshold = 10;
    params.maxThreshold = 220;

    // Minimum repeatability, (write what this does)
    params.minRepeatability = 2;

    // Minimum distance between blobs (in pixels?)
    params.minDistBetweenBlobs = 10;

    // Colour filtering (write what this does)
    params.filterByColor = false;
    params.blobColor = 0;

    // Area filtering (write what this does)
    params.filterByArea = false;
    params.minArea = 25;
    params.maxArea = 5000;

    // Circularity filtering (write what this does)
    params.filterByCircularity = true;
    params.minCircularity = 0.9f;
    params.maxCircularity = (float)1e37;

    // Inertia filtering (write what this does)
    params.filterByInertia = false;
    params.minInertiaRatio = 0.1f;
    params.maxInertiaRatio = (float)1e37;

    // Convexity filtering (write what this does)
    params.filterByConvexity = false;
    params.minConvexity = 0.95f;
    params.maxConvexity = (float)1e37;

    
    Ptr<SimpleBlobDetector> detector = SimpleBlobDetector::create(params);
	detector->detect(frame, blobs);
}

int main(int, char**) {

    std::cout << getBuildInformation() << std::endl;
    

    VideoCapture camera(0);
    camera.set(CAP_PROP_FPS, 30);
    camera.set(CAP_PROP_AUTO_WB, true);

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

    createTrackbar("Brightness", "Control", &brightness, 100);
    createTrackbar("Contrast", "Control", &contrast, 5);
    

    Mat frame;

    while (true)
    {

        camera >> frame;
        Mat output = process(frame);

        detect(output);
        drawKeypoints(output, blobs, output);
        
        imshow("Webcam", output);

        if (waitKey(10) >= 0)
            break;
    }
    
    camera.release();

}


