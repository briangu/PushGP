package org.ops5.push;


import java.util.Arrays;


public class Main
{
  public static void main(String[] args) throws Exception
  {
    if (args.length <= 1)
    {
      System.out.println("Usage: <gp | inspect | eq> [args]");
      System.exit(0);
    }

    String mode = args[0];

    String[] subArgs = Arrays.asList(args).subList(1,args.length).toArray(new String[0]);

    if (mode.equals("gp"))
    {
      PushGP.main(subArgs);
    }
    else if (mode.equals("inspect"))
    {
      PushInspector.main(subArgs);
    }
    else if (mode.equals("eq"))
    {
      PushEquationBuilder.main(args);
    }
  }
}
