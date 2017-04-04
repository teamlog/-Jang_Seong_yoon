#include <stdio.h>
int main() {
	int pann[19][19] = { 0, };
	int x, y;
	int n;

	scanf("%d", &n);
	for (int i = 0; i < n; i++) {
		scanf("%d %d", &x, &y);
		pann[x-1][y-1] = 1;
	}
	for(int i = 0; i < 19; i++) {
		for (int j = 0; j < 19; j++) {
			printf("%d ", pann[i][j]);
		}
		printf("\n");
	}

	return 0;
}
