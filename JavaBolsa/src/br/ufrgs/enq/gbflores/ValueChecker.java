package br.ufrgs.enq.gbflores;

import org.apache.commons.math.optimization.ConvergenceChecker;
import org.apache.commons.math.optimization.PointCostPair;

class ValueChecker implements ConvergenceChecker {
	private double threshold;

	public ValueChecker(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * check for convergence
	 */
	public boolean converged(PointCostPair[] simplex) {
		PointCostPair smallest = simplex[0];
		PointCostPair largest = simplex[simplex.length - 1];
		return (1000*largest.getCost() - 1000*smallest.getCost()) < threshold;
	}
};
