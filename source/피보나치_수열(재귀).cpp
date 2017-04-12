#include <stdio.h>
int f_data[116] = { 1, 1 };
int last_pos = 1;
int f(int n)
{
	int i;
	if (f_data[n - 1] == 0)
	{
		for (i = last_pos + 1; i<n; ++i)
		{
			f_data[i] = f_data[i - 1] + f_data[i - 3];
		}
		last_pos = n - 1;
	}
	return f_data[n - 1];
}
int main() {
	int n;
	scanf("%d", &n);
	printf("%d", (f(n)));

	return 0;
}