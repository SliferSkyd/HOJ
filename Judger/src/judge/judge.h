#ifndef __JUDGE_H
#define __JUDGE_H

#include "../common/common.h"

void runJudge(struct execConfig *execConfig, struct judgeResult *judgeResult);
int compile(struct execConfig *execConfig);

#endif
