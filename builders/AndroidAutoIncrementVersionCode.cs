using System.IO;
using System.Text.RegularExpressions;

namespace AndroidAutoIncrementVersionCode
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                string FILE = @"AndroidManifest.xml";
                string text = File.ReadAllText(FILE);
                Regex regex = new Regex(@"(?<A>android:versionCode="")(?<VER>\d+)(?<B>"")", RegexOptions.IgnoreCase);
                Match match = regex.Match(text);
                int verCode = int.Parse(match.Groups["VER"].Value) + 1;
                string newText = regex.Replace(text, "${A}" + verCode + "${B}", 1);

                File.WriteAllText(FILE, newText);
            }
            catch { }
        }
    }
}