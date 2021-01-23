#include <iostream>
#include <stdio.h>

#include <opencv2/objdetect/objdetect.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/features2d.hpp>

using namespace std;
using namespace cv;


int iLowH = 38;
int iHighH = 77;

int iLowS = 83; 
int iHighS = 137;

int iLowV = 83;
int iHighV = 145;

vector<KeyPoint> blobs;

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

        detect(output);
        drawKeypoints(output, blobs, output);
        
        imshow("Webcam", output);

        if (waitKey(10) >= 0)
            break;
    }
    
    camera.release();

}


