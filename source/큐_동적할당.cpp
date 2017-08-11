#include<stdio.h>
#include<stdlib.h>
struct node{
	int value;
	struct node* next;
};
struct node* head= NULL;
struct node* tail= NULL;
void enqueue(const int n){
	struct node* ptr = (struct node*)malloc(sizeof(struct node));
	*ptr = { n, NULL };
	if (head!= NULL) (*head).next = ptr;
	head = ptr;
	if (tail == NULL){
		tail = head;
	}
}
struct node dequeue(){
	struct node value = *tail;
	free(tail);
	tail = value.next;
	return value;
}
int main(){
	int n,v;
	scanf("%d", &n);
	for (int i = 0; i < n; i++){
		scanf("%d", &v);
		enqueue(v);
	}
	for (int i = 0; i < n; i++){
		printf("%d", dequeue().value);
	}
	return 0;
}