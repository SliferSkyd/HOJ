import docker
from docker.errors import ContainerError
import os
import argparse
import time
import threading

parser = argparse.ArgumentParser(description='Run Java submission')
parser.add_argument('--timeout', type=int, default=5, help='Timeout in seconds')
parser.add_argument('--memory_limit', type=str, default="512m", help='Memory limit for container')
args = parser.parse_args()

def run_java_submission(timeout=5, memory_limit="512m"):
    client = docker.from_env()

    try:
        # Define path to data directory
        data_dir = os.path.abspath("./JudgeService/src/main/java/com/sliferskyd/judgeservice/data")
        print(data_dir)
        # Run container
        container = client.containers.run(
            working_dir='/app',
            image="openjdk:8",
            volumes={data_dir: {'bind': '/app', 'mode': 'rw'}},
            command="bash -c 'javac Main.java && java Main < input.txt > output.txt'",
            mem_limit=memory_limit,
            detach=True,
            network_disabled=True,
        )


        exit_code, compile_output = container.exec_run("javac Main.java", tty=True, stdout=True)
        start_time = time.time()
        if exit_code == 0:
            print("Compilation successful")
        else:
            print("Compilation failed")
            # Print the compilation error message
            # print("Compilation error message:", compile_output.decode())
            return

        container.exec_run("java Main < input.txt > output.txt")
        response = container.wait(timeout=timeout)

        end_time = time.time()
        status_code = response['StatusCode']
        print(end_time - start_time)

        if status_code == 0:
            # Read output file and expected answer
            with open(os.path.join(data_dir, "output.txt"), "r") as output_file:
                output = output_file.read().strip()

            with open(os.path.join(data_dir, "answer.txt"), "r") as answer_file:
                correct_answer = answer_file.read().strip()
            print(output, correct_answer)
            # Check for correct answer
            if output == correct_answer:
                print("Correct Answer")
            else:
                print("Wrong Answer")
        else:
            print("Time Limit Exceeded")

    except docker.errors.APIError as e:
        if '409 Client Error: Conflict' in str(e):
            print("Memory Limit Exceeded")
        else:
            print("Docker API Error:", e)
    except Exception as e:
        print("Error:", e)
    finally:
        # Clean up: Stop and remove container
        try:
            container.stop()
            container.remove()
        except Exception as e:
            print("Cleanup Error:", e)

if __name__ == '__main__':
    run_java_submission(timeout=args.timeout, memory_limit=args.memory_limit)
