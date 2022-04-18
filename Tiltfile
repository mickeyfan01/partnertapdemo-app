SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='your-registry.io/project/partnertapdemo-default')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='appnamespace')

k8s_custom_deploy(
    'partnertapdemo',
    apply_cmd="tanzu apps workload apply -f config/workload.yaml --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes >/dev/null" +
               " && kubectl get workload partnertapdemo --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('partnertapdemo', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'partnertapdemo'}])
allow_k8s_contexts('dttapdemo')