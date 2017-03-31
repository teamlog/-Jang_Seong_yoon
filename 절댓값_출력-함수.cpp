#include <stdio.h>

double abv(double n) {
	if (n < 0) {
		n =(n*(-2))+n;
	}
	return n;
}
int main() {
	double a;
	scanf("%lf", &a);
	printf("%.15g", abv(a));

	return 0;
}
