package se.kth.iv1350.repairelectricbike.integration;

import java.util.Objects;

/**
 * Contains information about a repair task.
 */
public class RepairTaskDTO {
    private final String repairTaskDescription;
    private final double costToRepair;

    /**
     * Creates a new instance of a repair task.
     * 
     * @param repairTaskDescription The description of a repair task.
     * @param costToRepair          The cost for a repair task.
     */
    public RepairTaskDTO(String repairTaskDescription, double costToRepair) {
        this.repairTaskDescription = repairTaskDescription;
        this.costToRepair = costToRepair;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof RepairTaskDTO other))
            return false;

       return Objects.equals(repairTaskDescription, other.repairTaskDescription) &&
              costToRepair == other.costToRepair;               
    }

    /**
     * Get the value of repair task description
     * 
     * @return  the value of repair task description
     */
    public String getRepairTaskDescription() {
        return repairTaskDescription;
    }

    /**
     * Get the value of cost to repair.
     * 
     * @return  the value of cost to repair.
     */
    public double getCostToRepair() {
        return costToRepair;
    }
}
