#include <stdio.h>

int main() {
	int arr[50][50] = {0,};
	int n;
	int x=-1, y=0,z=1;
	int i = 0;
	int a=1;
	int cnt=3;

	scanf("%d", &n);
	int max = n;

	while (max > 0) {
		if (cnt % 2 == 1) z = 1;
		else if(cnt%2==0) z = -1;
		for (i=0; i < max; i++) {
			x += z;
			arr[x][y] = a;
			a++;		
		}
		max--;
		for (i=0; i < max; i++) {
			y += z;
			arr[x][y] = a;
			a++;
		}
		cnt++;
	}

	
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			printf("%d ", arr[i][j]);
		}
		printf("\n");
	}
	return 0;
}
