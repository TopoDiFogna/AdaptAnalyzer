package it.polimi.adaptanalyzertool;

import it.polimi.adaptanalyzertool.metrics.ServicesMetrics;
import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;

public class Main {

    public static void main(String[] args) {
        ProvidedService c11s1 = new ProvidedService("s1", 1);
        RequiredService c11s2 = new RequiredService("s2", 0.8, 1);
        RequiredService c11s3 = new RequiredService("s3", 1, 2);
        Component c11 = new Component("c11", 2, 0.93);
        c11.addProvidedService(c11s1);
        c11.addRequiredService(c11s2);
        c11.addRequiredService(c11s3);

        ProvidedService c12s1 = new ProvidedService("s1", 3);
        ProvidedService c12s2 = new ProvidedService("s2", 3);
        RequiredService c12s3 = new RequiredService("s3", 0.5, 2);
        RequiredService c12s4 = new RequiredService("s4", 0.9, 3);
        Component c12 = new Component("c12", 8, 0.85);
        c12.addProvidedService(c12s1);
        c12.addProvidedService(c12s2);
        c12.addRequiredService(c12s3);
        c12.addRequiredService(c12s4);

        ProvidedService c31s3 = new ProvidedService("s3", 1.5);
        Component c31 = new Component("c31", 4, 0.9);
        c31.addProvidedService(c31s3);

        ProvidedService c41s3 = new ProvidedService("s4", 2);
        Component c41 = new Component("c41", 4, 0.92);
        c41.addProvidedService(c41s3);

        Architecture architecture = new Architecture("test");
        architecture.addComponent(c11);
        architecture.addComponent(c12);
        architecture.addComponent(c31);
        architecture.addComponent(c41);

        System.out.print("S1: " + ServicesMetrics.NumberOfExecutions(architecture, c11s1) + "\n");
        System.out.print("S2: " + ServicesMetrics.NumberOfExecutions(architecture, c11s2) + "\n");
        System.out.print("S3: " + ServicesMetrics.NumberOfExecutions(architecture, c11s3) + "\n");
        System.out.print("S4: " + ServicesMetrics.NumberOfExecutions(architecture, c12s4) + "\n");

        System.out.print("S1: " + ServicesMetrics.ProbabilityToBeRunning(architecture, c11s1) + "\n");
        System.out.print("S2: " + ServicesMetrics.ProbabilityToBeRunning(architecture, c11s2) + "\n");
        System.out.print("S3: " + ServicesMetrics.ProbabilityToBeRunning(architecture, c11s3) + "\n");
        System.out.print("S4: " + ServicesMetrics.ProbabilityToBeRunning(architecture, c12s4) + "\n");
    }
}
