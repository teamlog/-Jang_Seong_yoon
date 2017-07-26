#include<stdio.h>
#include<string.h>
#define size 100000
#define dec(x) (x)<='9'&&(x)>'0'
	int stack[size] = { 0, };
	int top = -1;
	int pop(){
		if (top == -1) return -1;
		int value = stack[top];
		stack[top] =0;
		top--;
		return value;
	}
	int push(int value){
		if (top + 1 > size) return -1;
		top++;
		stack[top] = value;
		return 0;
	}
	int peek(){
		if (top == -1) return -1;
		int value = stack[top];
		return value;
	}
char iron[100000];
int main(){
	char calc[1000];
	int i;
	scanf("%s", calc);
	for (i = 0; i < strlen(calc); i++){
		if (dec(calc[i])){
			push(calc[i]-'0');
		}
		else {
			switch (calc[i]){
				case '+':push(pop() + pop()); break;
				case '*':push(pop() * pop()); break;
				case '-':push(-1 * pop() + pop()); break;
				case '/':int temp = pop(); push(pop()/temp); break;
			}
		}
	}
	printf("%d", peek());
	return 0;
}