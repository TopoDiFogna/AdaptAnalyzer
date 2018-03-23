package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;

import java.util.HashMap;

import static it.polimi.adaptanalyzertool.metrics.ServicesMetrics.collectProvidedServices;

/**
 * This class provides the methods to calculate all the adaptability metrics.
 * <p>
 * It has to be used as a static class since no object must be instantiated before calling the methods that are
 * all public and static.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public final class AdaptabilityMetrics {

    private AdaptabilityMetrics(){}

    /**
     * The Level of system adaptability (LSA) measures the number of components used to make up the system with respect
     * to the number of components that the most adaptable architecture would use.
     *
     * @param architecture the architecture to be analyzed.
     *
     * @return the number of components used to make up the system with respect to the number of components that the most
     * adaptable architecture would use.
     */
    public static double LevelSystemAdaptability(Architecture architecture) {
        HashMap<String, ProvidedService> servicesHashMap = collectProvidedServices(architecture);
        int aas = 0;
        int n = 0;
        for (ProvidedService providedService : servicesHashMap.values()) {
            aas += ServicesMetrics.AbsoluteAdaptability(architecture, providedService);
        }
        for (Component component : architecture.getComponents().values()) {
            if (!component.getProvidedServices().isEmpty()) {
                n += 1;
            }
        }
        return aas / n;
    }
}
