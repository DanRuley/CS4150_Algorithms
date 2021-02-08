using System;
using System.Collections.Generic;

namespace CartesiaPrime
{
    /*Solution to the 4150 final Kattis problem: Cartesia Prime
     *@Author: Dan Ruley
     */
    class Cartesia
    {
        /// <summary>
        /// Main entry point.
        /// </summary>
        static void Main(string[] args)
        {
            int x, y;

            string[] input = Console.ReadLine().Split(" ");
            x = int.Parse(input[0]);
            y = int.Parse(input[1]);

            Graph g = new Graph();
            g.ReadParams();
            g.ReadBorg();
            int result = g.BeamMeUpScotty(x, y);

            //Beam me up Scotty is definitely an iconic phrase; however, I think we all know 
            //Geordi Laforge is the best Engineer in Starfleet.
            if (result >= 0)
                Console.WriteLine("I had " + (g.m - result) + " to spare! Beam me up Scotty!");
            else
                Console.WriteLine("You will be assimilated! Resistance is futile!");

            Console.ReadLine();
        }
    }

    /// <summary>
    /// Simple Graph class.
    /// </summary>
    class Graph
    {
        private int a, b, c, d;
        public int m { get; set; }

        public HashSet<Vertex> borg;

        public Graph()
        {
            borg = new HashSet<Vertex>();
        }

        public void ReadParams()
        {
            string[] input = Console.ReadLine().Split(" ");

            a = int.Parse(input[0]);
            b = int.Parse(input[1]);
            c = int.Parse(input[2]);
            d = int.Parse(input[3]);
            m = int.Parse(Console.ReadLine());
        }

        public void ReadBorg()
        {
            int n = int.Parse(Console.ReadLine());

            for (int i = 0; i < n; i++)
            {
                string[] input = Console.ReadLine().Split(" ");
                borg.Add(new Vertex(int.Parse(input[0]), int.Parse(input[1]), 0));
            }
        }

        /// <summary>
        /// Searches the graph with a BFS, only creating vertices as needed.
        /// Returns t: the shortest number of beams required to reach (0,0) OR -1 if it is not possible.
        /// </summary>
        public int BeamMeUpScotty(int x_start, int y_start)
        {
            //Best way I could think of to avoid a lot of redundant code with the +- delts
            int[,] signs = new int[,] { { 1, 1, -1, -1 }, { 1, -1, 1, -1 } };

            Vertex s = new Vertex(x_start, y_start, 0);

            if (borg.Contains(s))
                return -1;

            Queue<Vertex> q = new Queue<Vertex>();
            q.Enqueue(s);
            int t;
            int newx;
            int newy;
            while (q.Count != 0)
            {
                Vertex u = q.Dequeue();

                //t = dist of parent + 1
                t = u.Dist + 1;

                //Out of time, we've been assimilated!!
                if (t > m)
                    return -1;

                int xdelt = a * t % b;
                int ydelt = c * t % d;

                for (int i = 0; i < 4; i++)
                {
                    newx = u.Coords[0] + signs[0, i] * xdelt;
                    newy = u.Coords[1] + signs[1, i] * ydelt;

                    Vertex v = new Vertex(newx, newy, t);

                    if (borg.Contains(v))
                        continue;
                    else if (newx == 0 && newy == 0)
                        return t;
                    else
                        q.Enqueue(v);
                }
            }
            //The only way we could get here is if every move encountered a borg I think...
            return -1;
        }
    }

    /// <summary>
    /// Basic Vertex containing coordinates and dist (which represents the number of beams since the start)
    /// Also overrides hashcode/equals for Borg HashSet checking
    /// </summary>
    class Vertex
    {
        public int Dist { get; set; }
        public int[] Coords { get; set; }


        public Vertex(int x, int y, int d)
        {
            Dist = d;
            Coords = new int[] { x, y };
        }

        public override bool Equals(object obj)
        {
            return Equals(obj as Vertex);
        }

        public bool Equals(Vertex v)
        {
            return this.Coords[0] == v.Coords[0] && this.Coords[1] == v.Coords[1];
        }

        public override int GetHashCode()
        {
            String hc = this.Coords[0].ToString() + this.Coords[1].ToString();
            return hc.GetHashCode();
        }
    }
}
