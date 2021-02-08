using System;
using System.Collections.Generic;

namespace DisjointSet
{
    class DJS
    {
        static void Main(string[] args)
        {
            DisjointSet dj = new DisjointSet(new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
            dj.PrintWebGraphViz();
            dj.Union('a', 'b');
            dj.PrintWebGraphViz();
            dj.Union('c', 'd');
            dj.PrintWebGraphViz();
            dj.Union('a', 'd');
            dj.PrintWebGraphViz();
            dj.Union('e', 'f');
            dj.PrintWebGraphViz();
            dj.Union('g', 'h');
            dj.PrintWebGraphViz();
            dj.Union('f', 'h');
            dj.PrintWebGraphViz();
            dj.Union('a', 'e');
            dj.PrintWebGraphViz();
            dj.Union('i', 'j');
            dj.PrintWebGraphViz();
            dj.Union('a', 'j');

            dj.PrintWebGraphViz();


            Console.ReadLine();
        }


    }


    class DisjointSet
    {
        private Dictionary<char, char> Nodes_Parents;
        private Dictionary<char, int> Nodes_Ranks;

        public DisjointSet(char[] vals)
        {
            Nodes_Parents = new Dictionary<char, char>();
            Nodes_Ranks = new Dictionary<char, int>();

            for (int i = 0; i < vals.Length; i++)
            {
                Nodes_Parents.Add(vals[i], vals[i]);
                Nodes_Ranks.Add(vals[i], 0);
            }
        }

        public void Union(char x, char y)
        {
            char xrep = Find(x);
            char yrep = Find(y);

            if (xrep == yrep) return;
            else if (Nodes_Ranks[xrep] > Nodes_Ranks[yrep])
            {
                Nodes_Parents[yrep] = xrep;
                Nodes_Ranks[xrep]++;
            }
            else if (Nodes_Ranks[xrep] < Nodes_Ranks[xrep])
            {
                Nodes_Parents[xrep] = yrep;
                Nodes_Ranks[yrep]++;
            }
            else
            {
                if (xrep > yrep)
                {
                    Nodes_Parents[yrep] = xrep;
                    Nodes_Ranks[xrep]++;
                }
                else
                {
                    Nodes_Parents[xrep] = yrep;
                    Nodes_Ranks[yrep]++;
                }
            }
        }

        public char Find(char x)
        {
            if (Nodes_Parents[x] != x)
            {
                Nodes_Parents[x] = Find(Nodes_Parents[x]);
            }
            return Nodes_Parents[x];
        }

        public void PrintWebGraphViz()
        {
            Console.WriteLine("digraph G {\n");
            foreach (char c in Nodes_Parents.Keys)
            {
                Console.WriteLine(c + " -> " + Nodes_Parents[c]);
            }

            Console.WriteLine("}");
            Console.WriteLine();

        }
    }

}
