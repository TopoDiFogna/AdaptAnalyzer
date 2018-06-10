package it.polimi.adaptanalyzertool.model;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains all the information about a component for an architecture.
 * <p>
 * The components are the fundamental parts of an architecture since they define the architecture.
 * A component cannot be more than once in an architecture but can be part of multiple architectures at once.
 * </p>
 * <p>
 * Components should at least require or provide a service to be useful for an architecture.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Component {

    private final String name;
    private double cost;
    private double availability;
    private boolean used;
    private double colorRed;
    private double colorBlue;
    private double colorGreen;
    private double colorOpacity;
    private HashMap<String, ProvidedService> providedServices;
    private HashMap<String, RequiredService> requiredServices;

    /**
     * Basic constructor for the component. Only the mandatory attributes are required; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Used: True</li>
     * <li>Color: WHITE</li>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability) {
        this(name, cost, availability, true, 1, 1, 1, 1, new HashMap<>(), new HashMap<>());
    }

    /**
     * Basic constructor for the component. Only the mandatory attributes are required; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Color: WHITE</li>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     * @param used         if this component is used or not.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, boolean used) {
        this(name, cost, availability, used, 1, 1, 1, 1, new HashMap<>(), new HashMap<>());
    }


    /**
     * Only the mandatory attributes are required but color; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     * @param used         if this component is used or not.
     * @param colorRed     red component color for the component in the UI.
     * @param colorBlue    blue component color for the component in the UI.
     * @param colorGreen   green component color for the component in the UI.
     */
    public Component(String name, double cost, double availability, boolean used, double colorRed, double colorGreen, double colorBlue) {
        this(name, cost, availability, used, colorRed, colorGreen, colorBlue, 1, new HashMap<>(), new HashMap<>());
    }

    /**
     * Only the mandatory attributes are required but color; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     * @param used         if this component is used or not.
     * @param colorRed     red component color for the component in the UI.
     * @param colorBlue    blue component color for the component in the UI.
     * @param colorGreen   green component color for the component in the UI.
     * @param colorOpacity opacity component color for the component in the UI.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, boolean used, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity) {
        this(name, cost, availability, used, colorRed, colorGreen, colorBlue, colorOpacity, new HashMap<>(), new HashMap<>());
    }

    /**
     * All the attributes are required but Services Provided; Services Required are set to default values:
     * <ul>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name             the name of the component.
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param used             if this component is used or not.
     * @param colorRed         red component color for the component in the UI.
     * @param colorBlue        blue component color for the component in the UI.
     * @param colorGreen       green component color for the component in the UI.
     * @param colorOpacity     opacity component color for the component in the UI.
     * @param providedServices the services provided by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, boolean used, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity,
                     HashMap<String, ProvidedService> providedServices) {
        this(name, cost, availability, used, colorRed, colorGreen, colorBlue, colorOpacity, providedServices, new HashMap<>());
    }

    /**
     * Constructor for the component with all the attributes required.
     *
     * @param name             the name of the component.
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param used             if this component is used or not.
     * @param colorRed         red component color for the component in the UI.
     * @param colorBlue        blue component color for the component in the UI.
     * @param colorGreen       green component color for the component in the UI.
     * @param colorOpacity     opacity component color for the component in the UI.
     * @param providedServices the services provided by this component.
     * @param requiredServices the services required by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, boolean used, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity, HashMap<String, ProvidedService> providedServices, HashMap<String, RequiredService> requiredServices) {
        this.name = name;
        this.cost = cost;
        this.availability = availability;
        this.used = used;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.colorOpacity = colorOpacity;
        this.providedServices = providedServices;
        this.requiredServices = requiredServices;
    }

    /**
     * Gets the name of the current component.
     * <p>
     * The name identifies the component and acts as a key in the {@code HashMap} used in the implementation.
     * </p>
     *
     * @return the name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost for the current Component.
     *
     * @return the cost as a double for the current Component.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the availability for the current Component.
     *
     * @return the availability for the current component in the range 0-1.
     */
    public double getAvailability() {
        return availability;
    }

    /**
     * Tells if this component is used in the architecture.
     *
     * @return {@code true} if the component is actually used, {@code false} otherwise.
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Sets the component status, if it's used or no.
     *
     * @param used {@code true} if the component is used, {@code false} otherwise.
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Returns the color associated with this component.
     * <p>
     * Note that this is a shorthand for all the colors; if the single colors need to be retrieved then
     * use the color getters.
     * </p>
     *
     * @return a new {@link Color} associated with this component.
     */
    public Color getColor() {
        return new Color(colorRed, colorGreen, colorBlue, colorOpacity);
    }

    /**
     * Gets the services provided by the current Component.
     *
     * @return a {@code HashMap} containing the services provided by the current component.
     */
    public Set<ProvidedService> getProvidedServices() {
        return new HashSet<>(providedServices.values());
    }

    /**
     * Searches for a provided service in this component. It returns null if the provided service is not found.
     *
     * @param name the name of the provided service that has to be searched for.
     *
     * @return the provided service object if found, {@code null} otherwise.
     */
    public ProvidedService getSingleProvidedService(String name) {
        return providedServices.get(name);
    }

    /**
     * Returns a set of names that corresponds to all the provided service names.
     *
     * @return a set of string that corresponds to all the provided service names.
     */
    public Set<String> getProvidedServicesNames() {
        return providedServices.keySet();
    }

    /**
     * Adds a provided service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceProvided the provided service that has to be added to the current component.
     */
    public void addProvidedService(ProvidedService serviceProvided) {
        this.providedServices.put(serviceProvided.getName(), serviceProvided);
    }

    /**
     * Removes a provided service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceProvided the provided service to be removed from the component.
     */
    public void removeProvidedService(ProvidedService serviceProvided) {
        providedServices.remove(serviceProvided.getName(), serviceProvided);
    }

    /**
     * Removes all the provided services from the current component.
     */
    public void removeAllProvidedServices() {
        providedServices.clear();
    }

    /**
     * Gets the services required by the current Component.
     *
     * @return a {@code HashMap} containing the services required by the current component.
     */
    public Set<RequiredService> getRequiredServices() {
        return new HashSet<>(requiredServices.values());
    }

    /**
     * Searches for a required service in this component. It returns null if the required service is not found.
     *
     * @param name the name of the required service that has to be searched for.
     *
     * @return the required service object if found, {@code null} otherwise.
     */
    public RequiredService getSingleRequiredService(String name) {
        return requiredServices.get(name);
    }

    /**
     * Returns a set of names that corresponds to all the required service names.
     *
     * @return a set of string that corresponds to all the required service names.
     */
    public Set<String> getRequiredServicesNames() {
        return requiredServices.keySet();
    }

    /**
     * Adds a required service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceRequired the required service that has to be added to the current component.
     */
    public void addRequiredService(RequiredService serviceRequired) {
        this.requiredServices.put(serviceRequired.getName(), serviceRequired);
    }

    /**
     * Removes a required service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceRequired the required service to be removed from the component.
     */
    public void removeRequiredService(RequiredService serviceRequired) {
        requiredServices.remove(serviceRequired.getName(), serviceRequired);
    }

    /**
     * Removes a generic service from a component.
     *
     * @param service the service to be removed, the type of service must extend {@link AbstractService}
     *
     * @throws IllegalArgumentException if the service does not extend {@link AbstractService}
     */
    public void removeService(AbstractService service) {
        if (service instanceof ProvidedService) {
            removeProvidedService((ProvidedService) service);
        } else if (service instanceof RequiredService) {
            removeRequiredService((RequiredService) service);
        } else throw new IllegalArgumentException("Service " + service.getName() + " not recognized");
    }

    /**
     * Removes all the required services from the current component.
     */
    public void removeAllRequiredServices() {
        requiredServices.clear();
    }

    /**
     * Removes all the services, required and provided, from the current component.
     */
    public void removeAllServices() {
        removeAllProvidedServices();
        removeAllRequiredServices();
    }

    @Override
    public String toString() {
        return "Component " + name + ": " +
                "\n\tcost= " + cost +
                "\n\tavailability= " + availability +
                "\n\tused= " + used;
    }

    /**
     * Clones the component but maintains a reference to the same services.
     *
     * @return a clone of this component.
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Component clone() {
        return new Component(name, cost, availability, used, colorRed, colorGreen, colorBlue, colorOpacity, providedServices, requiredServices);
    }
}
