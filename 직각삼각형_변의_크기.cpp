#include <stdio.h>

double square_root(double num);
int main()
{
	int a[100], b[100], c[100];
	int i=0;
	double num;
	while (1) {
		scanf("%d %d %d", &a[i], &b[i], &c[i]);
		if (a[i] == 0 && b[i] == 0 && c[i] == 0) {
			break;
		}
		i++;
	}

	for (int j=0;j<i;j++){
		if (a[j] == -1) {
			if (!(b[j] > c[j])) {
				a[j] = c[j] * c[j] - b[j] * b[j];
				num = square_root(a[j]);

				printf("Triangle #%d\n", j+1);
				printf("a = %.3f\n", num);
			}
			else if (b[j] > c[j]) {
				printf("Triangle #%d\n", j + 1);
				printf("Impossible.\n");
			}

		}
		else if (b[j] == -1) {
			if (!(a[j]>c[j])) {
				b[j] = c[j] *c[j] - a[j] *a[j];
				num = square_root(b[j]);

				printf("Triangle #%d\n", j+1);
				printf("b = %.3f\n", num);
			}
			else if(a[j]>c[j]) {
				printf("Triangle #%d\n", j + 1);
				printf("Impossible.\n");
            }
		}
		else if (c[j] == -1) {
			c[j] = a[j] *a[j] + b[j] *b[j];
			num = square_root(c[j]);

			printf("Triangle #%d\n", j+1);
			printf("c = %.3f\n", num);
		}
		else if (a[j] == 0 || b[j] == 0 || c[j] == 0) {
			printf("Triangle #%d\n", j + 1);
			printf("Impossible.\n");
		}
		printf("\n");
	}
		return 0;
}
double square_root(double num) {
	double next;
	double temp;

	next = 0.5*(1 + (num / 1));
	while (1) {
		temp = next;
		next = 0.5*(next + (num / next));
		if (temp - next < 0.005 || temp - next < 0.005) {
			break;
		}
	}
	return next;
}
