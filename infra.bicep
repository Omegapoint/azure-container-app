param location string = resourceGroup().location
param appName string

resource acr 'Microsoft.ContainerRegistry/registries@2021-09-01' = {
  name: '${appName}acr'
  location: location
  sku: {
    name: 'Basic'
  }
  properties: {
    publicNetworkAccess: 'Enabled'
    adminUserEnabled: false
  }
}

resource identity 'Microsoft.ManagedIdentity/userAssignedIdentities@2022-01-31-preview' = {
  name: '${appName}-identity'
  location: location
}

@description('This is the built-in AcrPull role. See https://learn.microsoft.com/sv-se/azure/role-based-access-control/built-in-roles#acrpull')
resource acrPullRoleDefinition 'Microsoft.Authorization/roleDefinitions@2018-01-01-preview' existing = {
  scope: subscription()
  name: '7f951dda-4ed3-4680-a7ca-43fe172d538d'
}

resource roleAssignment 'Microsoft.Authorization/roleAssignments@2020-04-01-preview' = {
  name: guid(subscription().id, identity.id, acrPullRoleDefinition.id, acr.id)
  scope: acr
  properties: {
    roleDefinitionId: acrPullRoleDefinition.id
    principalId: identity.properties.principalId
    principalType: 'ServicePrincipal'
  }
}

resource logAnalyticsWorkspace 'Microsoft.OperationalInsights/workspaces@2020-08-01' = {
  name: appName
  location: location
  properties: {
    sku: {
      name: 'PerGB2018'
    }
  }
}

resource vnet 'Microsoft.Network/virtualNetworks@2020-07-01' = {
  name: appName
  location: location
  properties: {
    addressSpace: {
      addressPrefixes: [
        '10.0.0.0/8'
      ]
    }
    subnets: [
      {
        name: 'azure-container-apps'
        properties: {
          addressPrefix: '10.0.0.0/16'
        }
      }
    ]
  }
}

resource environment 'Microsoft.App/managedEnvironments@2022-03-01' = {
  name: appName
  location: location
  properties: {
    vnetConfiguration: {
      internal: false
      infrastructureSubnetId: vnet.properties.subnets[0].id
    }
    appLogsConfiguration: {
      destination: 'log-analytics'
      logAnalyticsConfiguration: {
        customerId: logAnalyticsWorkspace.properties.customerId
        sharedKey: logAnalyticsWorkspace.listKeys().primarySharedKey
      }
    }
  }
}

output environmentName string = environment.name
output environmentDefaultDomain string = environment.properties.defaultDomain
output acrName string = acr.name
output identityName string = identity.name
