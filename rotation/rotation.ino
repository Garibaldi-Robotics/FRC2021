// Basic demo for readings from Adafruit BNO08x
#include <Adafruit_BNO08x.h>
#include "quat2euler.h"
#include <math.h>
#include <Arduino.h>
#include <Wire.h>

Adafruit_BNO08x bno08x(-1);
sh2_SensorValue_t sensorValue;

void setup(void) {
  Serial.begin(115200);


  if (!bno08x.begin_I2C()) {
    Serial.println("Failed to find BNO08x chip");
    while (1) delay(10);
  }
  Serial.println("BNO08x Found!");

  setSensor();

  delay(100);
}


EulerAngles angles = {1, 1, 1};

void loop() {
  delay(10);

  if (bno08x.wasReset()) {
    setSensor();
  }

  if (!bno08x.getSensorEvent(&sensorValue)) {
    return;
  }
  switch (sensorValue.sensorId) {
    case SH2_ROTATION_VECTOR:
      getRotation();
      break;
    default:
      Serial.println("None");
      break;
  }

  //if (Serial.available())
  //  if (Serial.readString() == "getData")
  //      sendData();

  sendData();

}

void getRotation() {
  Quaternion quaternion;

  quaternion.a = sensorValue.un.rotationVector.real;
  quaternion.i = sensorValue.un.rotationVector.i;
  quaternion.j = sensorValue.un.rotationVector.j;
  quaternion.k = sensorValue.un.rotationVector.k;

  angles = ToEulerAngles(quaternion);
  ToDeg(&angles);
}

void sendData() {
  Serial.printf("%f,%f,%f\n", angles.roll, angles.pitch, angles.yaw);
  Serial.flush();
}

void setSensor() {
  if (!bno08x.enableReport(SH2_ROTATION_VECTOR)) {
    Serial.println("Could not enable rotation vector");
  }
}