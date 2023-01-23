# azure-container-app

## Prerequisites
* [Java 14+](https://openjdk.org/install/)
* [Maven](https://maven.apache.org/install.html)
* [Azure CLI](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli)
* [Docker](https://docs.docker.com/get-docker/) 

## Get started
1. Update the variable `NAME` in the `setup.sh` file. Your resources and resource group will be prefixed with the name. 
2. Update the `setup.sh` file with the correct docker build command. Use `docker buildx build --platform linux/amd64` if you have a Mac with an ARM chip. Otherwise, use the standard `docker build` command.
3. Run the `setup.sh` script. The script will create a resource group and all the required services. It will also deploy the engine and the student-service.

## Resources
[Azure Container Apps overview](https://learn.microsoft.com/en-us/azure/container-apps/overview)\
[Azure Container Apps environments](https://learn.microsoft.com/en-us/azure/container-apps/environment)\
[Microservices with Azure Container Apps](https://learn.microsoft.com/en-us/azure/container-apps/microservices)\
[Connect applications in Azure Container Apps](https://learn.microsoft.com/en-us/azure/container-apps/connect-apps?tabs=bash)
