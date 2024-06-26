#include "common/common.h"
#include "judge/judge.h"


int main(int argc, char *argv[]) {
    struct execConfig execConfig;
    struct judgeResult judgeResult;
    initExecConfigAndJudgeResult(&execConfig, &judgeResult);
    if (getAndSetOptions(argc, argv, &execConfig)) {
        if (validateForExecConfig(&execConfig)) {
            if (!compile(&execConfig)) {
                judgeResult.condition = COMPILE_ERROR;
            } else {
                runJudge(&execConfig, &judgeResult);
            }
        } else {
            judgeResult.condition = VALIDATE_ERROR;
        }
    }
    generateResult(&execConfig, &judgeResult);
    return 0;
}