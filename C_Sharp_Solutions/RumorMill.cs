using System;
using System.Collections.Generic;
using System.Text;

namespace PS5
{
    class RumorMill
    {
        static void Main(string[] args)
        {
            Dictionary<int, List<string>> distance_layers = new Dictionary<int, List<string>>();
            HashSet<string> not_heard = new HashSet<string>();
            HashSet<string> heard = new HashSet<string>();
            Dictionary<string, Student> students = new Dictionary<string, Student>();
            List<string> r = new List<string>();

            int num_students = int.Parse(Console.ReadLine());

            for (int i = 0; i < num_students; i++)
            {
                string n = Console.ReadLine();
                not_heard.Add(n);
                students.Add(n, new Student(n));
            }

            int friend_pairs = int.Parse(Console.ReadLine());

            for (int i = 0; i < friend_pairs; i++)
            {
                string[] s = Console.ReadLine().Split(" ");
                students[s[0]].friends.Add(students[s[1]]);
                students[s[1]].friends.Add(students[s[0]]);
            }

            int rumors = int.Parse(Console.ReadLine());

            for (int i = 0; i < rumors; i++)
            {
                r.Add(Console.ReadLine());
            }

            for (int i = 0; i < rumors; i++)
            {
                StringBuilder rumor = new StringBuilder();

                Student start = students[r[i]];
                not_heard.Remove(start.name);
                heard.Add(start.name);
                start.distance = 0;
                distance_layers.Add(0, new List<string>() { start.name });

                Queue<Student> st = new Queue<Student>();
                st.Enqueue(start);

                while (st.Count > 0)
                {
                    Student s = st.Dequeue();
                    foreach (Student f in s.friends)
                    {
                        if (f.distance == -1)
                        {
                            st.Enqueue(f);
                            not_heard.Remove(f.name);
                            heard.Add(f.name);
                            int distance = s.distance + 1;
                            f.distance = distance;

                            if (distance_layers.ContainsKey(distance)) distance_layers[distance].Add(f.name);
                            else
                                distance_layers.Add(distance, new List<string> { f.name });
                        }
                    }
                }

                for (int j = 0; j < distance_layers.Count; j++)
                {
                    distance_layers[j].Sort();
                    foreach (string s in distance_layers[j])
                        rumor.Append(s + " ");
                }

                List<string> friendless = new List<string>(not_heard);
                friendless.Sort();

                foreach (string s in friendless) rumor.Append(s + " ");

                Console.WriteLine(rumor.ToString());

                foreach(string s in heard)
                {
                    not_heard.Add(s);
                    students[s].distance = -1;
                }

                heard = new HashSet<string>();
                distance_layers = new Dictionary<int, List<string>>();
            }
        }
    }

    public class Student
    {
        internal HashSet<Student> friends;
        internal string name;
        internal int distance;
        public Student(string n)
        {
            distance = -1;
            name = n;
            friends = new HashSet<Student>();
        }
    }
}
