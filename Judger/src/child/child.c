#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "child.h"

#include "../guard/guard.h"

#define CHILD_EXIT exit

void setLimitation(struct execConfig *execConfig) {
    struct rlimit maxMemory;
    // kb to bytes
    maxMemory.rlim_cur = maxMemory.rlim_max = (execConfig->wallMemoryLimit) * 1024;

    if (setrlimit(RLIMIT_AS, &maxMemory) != 0) {
        CHILD_EXIT(SET_LIMIT_ERROR);
    }

    struct rlimit maxTime;
    maxTime.rlim_cur = maxTime.rlim_max = (execConfig->cpuTimeLimit / 1000) + 1;
    if (setrlimit(RLIMIT_CPU, &maxTime) != 0) {
        CHILD_EXIT(SET_LIMIT_ERROR);
    }

    struct rlimit maxProcessAmount;
    maxProcessAmount.rlim_cur = maxProcessAmount.rlim_max = execConfig->processLimit;
    if (setrlimit(RLIMIT_NPROC, &maxProcessAmount) != 0) {
        CHILD_EXIT(SET_LIMIT_ERROR);
    }

    struct rlimit maxOutput;
    maxOutput.rlim_cur = maxOutput.rlim_max = execConfig->outputLimit;
    if (setrlimit(RLIMIT_FSIZE, &maxOutput) != 0) {
        CHILD_EXIT(SET_LIMIT_ERROR);
    }

    struct rlimit maxStack;
    maxStack.rlim_cur = maxStack.rlim_max = RLIM_INFINITY;
    if (setrlimit(RLIMIT_STACK, &maxStack) != 0) {
        CHILD_EXIT(SET_LIMIT_ERROR);
    }

}

void runChild(struct execConfig *execConfig) {
    FILE *inputFile = NULL;
    FILE *outputFile = NULL;
    FILE *errFile = NULL;

    if (execConfig->stdinPath[0] != '\0') {
        inputFile = fopen(execConfig->stdinPath, "r");
        if (!inputFile) {
            CHILD_EXIT(INPUT_FILE_NOT_FOUND);
        }
        int f = fileno(inputFile);
        dup2(f, STDIN_FILENO);
    }

    if (execConfig->stdoutPath[0] != '\0') {
        outputFile = fopen(execConfig->stdoutPath, "w");
        if (!outputFile) {
            CHILD_EXIT(CAN_NOT_MAKE_OUTPUT);
        }
        int f2 = fileno(outputFile);
        dup2(f2, STDOUT_FILENO);
    }

    if (execConfig->stderrPath[0] != '\0') {
        errFile = fopen(execConfig->stderrPath, "w");
        if (!errFile) {
            CHILD_EXIT(CAN_NOT_MAKE_OUTPUT);
        }
        int f3 = fileno(errFile);
        dup2(f3, STDERR_FILENO);
    }

    setLimitation(execConfig);

    if (execConfig->uid > 0) {
        if (setuid(execConfig->uid) == -1) {
            CHILD_EXIT(RUNTIME_ERROR);
        }
    }

    if (execConfig->guard) {
        setSeccompGuard();
    }

    char *envp[] = {"PATH=/bin", 0};
    execve(execConfig->execPath, NULL, envp);
    CHILD_EXIT(EXIT_SUCCESS);
}