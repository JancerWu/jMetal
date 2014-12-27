package org.uma.jmetal.util.archive.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.comparator.impl.CrowdingDistanceComparator;
import org.uma.jmetal.util.comparator.impl.DominanceComparator;
import org.uma.jmetal.util.comparator.impl.EqualSolutionsComparator;
import org.uma.jmetal.util.solutionattribute.DensityEstimator;
import org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Antonio J. Nebro on 24/09/14.
 */
public class CrowdingDistanceArchive<T extends Solution> implements Archive<T> {
  private int maxSize ;
  private List<T> solutionList;
  private Comparator<Solution> dominanceComparator;
  private Comparator<Solution> equalsComparator;
  private Comparator<Solution> crowdingDistanceComparator;
  private DensityEstimator crowdingDistance ;

  public CrowdingDistanceArchive(int maxSize) {
    this.maxSize = maxSize ;
    solutionList = new ArrayList<>(maxSize + 1) ;
    dominanceComparator = new DominanceComparator();
    crowdingDistanceComparator = new CrowdingDistanceComparator() ;
    crowdingDistance = new CrowdingDistance() ;
    equalsComparator = new EqualSolutionsComparator() ;
  }

  @Override
  public boolean add(T solution) {
    int flag ;
    int i = 0;
    Solution aux;
    while (i < solutionList.size()) {
      aux = solutionList.get(i);

      flag = dominanceComparator.compare(solution, aux);
      if (flag == 1) {
        return false;
      } else if (flag == -1) {
        solutionList.remove(i);
      } else {
        if (equalsComparator.compare(aux, solution) == 0) {
          return false;
        }
      }
      i++;
    }

    solutionList.add(solution);
    if (solutionList.size() > maxSize) { // FIXME: check whether the removed solution is the inserted one
      crowdingDistance.computeDensityEstimator(solutionList);
      int index = new SolutionListUtils().findWorstSolution(solutionList, crowdingDistanceComparator) ;
      solutionList.remove(index);
    }
    return true;
  }

  @Override
  public List<T> getSolutionList() {
    return solutionList;
  }

  @Override
  public int getMaxSize() {
    return maxSize;
  }

  public void computeDistance() {
    crowdingDistance.computeDensityEstimator(solutionList);
  }
}
