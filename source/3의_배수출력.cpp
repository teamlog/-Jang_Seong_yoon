#include <stdio.h>
int main() {
	int sum=0;
	for (int i = 1; i <= 20; i++) {
		if (i % 3 == 0) {
			printf("%d ", i);
			sum += i;
		}
	}
	printf("\n%d", sum);
	return 0;
}