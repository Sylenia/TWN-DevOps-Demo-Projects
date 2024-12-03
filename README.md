# System Health Checker

## Purpose
The **System Health Checker** is a simple Java application designed to:
- Provide real-time system information, such as:
  - Operating system details.
  - CPU specifications and usage.
  - Total and available memory.
- Host a lightweight web interface via SparkJava, allowing users to view system stats in their browsers.

### Features
- Dynamic HTML interface to display system health information.
- Built using Java, Gradle, and OSHI (Open Source Hardware Info) for hardware interaction.
- Configured for deployment on a DigitalOcean droplet.

### Use Case
This project serves as a demonstration of deploying a Gradle-based Java application on a Linux server. It showcases:
1. Setting up a DigitalOcean droplet.
2. Configuring secure server access.
3. Packaging and deploying a Java application with dependencies.
4. Running the app with external web access.

## Setup and Deployment

### Prerequisites
1. **DigitalOcean Account**: Create an account on [DigitalOcean](https://www.digitalocean.com/).
2. **SSH Key**: Ensure you have an SSH key pair. If not, generate one:
   ```bash
   ssh-keygen -t ed25519 -C "your_email@example.com"
3. Note the droplet’s IP address (e.g., `167.172.173.200`).

## Step 2: Connect to the Droplet
1. **Use SSH to connect to the droplet as `root`**:
   ```bash
   ssh root@167.172.173.200
   ```

2. **Update and upgrade the system**:
   ```bash
   sudo apt update && sudo apt upgrade -y
   ```

3. **Install Java**:
   ```bash
   sudo apt install openjdk-21-jdk -y
   ```

## Step 3: Configure a New User
1. **Create a new user `devuser`**:
   ```bash
   sudo adduser devuser
   ```

2. **Grant `sudo` privileges to the new user**:
   ```bash
   sudo usermod -aG sudo devuser
   ```

## Step 4: Build and Deploy the Application
1. **Build the application on your local machine**:
   ```bash
   ./gradlew clean shadowJar
   ```
   **Note**: 
   Used shadowJar to create a fat JAR that includes all dependencies, ensuring the application is self-contained and easy to deploy.

2. **Transfer the JAR file to the droplet as `root`**:
   ```bash
   scp app/build/libs/app.jar root@167.172.173.200:/root/
   ```

3. **Move the file to `devuser`’s home directory**:
   ```bash
   ssh root@167.172.173.200
   mv /root/app.jar /home/devuser/
   chown devuser:devuser /home/devuser/app.jar
   ```

## Step 5: Run the Application
1. **Switch to `devuser`**:
   ```bash
   su - devuser
   ```

2. **Navigate to the directory containing the JAR file**:
   ```bash
   cd /home/devuser
   ```

3. **Run the application**:
   ```bash
   java -jar app.jar
   ```

4. **Access the application in browser using the droplet’s IP**:
   ```
   http://167.172.173.200:8080
   ```

## Optional:
**Run the app in the background**:
```bash
nohup java -jar app.jar > app.log 2>&1 &
```

**View the logs**:
```bash
tail -f app.log
```