#include "guard.h"

int FORBIDDEN_LIST[] = {
        // 进程控制
        SCMP_SYS(fork),
        SCMP_SYS(clone),
        SCMP_SYS(vfork)
};


int addSeccompRules(scmp_filter_ctx ctx) {
    int len = sizeof(FORBIDDEN_LIST) / sizeof(int);
    for (int i = 0; i < len; i++) {
        if (seccomp_rule_add(ctx, SCMP_ACT_KILL, FORBIDDEN_LIST[i], 0) != 0) {
            return 0;
        }
    }
    return 1;
}

void setSeccompGuard() {
    scmp_filter_ctx ctx;
    ctx = seccomp_init(SCMP_ACT_ALLOW);
    if (!ctx) {
        //TODO：限制失效处理
    }
    addSeccompRules(ctx);
    seccomp_load(ctx);
}