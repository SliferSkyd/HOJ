package com.sliferskyd.judgeservice;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

public class DockerExecutor {
    private DockerClient dockerClient;
    public DockerExecutor() {
        this.dockerClient = DockerClientBuilder.getInstance().build();
    }

    public void executeSubmission(String imageName, String command) throws InterruptedException {
        String containerId = dockerClient.createContainerCmd(imageName)
                .withCmd(command.split(" "))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerId).exec();

        long startTime = System.currentTimeMillis();

        while (dockerClient.inspectContainerCmd(containerId).exec().getState().getRunning()) {
            // Wait for the container to finish executing the submission
        }

        long endTime = System.currentTimeMillis();
        long timeConsumed = endTime - startTime;

        Statistics stats = dockerClient.statsCmd(containerId).exec(new StatsCallback()).awaitCompletion().getStats();

        long memoryConsumed = stats.getMemoryStats().getUsage();


        String logs = dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .exec(new LogToStringContainerCallback())
                .toString();

        System.out.println(logs);

        dockerClient.removeContainerCmd(containerId).exec();
    }
}

class LogToStringContainerCallback extends LogContainerResultCallback {
    protected final StringBuilder log = new StringBuilder();

    @Override
    public void onNext(Frame item) {
        log.append(new String(item.getPayload()));
    }

    @Override
    public String toString() {
        return log.toString();
    }
}

class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
    private Statistics stats;

    @Override
    public void onNext(Statistics stats) {
        this.stats = stats;
    }

    public Statistics getStats() {
        return stats;
    }
}