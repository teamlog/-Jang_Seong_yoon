#include <stdio.h>

void bit(int num) {
	if (num<=1) {
		printf("%d", num);
		return;
	}
	bit(num / 2);
	printf("%d", num % 2);
}
int main() {
	int n;
	scanf("%d", &n);

	bit(n);
	return 0;
}