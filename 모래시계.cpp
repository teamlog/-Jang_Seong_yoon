#include <stdio.h>

int main() {
	int n;

	scanf("%d", &n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (i == n - 1)printf("");
			else if (j < i) {
				printf(" ");
			}
			else printf("* ");
		}
		if (i == n - 1)printf("");
		else printf("\n");
	}
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= n; j++) {
			if (j <= n - i) {
				printf(" ");
			}
			else printf("* ");
		}
		printf("\n");
	}
	

	return 0;
}