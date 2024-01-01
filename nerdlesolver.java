import java.util.*;

public class nerdlesolver
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        boolean notDone = true;
        ArrayList<Character> allBlacks = new ArrayList<Character>();
        ArrayList<Character> allYellows = new ArrayList<Character>();
        ArrayList<ArrayList<Character>> possibleAns = new ArrayList<ArrayList<Character>>();

        for(int i = 0; i < 8; i ++)
        {
            possibleAns.add(new ArrayList<Character>());
        }
        for(int i = 1; i <= 9; i++)
        {
            char x = (char) (i + '0');
            possibleAns.get(0).add(x);
        }
        for(int i = 1; i < 8; i++)
        {
            for(int j = 0; j <= 9; j++)
            {
                char x = (char) (j + '0');
                possibleAns.get(i).add(x);
            }
            if(i < 5)
            {
                possibleAns.get(i).add('+');
                possibleAns.get(i).add('-');
                possibleAns.get(i).add('*');
                possibleAns.get(i).add('/');
            }
            if(i > 3 && i < 7)
            {
                possibleAns.get(i).add('=');
            }
        }
        System.out.print("Which way? PC (1) / USER (2): ");
        int mode = scan.nextInt();
        scan.nextLine();

        if(mode == 1)
        {
            do
            {
                allYellows = new ArrayList<Character>();

                System.out.print("Enter the greens (with spaces and $ for non-greens): ");
                String resultsSTR = scan.nextLine();
                char[] greens = parseResults(resultsSTR);

                System.out.print("Enter the yellows (with spaces and $ for non-yellows): ");
                resultsSTR = scan.nextLine();
                char[] yellows = parseResults(resultsSTR);
                for(int i = 0; i < yellows.length; i++)
                {
                    if(Character.compare(yellows[i], '$') != 0)
                    {
                        allYellows.add(yellows[i]);
                    }
                }

                System.out.print("Enter the blacks (with spaces and $ for non-blacks): ");
                resultsSTR = scan.nextLine();
                char[] blacks = parseResults(resultsSTR);
                for(int i = 0; i < blacks.length; i++)
                {
                    if(Character.compare(blacks[i], '$') != 0)
                    {
                        if(!allBlacks.contains(blacks[i]))
                        {
                            allBlacks.add(blacks[i]);
                        }
                    }
                }

                solver gameSolver = new solver(greens, yellows, blacks, possibleAns, allBlacks, allYellows);

                // for(int i = 0; i < 8; i++)
                // {
                //     Iterator<Character> it = gameSolver.possibleAns.get(i).iterator();
                    
                //     System.out.print(i + ": ");
                //     while(it.hasNext())
                //     {
                //         System.out.print(it.next() + " ");
                //     }
                //     System.out.println();
                // }

                String solvedGame = gameSolver.solve();

                
                System.out.println(solvedGame);

                System.out.print("Is this Correct? (0 for no, 1 for yes): ");
                int ans = scan.nextInt();
                System.out.println();
                scan.nextLine();
                if(ans == 1) { notDone = false; }
            } while(notDone);
        }
        if(mode == 2)
        {
            do
            {
                System.out.print("Enter the greens (with spaces and $ for non-greens): ");
                String resultsSTR = scan.nextLine();
                char[] greens = parseResults(resultsSTR);

                System.out.print("Enter the yellows (with spaces and $ for non-yellows): ");
                resultsSTR = scan.nextLine();
                char[] yellows = parseResults(resultsSTR);
                for(int i = 0; i < yellows.length; i++)
                {
                    if(Character.compare(yellows[i], '$') != 0)
                    {
                        if(!allYellows.contains(yellows[i])) { allYellows.add(yellows[i]); }
                        else if((String.valueOf(yellows)).contains(String.valueOf(yellows[i]))) { allYellows.add(yellows[i]); }
                    }
                }

                System.out.print("Enter the blacks (with spaces and $ for non-blacks): ");
                resultsSTR = scan.nextLine();
                char[] blacks = parseResults(resultsSTR);
                for(int i = 0; i < blacks.length; i++)
                {
                    if(Character.compare(blacks[i], '$') != 0)
                    {
                        if(!allBlacks.contains(blacks[i]))
                        {
                            allBlacks.add(blacks[i]);
                        }
                    }
                }
                System.out.println(allBlacks);

                solver gameSolver = new solver(greens, yellows, blacks, possibleAns, allBlacks, allYellows);

                // for(int i = 0; i < 8; i++)
                // {
                //     Iterator<Character> it = gameSolver.possibleAns.get(i).iterator();
                    
                //     System.out.print(i + ": ");
                //     while(it.hasNext())
                //     {
                //         System.out.print(it.next() + " ");
                //     }
                //     System.out.println();
                // }

                String solvedGame = gameSolver.solve();

                
                System.out.println(solvedGame);

                System.out.print("Is this Correct? (0 for no, 1 for yes): ");
                int ans = scan.nextInt();
                System.out.println();
                scan.nextLine();
                if(ans == 1) { notDone = false; }
            } while(notDone);
        }
        else { System.out.println("please run again and pick 1 or 2 for mode");}
        scan.close();
    }

    public static char[] parseResults(String resultsSTR)
    {
        char[] results = new char[8];

        StringTokenizer st = new StringTokenizer(resultsSTR);
        int i = 0;

        while(st.hasMoreTokens())
        {
            String next = st.nextToken();
            results[i] = next.charAt(0);
            i++;
        }

        return results;
    }
}