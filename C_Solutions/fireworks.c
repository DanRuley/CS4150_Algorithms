#include <stdio.h>

#define MAX 2147483647

int FindOptimal(int [], int, int, int, int);
int GreedyDistanceFinder(int [], int, int, int);


int main() {

  
  int N, s, maxdist, mindist;
  scanf("%d%d",&N,&s);

  
  int scenarios[s];
  int distances[N - 1];
  maxdist = 0;
  mindist = MAX;

  for (int i = 0; i < N - 1; i++)
    {
      scanf("%d",&distances[i]);
      maxdist += distances[i];
      mindist = distances[i] < mindist ? distances[i] : mindist;
    }
  
  for (int i = 0; i < s; i++)
    scanf("%d",&scenarios[i]);
  
  for (int i = 0; i < s; i++)
    {
      int result = FindOptimal(distances, mindist, maxdist, scenarios[i], N);
      printf("%d\n",result);
    }
}

int FindOptimal(int d[], int mindist, int maxdist, int nlaunch, int N)
{
  if(nlaunch == 2)
    return maxdist;
  
  else if(nlaunch == N)
    return mindist;
  
  int low = mindist;
  int hi = maxdist;
  int maxmin = 0;
  int mid;
  int val;

  while (low <= hi)
    {
      mid = (low + hi) / 2;

      val = GreedyDistanceFinder(d, nlaunch, mid, N);
      maxmin = val > maxmin && val != MAX? val : maxmin;

      //Not possible to space n launchers at least mid dist apart.
      if (val == MAX)
	    hi = mid - 1;
                
      else
	    low = mid + 1;
    }

  return maxmin;
}


int GreedyDistanceFinder(int d[], int nlaunch, int spacing, int N)
{

  int running_sum = 0;
  int launchers_used = 1;
  int min_dist = MAX;
  for (int i = 0; i < N - 1; i++)
    {
      running_sum += d[i];
      if (running_sum >= spacing)
	{
	  min_dist = running_sum < min_dist ? running_sum : min_dist;
 	  running_sum = 0;
	  launchers_used++;
	  if (launchers_used + N - 1 - i < nlaunch)
	    return MAX;
	}
    }

  if (launchers_used < nlaunch)
    return MAX;
  else
    return min_dist;
}

