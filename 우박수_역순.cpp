#include <stdio.h>


void ubs(int n){
	if (n == 1) {
		return;
	}
		else if (n % 2 == 1) {
			ubs(3 * n + 1);
		}
		else {
			ubs(n / 2);
		}
		printf("%d\n", n);
	
}
int main() {
	int num;
	scanf("%d", &num);

	printf("1\n");
	ubs(num);
	return 0;
}