cmake_minimum_required(VERSION 2.8)
project(judger C)
LINK_LIBRARIES("pthread")
LINK_LIBRARIES("seccomp")
set(CMAKE_C_FLAGS "-O3 -std=gnu99 -pie -fPIC -pthread")
add_executable(
        judger
        src/main.c
        src/common/common.c
        src/common/common.h
        src/time/time.c
        src/time/time.h
        src/system/system.c
        src/system/system.h
        src/child/child.c
        src/child/child.h
        src/judge/judge.c
        src/judge/judge.h
        src/guard/guard.c
        src/guard/guard.h)