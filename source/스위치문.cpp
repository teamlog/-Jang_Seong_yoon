#include <stdio.h>
int main() {
	int a;
	printf("학과코드(1-4)를 입력하시오->");
	scanf("%d", &a);
	switch (a) {
	case 1:printf("정보통신과\n"); break;
	case 2:printf("소프트웨어과\n"); break;
	case 3:printf("테크노경영과\n"); break;
	case 4:printf("멀티미디어과\n"); break;
	default:printf("해당하는과가 없습니다\n"); break;
	}

	return 0;
}