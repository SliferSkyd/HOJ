#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "common.h"
#include <string.h>

#define VALIDATE_CONFIG_ERROR 0
#define VALIDATE_SUCCESS 1

void initExecConfigAndJudgeResult(struct execConfig *execConfig, struct judgeResult *judgeResult) {
    execConfig->memoryLimit = MEMORY_LIMIT_DEFAULT;
    execConfig->cpuTimeLimit = TIME_LIMIT_DEFAULT;
    execConfig->realTimeLimit = WALL_TIME_DEFAULT;
    execConfig->processLimit = PROCESS_LIMIT_DEFAULT;
    execConfig->outputLimit = OUTPUT_LIMIT_DEFAULT;
    execConfig->wallMemoryLimit = WALL_MEMORY_DEFAULT;
    execConfig->uid = UID_DEFAULT;
    execConfig->guard = GUARD_DEFAULT;
    execConfig->execPath = "../data/run";
    execConfig->stderrPath = "../data/test.err";
    execConfig->stdoutPath = "../data/test.out";
    execConfig->stdinPath = "../data/test.in";
    execConfig->stdansPath = "../data/test.ans";
    judgeResult->condition = 1;
    judgeResult->memoryCost = 0;
    judgeResult->realTimeCost = 0;
    judgeResult->cpuTimeCost = 0;
}

int validateForExecConfig(struct execConfig *execConfig) {
    if (execConfig->cpuTimeLimit < 0
        || execConfig->memoryLimit < 1024
        || execConfig->realTimeLimit < 0
        || execConfig->processLimit < 0
        || execConfig->outputLimit < 0
        || execConfig->execPath[0] == '\0') {
        return VALIDATE_CONFIG_ERROR;
    }
    return VALIDATE_SUCCESS;
}

int getAndSetOptions(int argc, char *argv[], struct execConfig *execConfig) {
    int opt;
    while ((opt = getopt(argc, argv, "t:c:m:f:g:p:l:")) != -1) {
        switch (opt) {
            case 't':
                execConfig->realTimeLimit = atoi(optarg);
                break;
            case 'c':
                execConfig->cpuTimeLimit = atoi(optarg);
                break;
            case 'm':
                execConfig->memoryLimit = atoi(optarg);
                break;
            case 'f':
                execConfig->outputLimit = atoi(optarg);
                break;
            case 'g':
                execConfig->guard = atoi(optarg);
                break;
            case 'l':
                if (strcmp(optarg, "c++") == 0) {
                    execConfig->language = CPP;
                } else if (strcmp(optarg, "java") == 0) {
                    execConfig->language = JAVA;
                } else if (strcmp(optarg, "python") == 0) {
                    execConfig->language = PYTHON3;
                } else {
                    printf("Unknown language: %s\n", optarg);
                }
                break;

            default:
                printf("Unknown option: %c\n", (char) optopt);
                return 0;
        }
    }
    return 1;
}

void generateResult(struct execConfig *execConfig, struct judgeResult *judgeResult) {
    printf("timeUsed: %llu\n", judgeResult->realTimeCost);
    printf("memoryUsed: %llu\n", judgeResult->memoryCost);
    printf("status: %d\n", judgeResult->condition);
}