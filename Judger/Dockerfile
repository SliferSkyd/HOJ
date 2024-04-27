FROM ubuntu:latest
WORKDIR /app
COPY . /app
RUN apt-get update && \
    apt-get install -y cmake make gcc libc6-dev && \
    apt-get install -y libpthread-stubs0-dev libseccomp-dev && \
    rm -rf /var/lib/apt/lists/*
RUN mkdir build
WORKDIR /app/build
RUN cmake ..
RUN make
