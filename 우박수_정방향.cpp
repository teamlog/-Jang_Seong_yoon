#include <stdio.h>


void ubs(int n){
	if (n == 1) {
		return;
	}
		else if (n % 2 == 1) {
			printf("%d\n", 3 * n + 1);
			ubs(3 * n + 1);
		}
		else {
			printf("%d\n", n / 2);
			ubs(n / 2);
		}
	
}
int main() {
	int num;
	scanf("%d", &num);

	printf("%d\n",num);
	ubs(num);
	return 0;
}