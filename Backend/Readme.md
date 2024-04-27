<h1>HOJ - Hoang's Online Judge</h1>
<p>HOJ is a simple online judge system that can be used to test your code. It is written in Java and uses Java Spring Boot as the web framework. 
The system is designed to be simple and easy to use. It is not meant to be a full-featured online judge system, but rather a simple tool for testing code.</p>
<p>This online judge is also a project I have made to learn about Microservices, Docker and System Design.</p>

<h2> Services </h2>
<p>HOJ is made up of several microservices:</p>
<ol>
    <li><strong>Problem Management Service</strong></li>
    <ul>
        <li>Responsibilities: Manages problem statements, metadata (like difficulty, tags), and admin functionalities (like adding or updating problems).</li>
        <li>Database: Owns a database that stores all problem-related data.</li>
        <li>APIs: Provides APIs to add, retrieve, update, and delete problems.</li>
        <li>Technologies: Spring Boot, Spring Data JPA, MongoDB.</li>
        <li>Implementation: This service will handle CRUD operations for problem statements and associated data. It could use Spring Data JPA repositories to abstract database interactions.</li>
    </ul>
    <li><strong>User Management Service</strong></li>
    <ul>
        <li>Responsibilities: Handles user registrations, authentications, and profiles. It may also manage user roles and permissions.</li>
        <li>Database: Owns a database for storing user data.</li>
        <li>APIs: Provides APIs for user registration, login, password management, and profile updates.</li>
        <li>Technologies: Spring Boot, Spring Security, JWT, MySQL.</li>
        <li>Implementation: This service will manage user-related operations like registration, login, and profile updates. It could use Spring Security for authentication and JWT for token-based authorization.</li>
    </ul>
    <li><strong>Submission Service</strong></li>
    <ul>
        <li>Responsibilities: Manages code submissions by users. It queues submissions for processing and retrieves results.</li>
        <li>Queue: Utilizes a message queue to manage the load of incoming submissions to the execution engine.</li>
        <li>APIs: Provides APIs to submit code, check submission status, and fetch submission results.</li>
        <li>Technologies: Spring Boot, RabbitMQ, MySQL.</li>
        <li>Implementation: This service will handle submission-related operations like queuing submissions and fetching results. It could use RabbitMQ for message queuing and MySQL for storing submission data.</li>
    </ul>
    <li><strong>Execution Engine Service</strong></li>
    <ul>
        <li>Responsibilities: Compiles and runs user-submitted code securely. Returns the output and execution metrics (like time and memory usage).</li>
        <li>Sandboxing: Uses containerization or virtualization to safely execute user code.</li>
        <li>Communication: Communicates with the Submission Service via a message queue to receive tasks and send back results.</li>
        <li>Technologies: Docker, Java Security Manager.</li>
        <li>Implementation: This service will execute user-submitted code in a secure environment. It could use Docker containers or Java Security Manager to isolate code execution.</li>
    </ul>
    <li><strong>Storage Service</strong></li>
    <ul>
        <li>Responsibilities: Manages storage and retrieval of submission outputs, test cases, and other necessary data.</li>
        <li>Database/File Storage: Uses a combination of databases and file storage solutions for efficient data management.</li>
        <li>APIs: Provides APIs to upload, download, and manage files related to problems and submissions.</li>
        <li>Technologies: Spring Boot, Amazon S3, MySQL.</li>
        <li>Implementation: This service will handle file storage operations for problem statements, test cases, and submission outputs. It could use Amazon S3 for scalable file storage and MySQL for metadata storage.</li>
    </ul>
    <li><strong>Scoring and Leaderboard Service</strong></li>
    <ul>
        <li>Responsibilities: Calculates scores based on submission results and manages leaderboards.</li>
        <li>Database: Maintains a database for scores and leaderboard rankings.</li>
        <li>APIs: Provides APIs to fetch and update scores and leaderboards.</li>
        <li>Technologies: Spring Boot, Spring Data JPA, MySQL.</li>
        <li>Implementation: This service will calculate scores based on submission results and update leaderboard rankings. It could use Spring Data JPA repositories to interact with the database.</li>
    </ul>
    <li><strong>Notification Service</strong></li>
    <ul>
        <li>Responsibilities: Sends notifications to users regarding system events (e.g., contest start, score updates).</li>
        <li>Integration: Integrates with email services and possibly real-time messaging platforms.</li>
        <li>APIs: Provides APIs to manage and send notifications.</li>
        <li>Technologies: Spring Boot, JavaMail, WebSocket.</li>
        <li>Implementation: This service will handle sending notifications to users via email or real-time messaging. It could use JavaMail for email notifications and WebSocket for real-time messaging.</li>
    </ul>
    <li><strong>API Gateway</strong></li>
    <ul>
        <li>Functionality: Serves as the single entry point for all client requests. It routes requests to the appropriate services and handles cross-cutting concerns like SSL termination, authentication, API rate limiting, and logging.</li>
        <li>Technologies: Spring Cloud Gateway, Spring Security, JWT.</li>
        <li>Implementation: This service will act as a reverse proxy for routing requests to backend services. It could use Spring Cloud Gateway for request routing and Spring Security with JWT for authentication and authorization.</li>
    </ul>
    <li><strong>Frontend</strong></li>
    <ul>
        <li>Responsibilities: Provides the user interface for the system, making API calls to the backend through the API Gateway.</li>
        <li>Technologies: React, Angular, Vue.js.</li>
        <li>Implementation: This service will implement the user interface for the online judge system. It could use frontend frameworks like React, Angular, or Vue.js to build interactive web pages.</li>
    </ul>
</ol>

<h2> Infrastructure Considerations </h2>
<ul>
    <li><strong>Service Discovery</strong>: Implements a mechanism for services to dynamically discover each other (e.g., using Eureka, Consul).</li>
    <li><strong>Load Balancing</strong>: Balances the load across multiple instances of services to improve performance and availability.</li>
    <li><strong>Monitoring and Logging</strong>: Integrates tools like Prometheus, Grafana, and ELK stack for monitoring and logging to ensure system health and troubleshoot issues.</li>
</ul>