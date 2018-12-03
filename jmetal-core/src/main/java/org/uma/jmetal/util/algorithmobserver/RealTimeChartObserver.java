package org.uma.jmetal.util.observer.impl;

import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.MeasureListener;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.chartcontainer.ChartContainer;
import org.uma.jmetal.util.observer.Observer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class RealTimeChartObserver implements MeasureListener<Map<String, Object>> {
  private ChartContainer chart;
  private MeasureManager measureManager ;

  public RealTimeChartObserver(Measurable measurable, String legend) {
    this(measurable, legend, "") ;
  }

  public RealTimeChartObserver(Measurable measurable, String legend, String referenceFrontName) {
    measureManager = measurable.getMeasureManager() ;
    BasicMeasure<Map<String, Object>> observedData =  (BasicMeasure<Map<String, Object>>)measureManager
            .<Map<String, Object>>getPushMeasure("ALGORITHM_DATA");

    observedData.register(this);

    chart = new ChartContainer(legend) ;
    try {
      chart.setFrontChart(0, 1, referenceFrontName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    chart.initChart();
  }

  @Override
  public void measureGenerated(Map<String, Object> data) {
    int evaluations = (int)data.get("EVALUATIONS") ;
    List<? extends Solution<?>> population = (List<? extends Solution<?>>) data.get("POPULATION");
    if (this.chart != null) {
      this.chart.getFrontChart().setTitle("Iteration: " + evaluations);
      this.chart.updateFrontCharts(population);
      this.chart.refreshCharts();
    }
  }
}
