#include<stdio.h>
#define size 10
int queue[size];
int top=-1;
int tail = -1;
void enqueue(int value){
	top++;
	if (top == size) top = 0;
	queue[top] = value;
	return;
}
int dequeue(){
	tail++;
	if (tail == size) tail = 0;
	int value = queue[tail];
	return value;
}
int main(){
	int a,c;
	scanf("%d", &c);
	for (int i = 0; i < c; ++i){
		scanf("%d", &a);
		enqueue(a);
		printf("%d ", dequeue());
	}

	return 0;
} 