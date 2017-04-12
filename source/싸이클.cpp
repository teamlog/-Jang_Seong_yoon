#include <stdio.h>
int main() {
	int n,cp=0,cnt=0,temp;
	scanf("%d", &n);
	cp = n;
	do {
		temp = (cp / 10) + (cp % 10);
		cp=(10 * (cp % 10)) + (temp%10);
		cnt++;
	} while (n != cp);
	printf("%d", cnt);
	return 0;
}