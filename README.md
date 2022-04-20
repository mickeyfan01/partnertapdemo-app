## Create an accelerator
```sh
kubectl apply -f partnertapdemo-accelerator.yaml -n accelerator-system
```

* You can now see the newly created acclerator on TAP GUI

* Create an application scaffold from app accelerator

## Work with application
- open application code folder with VSCode
- Create application workload with main branch
```sh
tanzu apps workload create -f config/workload.yaml --yes
Create workload:
      1 + |---
      2 + |apiVersion: carto.run/v1alpha1
      3 + |kind: Workload
      4 + |metadata:
      5 + |  labels:
      6 + |    app.kubernetes.io/part-of: partnertapdemo
      7 + |    apps.tanzu.vmware.com/has-tests: "true"
      8 + |    apps.tanzu.vmware.com/workload-type: web
      9 + |  name: partnertapdemo
     10 + |  namespace: default
     11 + |spec:
     12 + |  env:
     13 + |  - name: SPRING_PROFILES_ACTIVE
     14 + |    value: springdemo
     15 + |  params:
     16 + |  - name: annotations
     17 + |    value:
     18 + |      autoscaling.knative.dev/minScale: "1"
     19 + |  - name: gitops_ssh_secret
     20 + |    value: git-secret
     21 + |  resources:
     22 + |    limits:
     23 + |      cpu: "2"
     24 + |      memory: 1Gi
     25 + |    requests:
     26 + |      cpu: 100m
     27 + |      memory: 100Mi
     28 + |  serviceAccountName: default
     29 + |  source:
     30 + |    git:
     31 + |      ref:
     32 + |        branch: main
     33 + |      url: https://github.com/dineshtripathi30/partnertapdemo

Created workload "partnertapdemo"
```
- Check the status of workload and wait for it to become `Ready`
```sh
tanzu apps workload list
NAME                      APP                       READY   AGE
partnertapdemo            partnertapdemo            Ready   4m32s
```

## Start developing new functionality 
- Checkout application code to diff branch e.g. shopping
```sh
git checkout -b shopping
```
- Update `workload.yaml` file to have diff name for this test application
- Make the required changes to the application code. e.g. adding more controller inside `HelloController.java`    
- Deploy this test application (without impacting the production one running with main branch)
```sh
❯ tanzu apps workload create -f config/workload.yaml --yes
Create workload:
      1 + |---
      2 + |apiVersion: carto.run/v1alpha1
      3 + |kind: Workload
      4 + |metadata:
      5 + |  labels:
      6 + |    app.kubernetes.io/part-of: partnertapdemo-shopping
      7 + |    apps.tanzu.vmware.com/has-tests: "true"
      8 + |    apps.tanzu.vmware.com/workload-type: web
      9 + |  name: partnertapdemo-shopping
     10 + |  namespace: default
     11 + |spec:
     12 + |  env:
     13 + |  - name: SPRING_PROFILES_ACTIVE
     14 + |    value: springdemo
     15 + |  params:
     16 + |  - name: annotations
     17 + |    value:
     18 + |      autoscaling.knative.dev/minScale: "1"
     19 + |  - name: gitops_ssh_secret
     20 + |    value: git-secret
     21 + |  resources:
     22 + |    limits:
     23 + |      cpu: "2"
     24 + |      memory: 1Gi
     25 + |    requests:
     26 + |      cpu: 100m
     27 + |      memory: 100Mi
     28 + |  serviceAccountName: default
     29 + |  source:
     30 + |    git:
     31 + |      ref:
     32 + |        branch: shopping
     33 + |      url: https://github.com/dineshtripathi30/partnertapdemo

Created workload "partnertapdemo-shopping"
```
- Notice that the workload is created from same repository but with `shopping` branch and this application is having your newly developed controller
- View the list of workloads running now
```sh
❯ tanzu apps workload list
NAME                      APP                       READY   AGE
partnertapdemo            partnertapdemo            Ready   4m32s
partnertapdemo-shopping   partnertapdemo-shopping   Ready   5m3s
```
- Access both the applications. You can get the list of URL's by using the following command
```sh
❯ kubectl get serving
NAME                                                URL                                                                 READY   REASON
route.serving.knative.dev/partnertapdemo            http://partnertapdemo.default.tap11.tanzupartnerdemo.com            True    
route.serving.knative.dev/partnertapdemo-shopping   http://partnertapdemo-shopping.default.tap11.tanzupartnerdemo.com   True    
```
- You can verify both url's on the browser.

## Making more changes and testing them live on a shopping branch
- Configure `Tiltfile` to reflect the `workload name`, `source image`, `namespace`, and `cluster name` correctly