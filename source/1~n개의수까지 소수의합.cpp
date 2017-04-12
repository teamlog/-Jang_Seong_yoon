#include <stdio.h>
int main() {
	int n;
	int sum = 0;
	int con = 0;
	scanf("%d", &n);
	for (int i = 0; i <= n; i++) {
		con = 0;
		for (int j = 1; j <= i; j++) {
			if (i%j == 0) {
				con += 1;
			}
			if (j == i - 1) {
				if (con == 1) {
					sum += i;
				}
			}
		}
	}
	printf("%d", sum);
	return 0;
}