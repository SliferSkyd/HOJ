#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <sys/time.h>
#include <pthread.h>
#include <sys/wait.h>
#include <ctype.h>
#include "../child/child.h"
#include "../time/time.h"
#include "../system/system.h"
#include "judge.h"

void *monitorThread(void *timeoutKillerConfig) {
    struct timeoutKillerConfig *timeConf = (struct timeoutKillerConfig *) (timeoutKillerConfig);
    pid_t pid = timeConf->pid;
    int limitTime = timeConf->limitTime;
    sleep((unsigned int) (limitTime));
    killPid(pid, SIGKILL);
    return NULL;
}

int compareFile(const char *file1_path, const char *file2_path) {
    FILE *file1 = fopen(file1_path, "r");
    FILE *file2 = fopen(file2_path, "r");

    if (file1 == NULL || file2 == NULL) {
        fprintf(stderr, "Error opening files\n");
        return -1; // Return an error code indicating failure
    }

    int char1, char2;

    while (1) {
        char1 = fgetc(file1);
        char2 = fgetc(file2);
        if (char1 == EOF || char2 == EOF) break;
        // Skip whitespace characters
        while (isspace(char1)) {
            char1 = fgetc(file1);
        }
        while (isspace(char2)) {
            char2 = fgetc(file2);
        }
        if (char1 != char2) {
            fclose(file1);
            fclose(file2);
            return 1; // Return 1 to indicate files differ
        }
    }

    // Check if one file reached EOF before the other
    if (char1 != char2) {
        fclose(file1);
        fclose(file2);
        return 1; // Return 1 to indicate files differ
    }

    fclose(file1);
    fclose(file2);
    return 0; // Return 0 to indicate files are identical
}

enum RUNNING_CONDITION getRunningCondition(int status, struct execConfig *execConfig, struct judgeResult *judgeResult) {
    if (WIFEXITED(status)) {
        if (WEXITSTATUS(status) == 0) {
            int isMemoryExceeded = (unsigned long long) (judgeResult->memoryCost) > execConfig->memoryLimit;
            if (isMemoryExceeded) {
                return MEMORY_LIMIT_EXCEED;
            }
            int isCpuTimeExceeded = execConfig->cpuTimeLimit < judgeResult->cpuTimeCost;
            int isRealTimeExceeded = execConfig->realTimeLimit < judgeResult->realTimeCost;
            if (isCpuTimeExceeded || isRealTimeExceeded) {
                return TIME_LIMIT_EXCEED;
            }
            int isWrongAnswer = compareFile(execConfig->stdoutPath, execConfig->stdansPath);
            if (isWrongAnswer) {
                return WRONG_ANSWER;
            }
            return RUN_SUCCESS;
        }
        return UNKNOWN_ERROR;
    }

    if (WIFSIGNALED(status)) {
        if (WTERMSIG(status) == SIGXCPU) {
            return TIME_LIMIT_EXCEED;
        }
        if (WTERMSIG(status) == SIGFPE) {
            return FLOAT_ERROR;
        }
        if (WTERMSIG(status) == SIGSEGV) {
            return SEGMENTATION_FAULT;
        }
        if (WTERMSIG(status) == SIGKILL) {
            if (execConfig->cpuTimeLimit < judgeResult->cpuTimeCost) {
                return TIME_LIMIT_EXCEED;
            }
            return RUNTIME_ERROR;
        }
        if (WTERMSIG(status) == SIGXFSZ) {
            return OUTPUT_LIMIT_EXCEED;
        }
        return RUNTIME_ERROR;
    }
    return UNKNOWN_ERROR;
}

void runJudge(struct execConfig *execConfig, struct judgeResult *judgeResult) {
    struct timeval startTime, endTime;
    gettimeofday(&startTime, NULL);
    
    if (!isRoot()) {
        judgeResult->condition = NOT_ROOT_USER;
        return;
    }

    pid_t childPid = fork();
    pthread_t pthread = 0;

    if (childPid < 0) {
        judgeResult->condition = FORK_ERROR;
        return;
    }

    if (childPid == 0) {
        runChild(execConfig);
    }

    if (childPid > 0) {
        struct timeoutKillerConfig killerConfig;
        killerConfig.limitTime = execConfig->realTimeLimit / 1000;
        killerConfig.pid = childPid;

        int t = pthread_create(&pthread, NULL, monitorThread, (void *) (&killerConfig));
        if (t != 0) {
            judgeResult->condition = CREATE_THREAD_ERROR;
            return;
        }
        int status = 0;
        struct rusage costResource;

        wait4(childPid, &status, WSTOPPED, &costResource);

        pthread_cancel(pthread);
        gettimeofday(&endTime, NULL);

        judgeResult->cpuTimeCost = (int) getTimeMillisecondByTimeval(costResource.ru_utime);
        judgeResult->realTimeCost = getGapMillsecond(startTime, endTime);
        judgeResult->memoryCost = costResource.ru_maxrss;
        judgeResult->condition = getRunningCondition(status, execConfig, judgeResult);
    }
}

int compile(struct execConfig *execConfig) {
    if (execConfig->language == CPP) {
        if (system("g++ -o ../data/run ../data/main.cpp") != 0) return 0;
        return 1;
    } else if (execConfig->language == JAVA) {
        if (system("javac ../data/Main.java") != 0) return 0;
        system("echo '#!/bin/sh' > ../data/run");
        system("echo 'java Main' >> ../data/run");
        return 1;
    } else if (execConfig->language == PYTHON3) {
        system("echo '#!/bin/sh' > ../data/run");
        system("echo 'python3 ../data/main.py' >> ../data/run");
        return 1;
    }
    return -1;
}