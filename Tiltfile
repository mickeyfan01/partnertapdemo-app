SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='your-registry.io/project/partnertapdemo-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='appnamespace')

k8s_custom_deploy(
    'tapdemo',
    apply_cmd="tanzu apps workload apply -f config/workload.yaml --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes >/dev/null" +
               " && kubectl get workload tapdemo --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('tapdemo', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'tapdemo'}])
allow_k8s_contexts('tripathid@vmware.com@eks-partnerse-w01-s005.us-east-2.eksctl.io')