param location string = resourceGroup().location
param identityName string
param acrName string
param environmentName string
param name string
param version string
param external bool
param environmentVariables array

resource environment 'Microsoft.App/managedEnvironments@2022-03-01' existing = {
  scope: resourceGroup()
  name: environmentName
}

resource acr 'Microsoft.ContainerRegistry/registries@2021-09-01' existing = {
  scope: resourceGroup()
  name: acrName
}

resource identity 'Microsoft.ManagedIdentity/userAssignedIdentities@2022-01-31-preview' existing = {
  scope: resourceGroup()
  name: identityName
}

resource app 'Microsoft.App/containerApps@2022-03-01' = {
  name: name
  location: location
  identity: {
    type: 'UserAssigned'
    userAssignedIdentities: {
      '${identity.id}': {}
    }
  }
  properties: {
    managedEnvironmentId: environment.id
    configuration: {
      ingress: {
        external: external
        targetPort: 8080
      }
      activeRevisionsMode: 'Single'
      registries: [
        {
          server: acr.properties.loginServer
          identity: identity.id
        }
      ]
    }
    template: {
      containers: [
        {
          image: '${acr.name}.azurecr.io/${name}:${version}'
          name: name
          resources: {
            cpu: json('0.5')
            memory: '1.0Gi'
          }
          env: environmentVariables
        }
      ]
      scale: {
        minReplicas: 1
        maxReplicas: 1
      }
    }
  }
}
