package org.devops


//封装HTTP请求
def HttpReq(reqType,reqUrl,reqBody){
    def apiServer = "https://lb.kubesphere.local:6443/apis/apps/v1"
    withCredentials([string(credentialsId: 'kubernetes-token', variable: 'kubernetestoken')]) {
      result = httpRequest customHeaders: [[maskValue: true, name: 'Authorization', value: "Bearer ${kubernetestoken}"],
                                           [maskValue: false, name: 'Content-Type', value: 'application/yaml'], 
                                           [maskValue: false, name: 'Accept', value: 'application/yaml']], 
                httpMode: reqType, 
                consoleLogResponseBody: true,
                ignoreSslErrors: true, 
                validResponseCodes: '200:502',
                requestBody: reqBody,
                url: "${apiServer}/${reqUrl}"
                //quiet: true
    }
    return result
}
def HttpReq2(reqType,reqUrl,reqBody){
    def apiServer = "https://lb.kubesphere.local:6443/apis/apps/v1"
    withCredentials([string(credentialsId: 'kubernetes-token', variable: 'kubernetestoken')]) {
      result = httpRequest customHeaders: [[maskValue: true, name: 'Authorization', value: "Bearer ${kubernetestoken}"],
                                           [maskValue: false, name: 'Content-Type', value: 'application/strategic-merge-patch+json'], 
                                           [maskValue: false, name: 'Accept', value: 'application/strategic-merge-patch+json']], 
                httpMode: reqType, 
                consoleLogResponseBody: true,
                ignoreSslErrors: true, 
                validResponseCodes: '200:502',
                requestBody: reqBody,
                url: "${apiServer}/${reqUrl}"
                //quiet: true
    }
    return result
}

//新建Deployment
def CreateDeployment(nameSpace,deployName,deplyBody){
    apiUrl = "namespaces/${nameSpace}/deployments/"
    response = HttpReq('POST',apiUrl,deplyBody)
    def dresponse = readYaml text: "${response.content}"
    comid = dresponse['spec']['template']['spec']['containers']['image']
    
    
    
    println(comid)
    //println("Content: "+response.content)

   // println(response)
    
}

//删除deployment
def DeleteDeployment(nameSpace,deployName){
    apiUrl = "namespaces/${nameSpace}/deployments/${deployName}"
    response = HttpReq('DELETE',apiUrl,deplyBody)
    println(response)
}

//更新Deployment
def UpdateDeployment(nameSpace,deployName,deplyBody){
    apiUrl = "namespaces/${nameSpace}/deployments/${deployName}"
    response = HttpReq('PUT',apiUrl,deplyBody)
    println(response)
}

//获取Deployment
def GetDeployment(nameSpace,deployName){
    apiUrl = "namespaces/${nameSpace}/deployments/${deployName}"
    response = HttpReq('GET',apiUrl,'')
    return response
}
def PatchUpdateDeployment(nameSpace,deployName,deplyBody){
    apiUrl = "namespaces/${nameSpace}/deployments/${deployName}"
    response = HttpReq2('PATCH',apiUrl,deplyBody)
    println(response)
}
