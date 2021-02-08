using System;
using System.Collections.Generic;

namespace NarrowArtGallery
{
    class PS10
    {
        static Dictionary<Tuple<int, int, int>, int> memo;
        static int[,] g;
        static int N;
        static void Main(string[] args)
        {
            while (true)
            {
                memo = new Dictionary<Tuple<int, int, int>, int>();
                string[] input = Console.ReadLine().Split(' ');
                N = int.Parse(input[0]);
                if (N == 0) break;
                int k = int.Parse(input[1]);
                int[,] galleries = new int[N, 2];

                for (int i = 0; i < N; i++)
                {
                    input = Console.ReadLine().Split(' ');

                    galleries[i, 0] = int.Parse(input[0]);
                    galleries[i, 1] = int.Parse(input[1]);
                }

                g = galleries;

                int max = maxValue(0, -1, k);

                Console.WriteLine(max);
            }
        }

        private static int maxValue(int row, int close, int k)
        {
            if (row == N) return 0;

            Tuple<int, int, int> rr = new Tuple<int, int, int>(row, close, k);

            if (memo.ContainsKey(rr)) return memo[rr];

            int best = 0;

            //Cuts down on fn calls by up to 30/40%
            if (k == 0)
            {
                for (int i = g.GetLength(0) - 1; i > row; i--)
                {
                    Tuple<int, int, int> r = new Tuple<int, int, int>(i, -1, 0);
                    best += g[i, 0] + g[i, 1];
                    if (!memo.ContainsKey(r)) memo.Add(r, best);
                }

                best += g[row, 0] + g[row, 1];
                memo.Add(rr, best);
                return best;
            }


            //Can close either room or none
            if (close == -1)
            {
                //Don't have to close
                if (k < N - row)
                    best = Math.Max(g[row, 0] + g[row, 1] + maxValue(row + 1, -1, k), Math.Max(g[row, 1] + maxValue(row + 1, 1, k - 1), g[row, 0] + maxValue(row + 1, 0, k - 1)));

                //Have to close a room
                else
                    best = Math.Max(g[row, 1] + maxValue(row + 1, 1, k - 1), g[row, 0] + maxValue(row + 1, 0, k - 1));
            }

            //Cannot close [r][0] ==> can only close [r][1] or none
            else if (close == 0)
            {
                //Don't have to close
                if (k < N - row)
                    best = Math.Max(g[row, 0] + maxValue(row + 1, 0, k - 1), g[row, 0] + g[row, 1] + maxValue(row + 1, -1, k));
                //Have to close
                else
                    best = g[row, 0] + maxValue(row + 1, 0, k - 1);
            }

            //Cannot close [r][1] ==> can only close [r][0] or none
            else
            {
                if (k < N - row)
                    best = Math.Max(g[row, 1] + maxValue(row + 1, 1, k - 1), g[row, 0] + g[row, 1] + maxValue(row + 1, -1, k));
                else
                    best = g[row, 1] + maxValue(row + 1, 1, k - 1);
            }


            memo.Add(rr, best);
            return best;
        }
    }
}
