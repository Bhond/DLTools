package fr.pops.nn.neuralnetworks.networks;

public enum NeuralNetworkProperties {

    // Enum
    CURRENT_ERROR("CurrentError", 0),
    PRECISION("Precision", 1),
    RECALL("Recall", 2),
    F1("F1", 3);

    // Attributes
    private int id;
    private String name;

    // Ctor
    NeuralNetworkProperties(String name, int id){
        this.name = name;
        this.id = id;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
