import java.util.*;
import java.io.*;

public class solver
{
    char[] greens;
    char[] yellows;
    char[] blacks;
    ArrayList<ArrayList<Character>> possibleAns = new ArrayList<ArrayList<Character>>();
    ArrayList<Character> allBlacks = new ArrayList<Character>();
    ArrayList<Character> allYellows = new ArrayList<Character>();

    public solver(char[] greens, char[] yellows, char[] blacks, ArrayList<ArrayList<Character>> possibleAns, ArrayList<Character> allBlacks, ArrayList<Character> allYellows)
    {
        this.greens = greens;
        this.yellows = yellows;
        this.blacks = blacks;
        this.possibleAns = possibleAns;
        this.allBlacks = allBlacks;
        this.allYellows = allYellows;

        for(int i = 0; i < greens.length; i++)
        {
            if(Character.compare(greens[i], '$') != 0)
            {
                possibleAns.get(i).clear();
                possibleAns.get(i).add(greens[i]);
            }
        }
        for(int i = 0; i < yellows.length; i++)
        {
            if(Character.compare(yellows[i], '$') != 0)
            {
                possibleAns.get(i).remove(Character.valueOf(yellows[i]));
            }
        }
        for(int i = 0; i < blacks.length; i++)
        {
            if(Character.compare(blacks[i], '$') != 0)
            {
                if(allBlacks.contains(blacks[i]))
                {
                    if(!allYellows.contains(blacks[i]))
                    {
                        for(int j = 0; j < possibleAns.size(); j++)
                        {
                            possibleAns.get(j).remove(Character.valueOf(blacks[i]));
                        }
                    }
                    else
                    {
                        possibleAns.get(i).remove(Character.valueOf(blacks[i]));
                    }
                }
            }
        }
    }

    public String solve()
    {
        String ans = null;
        try
        {
            File eqFile = new File("C:\\Users\\varen\\Desktop\\nerdle eqs\\eqs.txt");
            Scanner myReader = new Scanner(eqFile);

            ans = getEQ(myReader);

            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return ans;
    }

    public String getEQ(Scanner reader)
    {
        String answer = "failed";
        boolean good = false;
        boolean done = false;

        while(reader.hasNextLine())
        {
            String tempEQ = reader.nextLine();

            for(int i = 0; i < tempEQ.length(); i++)
            {
                good = false;

                if(Character.compare(greens[i], tempEQ.charAt(i)) == 0)
                {
                    if(i == 7)
                    {
                        answer = tempEQ;
                        done = true;
                    }
                    continue;
                }
                if(Character.compare(greens[i], '$') != 0)
                {
                    break;
                }
                if(allBlacks.contains(tempEQ.charAt(i)) && !allYellows.contains(tempEQ.charAt(i)) && !String.valueOf(greens).contains(String.valueOf(tempEQ.charAt(i))))
                {
                    break;
                }
                for(int j = 0; j < possibleAns.get(i).size(); j++)
                {
                    if(good)
                    {
                        break;
                    }
                    if(Character.compare(tempEQ.charAt(i), possibleAns.get(i).get(j)) == 0)
                    {
                        good = true;
                    }
                }
                if(good == true)
                {
                if(i == 7)
                {
                    answer = tempEQ;

                    HashMap<Character, Integer> repeats = findRepeats(allYellows);
                    for(int j = 0; j < allYellows.size(); j++)
                    {
                        if(answer.contains(String.valueOf(allYellows.get(j))))
                        {
                            int val = repeats.get(allYellows.get(j));
                            if(repeats.containsKey(allYellows.get(j)) && val > 1)
                            {
                                if(specContainChar(allYellows.get(j), val, answer))
                                {
                                    done = true;
                                }
                                else
                                {
                                    done = false;
                                }                              
                            }
                            else
                            {
                                done = true;
                            }
                        }
                        else
                        {
                            done = false;
                            break;
                        }
                    }
                    break;
                }
                }
                if(good == false) { break; }
            }
            
            HashMap<Character, Integer> repeats = findRepeats(allYellows);
            for(int j = 0; j < allYellows.size(); j++)
            {
                if(answer.contains(String.valueOf(allYellows.get(j))))
                {
                    int val = repeats.get(allYellows.get(j));
                    if(repeats.containsKey(allYellows.get(j)) && val > 1)
                    {
                        if(specContainChar(allYellows.get(j), val, answer))
                        {
                            done = true;
                        }
                        else
                        {
                            done = false;
                        }                              
                    }
                    else
                    {
                        done = true;
                    }
                }
                else
                {
                    done = false;
                    break;
                }
            }

            if(done) { break; }
        }

        return answer;
    }

    public HashMap<Character, Integer> findRepeats(ArrayList<Character> allYellows)
    {
        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();

        int[] count = new int[allYellows.size()];
        int i = 0;
        Iterator<Character> it = allYellows.iterator();

        while(it.hasNext())
        {
            char curr = it.next();
            if(hm.containsKey(curr))
            {
                int index = -1;
                for(int j = 0; j < i; j++)
                {
                    if(allYellows.get(j) == curr)
                    {
                        index = j;
                    }
                }
                count[index]++;
                hm.replace(curr, count[index]);
            }
            else
            {
                count[i] = 1;
                hm.put(curr, count[i]);
            }
            i++;
        }
        
        return hm;
    }

    public boolean specContainChar(char curr, int count, String answer)
    {
        char[] ans = answer.toCharArray();
        int x = 0;
        for(int i = 0; i < answer.length(); i++)
        {
            if(ans[i] == curr)
            {
                x++;
            }
        }
        if(x >= count) { return true; }
        else { return false; }
    }
}
