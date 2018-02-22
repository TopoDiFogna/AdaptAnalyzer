package it.polimi.adaptanalyzertool.generator;

import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;

import java.util.Random;

public class ArchitectureGenerator {

    private final static String COMPONENT_NAME = "C";
    private final static String SERVICE_NAME = "S";
    private final static double MIN_AVAILABILITY = 0.8;
    private final static double MAX_AVAILABILITY = 1;
    private final static double MAX_COST = 100;
    private final static double MAX_EXEC_TIME = 10;
    private final static double MIN_PROBABILITY = 0.8;
    private final static double MAX_PROBABILITY = 1;
    private final static int MIN_EXECS = 1;
    private final static int MAX_EXECS = 10;

    private int numberOfComponents;

    private Random random;

    private int numberOfRequiredFunctions;
    private int adaptabilityDegree;

    public ArchitectureGenerator(int numberOfComponents, int numberOfRequiredFunctions, int adaptabilityDegree) {
        this.numberOfComponents = numberOfComponents;
        this.numberOfRequiredFunctions = numberOfRequiredFunctions;
        this.adaptabilityDegree = adaptabilityDegree;
        random = new Random();
    }

    public ArchitectureGenerator(int numberOfComponents, int numberOfRequiredFunctions, int adaptabilityDegree, long seed) {
        this.numberOfComponents = numberOfComponents;
        this.numberOfRequiredFunctions = numberOfRequiredFunctions;
        this.adaptabilityDegree = adaptabilityDegree;
        random = new Random(seed);
    }

    public Architecture generateArchitecture(String name) {

        Architecture newArchitecture = new Architecture(name);

        int lastRequiredFunc = 1;

        for (int i = 1; i <= (numberOfComponents / adaptabilityDegree); i++) {
            for (int adaptability = 1; adaptability <= adaptabilityDegree; adaptability++) {

                Component newComponent = new Component(
                        COMPONENT_NAME + i + adaptability,
                        random.nextDouble() * MAX_COST,
                        MIN_AVAILABILITY + (MAX_AVAILABILITY - MIN_AVAILABILITY) * random.nextDouble(),
                        true,
                        random.nextDouble(),
                        random.nextDouble(),
                        random.nextDouble());

                newArchitecture.addComponent(newComponent);

                newComponent.addProvidedService(new ProvidedService(SERVICE_NAME + i, random.nextDouble() * MAX_EXEC_TIME));

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
