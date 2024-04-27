#include "time.h"

long getTimeMillisecondByTimeval(struct timeval timeval) {
    //  startTime.tv_sec : s
    //  startTime.tv_usec : us
    return timeval.tv_sec * 1000 + timeval.tv_usec / 1000;
}

int getGapMillsecond(struct timeval startTime, struct timeval endTime) {
    long startMillisecond = getTimeMillisecondByTimeval(startTime);
    long endMillisecond = getTimeMillisecondByTimeval(endTime);
    return (int) (endMillisecond - startMillisecond);
}




