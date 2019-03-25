package project.excelSpike;

import java.util.List;

/*
 * I made AreaSchema and EquivalencySchema abstract, since we won't have more than one of them and we don't preserve the information.
 */
public class SampleTester {

	public static void main(String[] args) {
		
		/*
		 * AreaSchema example: use the getAllAreas() to get the areas, then use a for each to get the grades within them.
		 * In distribution this would be a couple loops. The number of areas shouldn't be too big so this won't be much of a bottleneck.
		 */
		AreaSchema.setAreasFromFile("/home1/ugrads/ecamp/CS2043/AreaInputSample");
		List<String> allAreas = AreaSchema.getAllAreas();
		for (String area : allAreas) {
			System.out.println(area);
			System.out.println(AreaSchema.getAllCoursesInArea(area));
		}
		
		/*
		 * Equivalency stuff is very fast, 0(1). The input is either unchanged or its the matching key value. No loops needed
		 * for this check; we can build in a .getEquivalent() check directly when it comes up.
		 */
		EquivalencySchema.setEquivalenciesFromFile("/home1/ugrads/ecamp/CS2043/EquivInputSample");
		System.out.println("Math 101 is equivalent to " + EquivalencySchema.getEquivalent("Math101"));
		System.out.println("Math 102 is equivalent to " + EquivalencySchema.getEquivalent("Math102"));
		System.out.println("Math 103 is equivalent to " + EquivalencySchema.getEquivalent("Math103"));
	}
}
