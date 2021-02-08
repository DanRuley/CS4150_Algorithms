using System;
using System.Collections.Generic;

namespace _4150
{
    class AutoSink
    {
        static void Main(string[] args)
        {
            List<string[]> Trips = new List<string[]>();
            Graph g = new Graph();

            int NumCities = int.Parse(Console.ReadLine());
            for (int i = 0; i < NumCities; i++)
            {
                string[] s = Console.ReadLine().Split(' ');
                g.AddVertex(s[0], int.Parse(s[1]));
            }

            int NumHighways = int.Parse(Console.ReadLine());
            for (int i = 0; i < NumHighways; i++)
            {
                string[] s = Console.ReadLine().Split(' ');
                g.AddVertexWithEdge(s[0], s[1]);
            }

            int NumTrips = int.Parse(Console.ReadLine());
            for (int i = 0; i < NumTrips; i++)
            {
                string[] s = Console.ReadLine().Split(' ');
                Trips.Add(new string[] { s[0], s[1] });
            }

            g.DepthFirstSearchWithTopoSort();

            foreach (string[] s in Trips)
            {
                if (s[0] == s[1]) Console.WriteLine("0");
                else
                {
                    int c = g.FindMinToll(s[0], s[1]);
                    if (c >= g.inf) Console.WriteLine("NO");
                    else
                        Console.WriteLine(c);
                }
            }
        }
    }


    class Graph
    {
        public int inf = 20000001;
        private Dictionary<string, Vertex> cities;
        private List<Vertex> TopoSorted;

        public Graph()
        {
            TopoSorted = new List<Vertex>();
            cities = new Dictionary<string, Vertex>();
        }

        public void AddVertex(string v, int t)
        {
            cities.Add(v, new Vertex(v, t));
        }

        public void AddVertexWithEdge(string v, string d)
        {
            Vertex src = cities[v];
            Vertex dst = cities[d];

            //Add to children set
            if (!src.children.Contains(dst)) src.children.Add(dst);
        }

        public void DepthFirstSearchWithTopoSort()
        {
            foreach (string v in cities.Keys)
                cities[v].visited = false;

            foreach (string v in cities.Keys)
                if (!cities[v].visited)
                    Explore(cities[v]);
        }

        public void Explore(Vertex v)
        {
            v.visited = true;

            foreach (Vertex e in v.children)
                if (!e.visited) Explore(e);

            TopoSorted.Add(v);
        }

        public int FindMinToll(string source, string dest)
        {
            foreach (Vertex v in TopoSorted)
            {
                //Case: v is our destination
                if (v.name == dest) v.cost = 0;

                //Case: v is a sink
                else if (v.children.Count == 0)
                    v.cost = inf;


                //third case, compute lowest of: cost of child to dest + toll to child
                else
                {
                    int mincost = inf;
                    foreach (Vertex c in v.children)
                    {
                        if (c.toll + c.cost < mincost)
                        {
                            mincost = c.toll + c.cost;
                        }
                    }
                    v.cost = mincost;
                }

                //This lets us end early
                if (v.name == source) return v.cost;
            }
            return cities[source].cost;
        }
    }

    public class Vertex
    {
        public HashSet<Vertex> children;
        public bool visited { get; set; }
        public int cost { get; set; }
        public int toll;
        public string name;


        public Vertex(string c, int t)
        {
            children = new HashSet<Vertex>();
            name = c;
            toll = t;
        }
    }
}
