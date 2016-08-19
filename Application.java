/**
 * Put your copyright and license info here.
 */
package com.example1.trial2;

import com.datatorrent.api.DAG;
import com.datatorrent.api.StreamingApplication;
import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.lib.algo.UniqueCounter;
import com.datatorrent.lib.io.ConsoleOutputOperator;
import org.apache.hadoop.conf.Configuration;

@ApplicationAnnotation(name="WordCountDemo")
public class Application implements StreamingApplication
{


  @Override
  public void populateDAG(DAG dag, Configuration conf)
  {
    InputReader in=dag.addOperator("WordInput", new InputReader());
    UniqueCounter<String> unique=dag.addOperator("count", new UniqueCounter<String>());

    dag.addStream("Word-Count", in.outputPort, unique.data).setLocality(DAG.Locality.CONTAINER_LOCAL);

    ConsoleOutputOperator cons=dag.addOperator("Console", new ConsoleOutputOperator());

    dag.addStream("count-console", unique.count, cons.input).setLocality(DAG.Locality.CONTAINER_LOCAL);
  }
}