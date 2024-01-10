# Pega architecture
- https://docs-previous.pega.com/client-managed-cloud/86/understanding-pega-deployment-architecture
1. Pega Platform是Distribute system
    - 多个跨计算节点的计算资源,用不同的节点Node,通过公共网络进行通讯
    - 节点分类
        1. Exposed Nodes 与负载均衡器交互
        2. Background Node
        3. Stateful Set
            - 内部嵌入的kafka等等
    - 
2. Pega的各个node以及数据库之间相互通讯组成PegaCluster
3. 负载均衡器(LoadBalancer)和Kubernetes以连接公开节点
    - http://www.52im.net/thread-2494-1-1.html
    - LB用于将请求或流量分配给多台节点设备上分别处理,将结果汇总返回给用户
        - 分配大量的并发处理
        - 分割单个繁重工作
    - K8s被映射到Pega上进行部署,
# 
1. Pega节点
    - 使用PegaDocker映象构建
    1. `三层`Pega Node
        - 向负载均衡器公开,load balancer
        1. Web
            - foreground或者WebUser
        2. Batch
            - 包含没有向LB公开节点
            - background” type node classification. This tier handles workloads for BackgroundProcessing, ADM, Batch, RealTime, RTDG, Custom1, Custom2, Custom3, Custom4, Custom5, and BIX processing.
        3. Stream
            - 用于跑一个内置的Kafka服务
2. 结构图
    - ![Alt text](image.png)
# Helm chart
- 在每一层提供一组基本节点,Pega部署在这个上面
- 指定部署中的层数来自定义Helm chart
# 自定义其他服务的部署
1. Elasticsearch
    - 使用Pega提供的含有ES插件的Docker映象来包含此服务
    - 使用SRS节点优化Elasticsearch
2. Cassandra
    1. Pega的决策营销功能需要这个部署
    2. 使用Helmchart将Dockerhub中的公开影响用于部署标准Cassandra
# 用于编排Pega平台部署的Kubernete
0. 文档
    - https://kubernetes.io/docs/home/
1. Pod
    - 一个或多个容器的可拓展但愿
    - Pod可以配置为Pega要用的Docker镜像
2. k8s对象的分类
    1. Kubernetes replica set
    2. K8s service
    3. k8s ingress
    4. k8s Configmap