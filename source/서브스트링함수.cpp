#include <stdio.h>

void substr(char *str, int start,int count);
int main() {
	char str[100];
	int start, count;
	scanf("%s", str);
	scanf("%d %d", &start, &count);
	substr(&str[0], start, count);
	return 0;
}
void substr(char *str, int start, int count) {
	char fstr[100];
	int a = 0;
	for (int i = start; i < count+start; i++) {
		fstr[a]= *(str+i);
		a++;
	}
	fstr[a] = '\0';
	printf("%s", fstr);
}