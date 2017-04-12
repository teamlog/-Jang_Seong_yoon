#include <stdio.h>
int main() {
	int cnt = 0;
	int N, nodenum = 0, temp = 0;
	int a[50000], b[50000];
	scanf("%d", &N);
	for (int i = 0; i < N; i++) {
		scanf("%d %d", &a[i], &b[i]);
	}
	while (nodenum!=N) {
		temp = cnt % (a[nodenum] + b[nodenum]);
		if (cnt < (a[nodenum] + b[nodenum])) {
			if (cnt < b[nodenum]) {
				int a = cnt;
				for (int i = 0; i < (b[nodenum] - a); i++) {
					cnt++;
				}
			}
		}
		else if ((0<temp) && (temp < b[nodenum])) {
			if (temp!=0) {
				for (int i = 0; i < (b[nodenum] - temp); i++) {
					cnt++;
				}
			}
		}
		else if (cnt >= (a[nodenum] + b[nodenum])) {
			if (temp == 0) {
				for (int i = 0; i < b[nodenum]; i++) {
					cnt++;
				}
			}
		}
		nodenum++;
		cnt++;
	}
	printf("%d", cnt);
	return 0;
}