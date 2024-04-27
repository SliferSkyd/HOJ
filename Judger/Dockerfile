FROM ubuntu:20.04
WORKDIR /app
COPY . /app
RUN apt-get update && \
    apt-get install -y cmake make gcc g++ libc6-dev && \
    apt-get install -y libpthread-stubs0-dev libseccomp-dev && \
    apt-get install -y python3 python3-pip default-jdk && \
    rm -rf /var/lib/apt/lists/*
RUN mkdir build
WORKDIR /app/build
RUN cmake ..
RUN make
