package it.polimi.adaptanalyzertool.generator;

import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;

import java.util.Random;

/**
 * <p>
 * This class is in charge to create a new architecture using some parameters.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ArchitectureGenerator {

    private final static String COMPONENT_NAME = "C";
    private final static String SERVICE_NAME = "S";
    private final static double MIN_AVAILABILITY = 0.8;
    private final static double MAX_AVAILABILITY = 1;
    private final static double MIN_COST = 1;
    private final static double MAX_COST = 10;
    private final static double MIN_EXEC_TIME = 1;
    private final static double MAX_EXEC_TIME = 10;
    private final static double MIN_PROBABILITY = 0.8;
    private final static double MAX_PROBABILITY = 1;
    private final static int MIN_EXECS = 1;
    private final static int MAX_EXECS = 10;

    private int numberOfComponents;

    private Random random;

    private int numberOfRequiredFunctions;
    private int adaptabilityDegree;

    /**
     * <p>
     * Creates a generator with random seed.
     * </p>
     *
     * @param numberOfComponents        number of components in the generated architecture.
     * @param numberOfRequiredFunctions number of functions (services) that every component must require.
     * @param adaptabilityDegree        how many times every component should be duplicated.
     */
    public ArchitectureGenerator(int numberOfComponents, int numberOfRequiredFunctions, int adaptabilityDegree) {
        this.numberOfComponents = numberOfComponents;
        this.numberOfRequiredFunctions = numberOfRequiredFunctions;
        this.adaptabilityDegree = adaptabilityDegree;
        random = new Random();
    }

    /**
     * <p>
     * Creates a generator with a specified seed.
     * </p>
     * <p>
     * Use this constructor if you want to generate again a previously generated architecture.
     * </p>
     *
     * @param numberOfComponents        number of components in the generated architecture.
     * @param numberOfRequiredFunctions number of functions (services) that every component must require.
     * @param adaptabilityDegree        how many times every component should be duplicated.
     * @param seed                      seed for the random generation.
     */
    public ArchitectureGenerator(int numberOfComponents, int numberOfRequiredFunctions, int adaptabilityDegree, long seed) {
        this.numberOfComponents = numberOfComponents;
        this.numberOfRequiredFunctions = numberOfRequiredFunctions;
        this.adaptabilityDegree = adaptabilityDegree;
        random = new Random(seed);
    }

    /**
     * <p>
     * Generates an architecture eith the given name and parameters specified in the constructor.
     * </p>
     *
     * @param name the architecture name.
     * @return the newly generated architecture.
     */
    public Architecture generateArchitecture(String name) {

        Architecture newArchitecture = new Architecture(name);

        int lastRequiredFunc = 1;

        for (int i = 1; i <= (numberOfComponents / adaptabilityDegree); i++) {
            for (int adaptability = 1; adaptability <= adaptabilityDegree; adaptability++) {

                Component newComponent = new Component(
                        COMPONENT_NAME + i + "-" + adaptability,
                        MIN_COST + (MAX_COST - MIN_COST) * random.nextDouble(),
                        MIN_AVAILABILITY + (MAX_AVAILABILITY - MIN_AVAILABILITY) * random.nextDouble(),
                        true,
                        random.nextDouble(),
                        random.nextDouble(),
                        random.nextDouble());

                newArchitecture.addComponent(newComponent);

                newComponent.addProvidedService(new ProvidedService(SERVICE_NAME + i,
                        MIN_EXEC_TIME + (MAX_EXEC_TIME - MIN_EXEC_TIME) * random.nextDouble()));

                for (int reqFunc = 1; reqFunc <= numberOfRequiredFunctions; reqFunc++) {
                    if ((lastRequiredFunc + reqFunc) <= (numberOfComponents / adaptabilityDegree)) {
                        int funcToReq = lastRequiredFunc + reqFunc;
                        newComponent.addRequiredService(new RequiredService(
                                SERVICE_NAME + funcToReq,
                                MIN_PROBABILITY + (MAX_PROBABILITY - MIN_PROBABILITY) * random.nextDouble(),
                                random.nextInt((MAX_EXECS - MIN_EXECS) + 1) + MIN_EXECS)
                        );
                    }
                }
            }
            lastRequiredFunc += numberOfRequiredFunctions;
        }
        return newArchitecture;
    }
}
