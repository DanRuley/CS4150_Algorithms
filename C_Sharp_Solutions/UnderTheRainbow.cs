using System;

namespace UnderTheRainbow
{
    class PS9
    {
        /// <summary>
        /// Main entry point for the application
        /// </summary>
        static void Main(string[] args)
        {
            int n = int.Parse(Console.ReadLine());
            int[] hotels = new int[n + 1];
            for (int i = 0; i < hotels.Length; i++)
            {
                hotels[i] = int.Parse(Console.ReadLine());
            }
            Console.WriteLine(FindMinPenalty(hotels));
            Console.ReadLine();
        }

        /// <summary>
        /// Computes the minumum penalty from hotels[0] to hotels[n] using a linear programming strategy.
        /// </summary>
        /// <param name="hotels">Array of hotel distances</param>
        /// <returns>The minimum penalty from Munchkinland to the Emerald City</returns>
        private static int FindMinPenalty(int[] hotels)
        {
            int[] penalties = new int[hotels.Length];

            for (int i = hotels.Length - 2; i >= 0; i--)
            {
                //i is the hotel whose penalty(i) we're calculating - penalties(j) is the candidate hotel we are trying to minimize
                int min = int.MaxValue;
                for (int j = i + 1; j < penalties.Length; j++)
                {
                    int penalty_factor = 400 - (hotels[j] - hotels[i]);
                    int penalty = penalty_factor * penalty_factor;
                    if (penalty + penalties[j] < min) min = penalty + penalties[j];
                }
                penalties[i] = min;
            }
            return penalties[0];
        }
    }
}
