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

## Deploy another workload with different name and using different branch
- Checkout application code to diff branch e.g. shopping
```sh
git checkout -b shopping
```
- Update `workload.yaml` file to have diff name for this test application    
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
- Notice that the workload is created from same repository but with `shopping` branch
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
- At this point, we have two instance of application is running. One with `main` branch code which is treated as production grade app and another with `shopping` branch code where you will be doing the development work and testing


## Update changes for app running using shopping branch
- Let say that you are adding an additioanl controller
```java
	@RequestMapping("/shopping4")
	public String shopping4() {
		return "Hey Partners shopping4, you can do some shopping here and also win prices";
	}
```
- commit the code 
```sh
❯ git add src/main/java/com/partnertapdemo/partnertapdemo/HelloController.java
❯ git commit -m "new update"
[shopping 108d64e] new update
 2 files changed, 12 insertions(+), 1 deletion(-)
❯ git push origin shopping
Enumerating objects: 28, done.
Counting objects: 100% (28/28), done.
Delta compression using up to 10 threads
Compressing objects: 100% (9/9), done.
Writing objects: 100% (12/12), 1.18 KiB | 1.18 MiB/s, done.
Total 12 (delta 4), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (4/4), completed with 3 local objects.
To https://github.com/dineshtripathi30/partnertapdemo.git
   ceda605..108d64e  shopping -> shopping
```
- You will notice that the workload updation starts automatically
- optionally: You can also run an update manually by using the following command
```sh
❯ tanzu apps workload update -f config/workload.yaml
Update workload:
...
 28, 28   |      cpu: 100m
 29, 29   |      memory: 100Mi
 30, 30   |  serviceAccountName: default
 31, 31   |  source:
 32     - |    image: tanzudemoreg.azurecr.io/tap11/build-service/partnertapdemo-shopping-default:latest@sha256:19d4f82b16fbd8a0f3fc66f779a34aa1187f10ac99dbc2fd5e504583e882f6e8
     32 + |    git:
     33 + |      ref:
     34 + |        branch: shopping
     35 + |      url: https://github.com/dineshtripathi30/partnertapdemo

? Really update the workload "partnertapdemo-shopping"? Yes
Updated workload "partnertapdemo-shopping"
```
- Access both application url's and you will notice that `partnertapdemo-shopping` app responds you on `/shopping` but `partnertapdemo` does not. because changes are not yet promoted to the main branch yet.

## Promote changes to main branch
- Let's assume that the newly developed functionaly is working fine and now you want to promote the changes to main branch so that `partnertapdemo` app can get the latest changes
- Follow the standard PR process and merge the changes
- You will notice that the `partnertapdemo` app starts building using `clustersupplychain`, wait for it's completion
- Once completed, open the `partnertapdemo` app url and append `/shopping4`, you will now see the changes in your prod app



## Making more changes and testing them live on a shopping branch
TAP makes the live updates easier with the help of `Tilt`. You can create a `Tiltfile` and with the help of Tanzu plugin, you can apply the live changes. For this, deploy `partnertapdemo-shopping` application on a different namespace and `partnertapdemo` on a separate namespace. In this example, `partnertapdemo` is deployed on a `default` namespace and `partnertapdemo-shopping` is deployed on `shopping` namespace.

- Configure `Tiltfile` to reflect the `workload name`, `source image`, `namespace`, and `cluster name` correctly
- Update `workload.yaml` file and point it to the `shopping` namespace and also git branch to the `shopping`
- Deploy the `partnertapdemo-shopping` application using the following command
```sh
tanzu apps workload create -f config/workload.yaml
```
- Wait for the app to get up and running, Check the status by running the following command
```sh
tanzu apps workload list -n shopping
```

### Live Changes Test

- In order to test the changes live, right click on the `Tiltfile` and Click `Tanzu: Live Update Starts` option
- Make required changes in the code and Save it
- You will notice on the terminal that the update will start syncing up 

Once changes are tested successfully, you can promote the change to the main branch and workload will be updated automatically.