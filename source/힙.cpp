#include<stdio.h>
#define size 10
#define left(i) (i)*2
#define right(i) (i)*2+1
int heap[size];
int compare(int a, int b, int c){
	if (a < b) a = b;
	if (a < c) a = c;
	return a;
}
void heapify(int tree[], int i){
	int left = 2 * i;
	int right = 2 * i + 1;
	tree[i] = compare(tree[i], tree[left], tree[right]);
	if (left > size || right>size) return;
}
void swap(int *a, int *b){
	int temp=*a;
	*a = *b;
	*b = temp;
	return;
}

void max_heapify(int *tree, const unsigned int i, const unsigned int heap_size){
	int l = left(i);
	int r = right(i);
	int largest;

	if (l <= heap_size && tree[l] > tree[i])
		largest = l;
	else
		largest = i;

	if (r <= heap_size && tree[r] > tree[largest])
		largest = r;

	if (largest != i){
		swap(&tree[i], &tree[largest]);
		max_heapify(tree, largest, heap_size);
	}
}
void buildheap(int heap[],int heap_size){
	for (int j = heap_size / 2; j > 0; j--)
		max_heapify(heap, j, heap_size);
}

void heapsort(int *tree, unsigned int heap_size){
	int i;
	buildheap(tree, heap_size);

	for (i = heap_size; i > 0; --i){
		swap(&tree[1], &tree[i]);
		max_heapify(tree, 1, --heap_size);
	}
}

int main(){
	for (int i = 1; i <= size; i++){
		scanf("%d", &heap[i]);
	}
	heapsort(heap, size);
	for (int i = 1; i <= size; i++){
		printf("%d ", heap[i]);
	}
	return 0;
}
