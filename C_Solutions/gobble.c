#include <stdio.h>

int max(int,int);

//C version
int main() {

  int rows, cols, n, lj, rj, maxval;

  scanf("%d%d",&rows,&cols);
  
  int maxvals[rows][cols * 2];
  
  for(int j = 0; j < cols; j++) {
    scanf("%d",&n);
    maxvals[0][j] = n;
    maxvals[0][j + cols] = -1 * n;
  }

  
  for(int i = 1; i < rows; i++){
    for(int j = 0; j < cols; j++) {
      scanf("%d",&n);
      lj = j == 0 ? cols - 1 : j - 1;
      rj = j == cols - 1 ? 0 : j + 1;

      maxval = max(maxvals[i-1][lj + cols] , max(maxvals[i-1][j] , maxvals[i-1][rj+cols]));
      maxvals[i][j] = maxval + n;
      maxvals[i][j+cols] = maxval - n;
      
    }
  }

    maxval = 0;
    for(int j = 0; j < cols; j++){
      maxval = maxvals[rows - 1][j] > maxval ? maxvals[rows - 1][j] : maxval;
    }
    
    printf("%d\n",maxval);
}


int max (int a, int b){

  return a > b ? a : b;

}
