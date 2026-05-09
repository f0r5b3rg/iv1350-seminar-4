package se.kth.iv1350.repairelectricbike.integration;

/**
 * Representing the different states a repair order can have. 
 * The state is used to track the progress of a repair order, from creation
 * to approval or rejection.
 */
public enum State {
    NEWLY_CREATED,
    READY_FOR_APPROVAL,
    ACCEPTED,
    DENIED
}
