#include <stdio.h>

int numarr(int num) {
	if (num == 0) return 0;
	else if (num == 1)return 1;
	return numarr(num - 1) + numarr(num - 2);
}
int main() {
	int n;
	scanf("%d", &n);
	printf("%d", (numarr(n)));

	return 0;
}