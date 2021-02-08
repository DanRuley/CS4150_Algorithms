using System;

namespace Fireworks
{
    /**
     * Solution to the 4150 final Kattis problem: Fireworks.
     * @Author: Dan Ruley
     */
    class Fireworks
    {
        /// <summary>
        /// Read input and print answers
        /// </summary>
        static void Main(string[] args)
        {
            string[] s = Console.ReadLine().Split(" ");

            int launchers = int.Parse(s[0]);
            int scn = int.Parse(s[1]);
            int[] scenarios = new int[scn];
            int[] distances = new int[launchers - 1];
            int maxdist = 0;
            int mindist = int.MaxValue;

            for (int i = 0; i < launchers - 1; i++)
            {
                distances[i] = int.Parse(Console.ReadLine());
                mindist = distances[i] < mindist ? distances[i] : mindist;
                maxdist += distances[i];
            }

            for (int i = 0; i < scenarios.Length; i++)
                scenarios[i] = int.Parse(Console.ReadLine());

            for (int i = 0; i < scenarios.Length; i++)
            {
                int result = FindOptimal(ref distances, mindist, maxdist, scenarios[i]);
                Console.WriteLine(result);
            }

        }

        /// <summary>
        /// Use a b-search to find the smallest distance it is possible to separate n launchers by repeatedly calling GreedyDistanceFinder.
        /// </summary>
        private static int FindOptimal(ref int[] d, int mindist, int maxdist, int nlaunch)
        {
            if (nlaunch == 2)
                return maxdist;
            else if (nlaunch == d.Length + 1)
                return mindist;

            int low = mindist;
            int hi = maxdist;
            int maxmin = 0;
            int mid;
            int val;

            while (low <= hi)
            {
                mid = (low + hi) / 2;

                val = GreedyDistanceFinder(ref d, nlaunch, mid);
                maxmin = val > maxmin && val != int.MaxValue ? val : maxmin;

                //Not possible to space n launchers at least mid dist apart.
                if (val == int.MaxValue)
                    hi = mid - 1;

                else
                    low = mid + 1;
            }

            return maxmin;
        }

        /// <summary>
        /// Iterate through the array of distances between the launchers.  Use a greedy strategy to count how many launchers may be spaced <= spacing distance apart.  If is possible to space n launchers with the given distance, return the minimum distance found.  Otherwise, return int.MAX if it is not possible.
        /// </summary>
        public static int GreedyDistanceFinder(ref int[] d, int nlaunch, int spacing)
        {
            int running_sum = 0;
            int launchers_used = 1;
            int min_dist = int.MaxValue;
            for (int i = 0; i < d.Length; i++)
            {
                running_sum += d[i];
                if (running_sum >= spacing)
                {
                    min_dist = running_sum < min_dist ? running_sum : min_dist;

                    running_sum = 0;
                    launchers_used++;
                }
            }

            if (launchers_used < nlaunch)
                return int.MaxValue;
            else
                return min_dist;
        }
    }
}
