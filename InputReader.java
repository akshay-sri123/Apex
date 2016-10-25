package com.example1.trial2;


import com.datatorrent.lib.io.SimpleSinglePortInputOperator;

import java.io.*;

public class InputReader extends SimpleSinglePortInputOperator<String> implements Runnable
{
    protected String file="/user/files/text/sample.txt";
    //protected String file="/home/akshay/sampl.txt";

    @Override
    public void run()
    {
        BufferedReader br=null;
        DataInputStream dis=null;
        InputStream fstream=null;


        while(true)
        {
            try
            {
                String line;
                fstream = this.getClass().getClassLoader().getResourceAsStream(file);

                dis=new DataInputStream(fstream);
                br=new BufferedReader(new InputStreamReader(dis));

                while((line=br.readLine()) != null)
                {
                    String[] words=line.trim().split("[\\p{Punct}\\s\\\"\\'“”]+");

                    for(String word: words)
                    {
                        word=word.trim().toLowerCase();

                        if(!word.isEmpty())
                        {
                            outputPort.emit(word);
                        }
                    }
                }
            }
            catch(Exception e)
            {

            }

            finally
            {
                try {
                    if(br!=null)
                    {
                        br.close();
                    }
                    if(dis!=null)
                    {
                        dis.close();
                    }
                    if(fstream!=null)
                    {
                        fstream.close();
                    }
                }
                catch(IOException exc)
                {

                }
            }
        }
    }
}
