#include <unistd.h>
#include <signal.h>
#include "system.h"

int killPid(pid_t pid, int killType) {
    // kill pid -9 cannot be caught or ignored
    int isKill = kill(pid, killType);
    return isKill;
}

int isRoot() {
    uid_t uid = getuid();
    return uid == 0;
}