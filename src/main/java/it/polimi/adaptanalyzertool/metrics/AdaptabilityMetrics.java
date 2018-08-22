package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.AbstractService;
import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;

import java.util.HashSet;

import static it.polimi.adaptanalyzertool.metrics.ServicesMetrics.*;

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

    private AdaptabilityMetrics() {
    }

    /**
     * The Level of system adaptability (LSA) measures the number of components used to make up the system with respect
     * to the number of components that the most adaptable architecture would use.
     *
     * @param architecture the architecture to be analyzed.
     * @return the number of components used to make up the system with respect to the number of components that the most
     * adaptable architecture would use.
     */
    public static double LevelSystemAdaptability(Architecture architecture) {
        HashSet<ProvidedService> servicesHashSet = collectProvidedServices(architecture);
        int aas = 0;
        double n = 0;
        for (ProvidedService providedService : servicesHashSet) {
            aas += AbsoluteAdaptability(architecture, providedService);
        }
        for (Component component : architecture.getComponents()) {
            if (!component.getProvidedServices().isEmpty()) {
                n += 1;
            }
        }
        return aas / n;
    }

    /**
     * The Mean of absolute adaptability of services (MAAS) measures the mean number of used component per service.
     *
     * @param architecture the architecture to be analyzed.
     * @return the mean number of used component per service.
     */
    public static double MeanAbsoluteAdaptability(Architecture architecture) {
        HashSet<ProvidedService> servicesHashMap = collectProvidedServices(architecture);
        double numberOfProvidedServices = servicesHashMap.size();
        int aas = 0;
        for (ProvidedService providedService : servicesHashMap) {
            aas += AbsoluteAdaptability(architecture, providedService);
        }
        return aas / numberOfProvidedServices;
    }

    /**
     * The mean of relative adaptability of services (MRAS) represents the mean of RAS.
     *
     * @param architecture the architecture to be analyzed.
     * @return the mean of RAS.
     * @see ServicesMetrics#RelativeAdaptability(Architecture, AbstractService)
     */
    public static double MeanRelativeAdaptability(Architecture architecture) {
        HashSet<ProvidedService> servicesHashMap = collectProvidedServices(architecture);
        double numberOfProvidedServices = servicesHashMap.size();
        int ras = 0;
        for (ProvidedService providedService : servicesHashMap) {
            ras += RelativeAdaptability(architecture, providedService);
        }
        return ras / numberOfProvidedServices;
    }
}
