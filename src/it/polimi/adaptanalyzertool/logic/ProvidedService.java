package it.polimi.adaptanalyzertool.logic;

/**
 * A service provided by a component, identified by its name.
 *
 * @see Component
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ProvidedService extends AbstractService {

    /**
     * Constructor for a Provided Service; only requires the service name.
     *
     * @param name the name of the service.
     */
    public ProvidedService(String name){
        super(name);
    }
}
