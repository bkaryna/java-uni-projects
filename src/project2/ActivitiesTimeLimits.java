package project2;

public enum ActivitiesTimeLimits {
    SWIM_MIN(10),
    SWIM_MAX(30),
    BIKE_MIN(30),
    BIKE_MAX(80),
    RUN_MIN(15),
    RUN_MAX(60);

    public final Integer value;

    ActivitiesTimeLimits(Integer value) {
        this.value = value;
    }
}
