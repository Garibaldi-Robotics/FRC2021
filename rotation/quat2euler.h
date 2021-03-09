#include <math.h>

typedef struct Quaternion {
	double a, i, j, k;
} Quaternion;

typedef struct EulerAngles {
	double roll, pitch, yaw;
} EulerAngles;

EulerAngles ToEulerAngles(Quaternion q) {
	EulerAngles angles;

	//roll (x-axis rotation)
	double sinr_cosp = 2 * (q.a * q.i + q.j * q.k);
	double cosr_cosp = 1 - 2 * (q.i * q.i + q.j * q.j);
	angles.roll = atan2(sinr_cosp, cosr_cosp);

	//pitch (y-axis rotation)
	double simp = 2 * (q.a * q.j - q.k * q.i);
	if (abs(simp) >= 1)
		angles.pitch = copysign(PI / 2, simp);
	else
		angles.pitch = asin(simp);

	//yaw (z-axis rotation)
	double siny_cosp = 2 * (q.a * q.k + q.i * q.j);
	double cosy_cosp = 1 - 2 * (q.j * q.j + q.k * q.k);
	angles.yaw = atan2(siny_cosp, cosy_cosp);

	return angles;
}
	