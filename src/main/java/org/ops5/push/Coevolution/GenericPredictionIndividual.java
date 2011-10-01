package org.ops5.push.Coevolution;


import org.ops5.push.core.GAIndividual;
import org.ops5.push.core.Program;
import org.ops5.push.core.PushGP;
import org.ops5.push.core.PushGPIndividual;


public class GenericPredictionIndividual extends PredictionGAIndividual
{
  private static final long serialVersionUID = 1L;

  protected Program _program;

  protected PushGP _solutionGA;

  public GenericPredictionIndividual()
  {
    _solutionGA = null;
  }

  public GenericPredictionIndividual(Program inProgram, PushGP inSolutionGA)
  {
    _program = inProgram;
    _solutionGA = inSolutionGA;
  }

  @Override
  public float PredictSolutionFitness(PushGPIndividual pgpIndividual)
  {
    //TODO implement _program being run to predict fitness

    return -2999;
  }

  @Override
  public GAIndividual clone()
  {
    return new GenericPredictionIndividual(_program, _solutionGA);
  }

  void SetProgram(Program inProgram)
  {
    if (inProgram != null) _program = new Program(inProgram);
  }

  public String toString()
  {
    return _program.toString();
  }
}
