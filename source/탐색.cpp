#include <stdio.h>
int main(){
	int n;
	int num[10000];
	int max = 0;
	int maxn;
	int min = 32767;
	int minn;

	scanf("%d", &n);
	for (int i = 0; i < n; i++){
		scanf("%d", &num[i]);
		if (num[i] > max) {
			max = num[i];
			maxn = i;
		}
		if (num[i] < min){
			min = num[i];
			minn = i;
		}
	}
	printf("%d : %d\n", maxn+1, max);
	printf("%d : %d\n", minn+1, min);

	return 0;
}
