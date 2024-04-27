#ifndef Y_JUDGER_COMMON_H
#define Y_JUDGER_COMMON_H

#include <stdio.h>
#include <unistd.h>
#include <sys/resource.h>

enum RUNNING_CONDITION {
    RUN_SUCCESS = 0,
    WRONG_ANSWER,
    RUNTIME_ERROR,
    TIME_LIMIT_EXCEED,
    MEMORY_LIMIT_EXCEED,
    OUTPUT_LIMIT_EXCEED,
    SEGMENTATION_FAULT,
    FLOAT_ERROR,
    UNKNOWN_ERROR,
    INPUT_FILE_NOT_FOUND,
    CAN_NOT_MAKE_OUTPUT,
    SET_LIMIT_ERROR,
    NOT_ROOT_USER,
    FORK_ERROR,
    CREATE_THREAD_ERROR,
    VALIDATE_ERROR
};

enum EXEC_SETTING_DEFAULT {
    TIME_LIMIT_DEFAULT = 15000,
    MEMORY_LIMIT_DEFAULT = 1024 * 64,
    WALL_MEMORY_DEFAULT = 1024 * 1024 * 3L,
    WALL_TIME_DEFAULT = 15000,
    PROCESS_LIMIT_DEFAULT = 100,
    OUTPUT_LIMIT_DEFAULT = 20000,
    UID_DEFAULT = 3000,
    GUARD_DEFAULT = 0
};

struct timeoutKillerConfig {
    int pid;
    int limitTime;
};

struct execConfig {
    rlim_t cpuTimeLimit;
    rlim_t memoryLimit;
    rlim_t wallMemoryLimit;
    rlim_t processLimit;
    rlim_t outputLimit;
    rlim_t realTimeLimit;
    char *execPath;
    char *stdinPath;
    char *stdoutPath;
    char *stderrPath;
    char *stdansPath;
    uid_t uid;
    int guard;
};

struct judgeResult {
    rlim_t realTimeCost;
    rlim_t memoryCost;
    rlim_t cpuTimeCost;
    int condition;
};

void initExecConfigAndJudgeResult(struct execConfig *execConfig, struct judgeResult *judgeResult);

int validateForExecConfig(struct execConfig *execConfig);

int getAndSetOptions(int argc, char *argv[], struct execConfig *execConfig);

void generateResult(struct execConfig *execConfig, struct judgeResult *judgeResult);

#endif
