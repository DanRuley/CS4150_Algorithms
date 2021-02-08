

using System;

namespace LongestSubsequence
{
    class LongestSS_MinEdit
    {
        static void Main(string[] args)
        {
            PrintArr(MinEditDistance("Snowy", "Sunny"));
            PrintArr(MinEditDistance("asdflknwiasdjfalkdsfnwlkasfd", "asdfkljasdflasnfkejfansdl"));

            Console.ReadLine();
        }
        //correct: 6, 5, 5, 4, 3, 4, 2, 3, 2, 1
        public static int[] LongestSubsequence(int[] input)
        {
            int[] result = new int[input.Length];

            for (int i = input.Length - 1; i >= 0; i--)
            {
                int max = 0;

                for (int j = i + 1; j < result.Length; j++)
                {
                    if (input[i] < input[j] && result[j] > max) max = result[j];
                }
                result[i] = 1 + max;
            }

            return result;
        }

        public static int[,] MinEditDistance(string s, string r)
        {
            int[,] result = BuildStartingArr(s.Length, r.Length);

            for (int i = 1; i < result.GetLength(0); i++)
            {
                for (int j = 1; j < result.GetLength(1); j++)
                {
                    //PrintArr(result);
                    if (s[i - 1] == r[j - 1]) result[i, j] = result[i - 1, j - 1];
                    else
                        result[i, j] = Math.Min(result[i, j - 1], Math.Min(result[i - 1, j - 1], result[i - 1, j])) + 1;
                }
            }

            return result;
        }

        public static int[,] BuildStartingArr(int s, int r)
        {
            int[,] result = new int[s + 1, r + 1];

            for (int i = 0; i <= s; i++)
            {
                result[i, 0] = i;
            }
            for (int i = 0; i <= r; i++)
            {
                result[0, i] = i;
            }
            return result;
        }

        public static void PrintArr(int[,] arr)
        {
            for (int i = 0; i < arr.GetLength(0); i++)
            {
                for (int j = 0; j < arr.GetLength(1); j++)
                {
                    Console.Write(arr[i, j] + " ");
                }
                Console.WriteLine();
            }
        }
    }
}
