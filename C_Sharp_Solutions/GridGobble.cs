using System;

namespace GridGobble
{
    /// <summary>
    /// Solution to the 4150 final Kattis problem: Grid Gobble
    /// @Author: Dan Ruley
    /// </summary>
    class GridGobble
    {
        /// <summary>
        /// Main entry point.
        /// </summary>
        static void Main(string[] args)
        {
            string[] rc = Console.ReadLine().Split(" ");
            int rows = int.Parse(rc[0]);
            int cols = int.Parse(rc[1]);

            int[,] grid = new int[rows, cols];

            //Maxvals essentially holds two values for each i,j grid cell: the max coming vertically and the max when coming diagonally (factoring in the subtraction).
            //maxvals[i,j] = max value reachable going straight
            //maxvals[i,j+cols] = max value reachable going diagonally
            int[,] maxvals = new int[rows, cols * 2];

            string[] nums = Console.ReadLine().Split(" ");

            int n;

            //easier to just fill in first row straight away
            for (int j = 0; j < cols; j++)
            {
                n = int.Parse(nums[j]);
                maxvals[0, j] = n;
                maxvals[0, j + cols] = -1 * n;
            }

            int lj;
            int rj;
            int max;

            //maxvals[i,j] = Max(leftdiag max (maxvals_i-1,j-1 + cols), vertical max (maxvals_i-1,j), rightdiag max (maxvals_i-1,j+1 + cols))
            for (int i = 1; i < rows; i++)
            {
                nums = Console.ReadLine().Split(" ");

                for (int j = 0; j < cols; j++)
                {
                    n = int.Parse(nums[j]);

                    //wrap-around shenanigans
                    lj = j == 0 ? cols - 1 : j - 1;
                    rj = j == cols - 1 ? 0 : j + 1;

                    max = Math.Max(maxvals[i - 1, lj + cols], Math.Max(maxvals[i - 1, j], maxvals[i - 1, rj + cols]));

                    maxvals[i, j] = max + n;
                    maxvals[i, j + cols] = max - n;
                }
            }

            max = 0;
            for (int j = 0; j < cols; j++)
            {
                max = maxvals[rows - 1, j] > max ? maxvals[rows - 1, j] : max;
            }
            Console.WriteLine(max);
        }
    }
}
