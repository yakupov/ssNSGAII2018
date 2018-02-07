package ru.ifmo.nds.nsga2;

import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.DominanceComparator;

public class RankComparator implements DominanceComparator {
    public static final String RANK_ATTR_NAME = "ru.itmo.nds.rank";

    @Override
    public int compare(Solution solution, Solution solution1) {
        final Object d1 = solution.getAttribute(RANK_ATTR_NAME);
        final Object d2 = solution1.getAttribute(RANK_ATTR_NAME);
        if (d1 instanceof Number && d2 instanceof Number) {
            return Integer.compare(((Number)d1).intValue(), ((Number)d2).intValue());
        }
        return 0;
    }
}
