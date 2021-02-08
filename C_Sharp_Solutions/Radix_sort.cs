using System;
using System.Diagnostics;

namespace Counting_radix
{
    class Radix_sort
    {
        static void Main(string[] args)
        {
            int[] nums = new int[10000000];
            Random r = new Random(314);

            int max = 0;
            for (int i = 0; i < 10000000; i++)
            {

                nums[i] = r.Next(0, 10000000);
            }

            Stopwatch w = new Stopwatch();
            w.Start();
            while (w.ElapsedMilliseconds < 1000)
            {

            }

            for (int i = 0; i < 100; i++)
            {
                int[] vals = new int[10000000];
                Array.Copy(nums, vals, 10000000);
                //radix_sort(vals, 10000000);
                Array.Sort(vals);
            }


            w.Stop();
            double time = w.ElapsedMilliseconds - 1000;

            w.Reset();
            w.Start();
            for (int i = 0; i < 100; i++)
            {
                int[] vals = new int[10000000];
                Array.Copy(nums, vals, 10000000);
            }
            w.Stop();

            Console.WriteLine("avg time: " + ((time - w.ElapsedMilliseconds) / 100));


            Console.ReadLine();
        }

        static int[] radix_sort(int[] input, int max_place)
        {
            int[] result = input;

            int mod = max_place;
            int div = 1;

            while (mod >= div)
            {
                int[] new_result = new int[input.Length];

                int[] indices = new int[10];

                for (int i = 0; i < input.Length; i++)
                {
                    int val = result[i];
                    int temp_mod = mod;

                    while (temp_mod > div)
                    {
                        val = val % temp_mod;
                        temp_mod /= 10;
                    }
                    val = val / div;
                    indices[val]++;
                }

                for (int i = 1; i < indices.Length; i++)
                {
                    indices[i] += indices[i - 1];
                }

                for (int i = input.Length - 1; i >= 0; i--)
                {
                    int val = result[i];
                    int temp_mod = mod;

                    while (temp_mod > div)
                    {
                        val = val % temp_mod;
                        temp_mod /= 10;
                    }

                    val = val / div;
                    new_result[--indices[val]] = result[i];
                }

                div *= 10;
                result = new_result;
            }

            return result;
        }
    }
}
