package org.devops


//封装HTTP请求
def HttpReq(reqType,reqUrl,reqBody){
    def apiServer = "https://lb.kubesphere.local:6443/apis/apps/v1"
    withCredentials([kubeconfigFile(credentialsId: 'kubernetes-c', variable: 'KUBECONFIG')]) {
      result = httpRequest customHeaders: [[maskValue: false, name: 'Content-Type', value: 'application/yaml'],
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
//新建Deployment
def CreateDeployment(nameSpace,deployName,deplyBody){
    apiUrl = "namespaces/${nameSpace}/deployments/"
    response = HttpReq('POST',apiUrl,deplyBody)
    println(response)
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
