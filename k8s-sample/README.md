# k8s-sample
### Spring on Kubernetes local multi node kind cluster 
https://spring.io/guides/topicals/spring-on-kubernetes/

#### Getting Started: start.spring.io
* curl https://start.spring.io/starter.tgz -d dependencies=webflux,actuator | tar -xzvf -
* mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=spring-k8s/gs-spring-boot-k8s
* docker images spring-k8s/gs-spring-boot-k8s
* docker run -p 8080:8080 --name gs-spring-boot-k8s -t spring-k8s/gs-spring-boot-k8s
* curl http://localhost:8080/actuator/health
* docker stop gs-spring-boot-k8s

#### On Kubernetes
* kind create cluster
* kubectl cluster-info --context kind-kind

#### Deploying to Kubernetes
* mkdir k8s
* kubectl create deployment gs-spring-boot-k8s --image spring-k8s/gs-spring-boot-k8s:snapshot -o yaml --dry-run=client > k8s/deployment.yaml
* kubectl create service clusterip gs-spring-boot-k8s --tcp 80:8080 -o yaml --dry-run=client > k8s/service.yaml
* docker tag spring-k8s/gs-spring-boot-k8s spring-k8s/gs-spring-boot-k8s:snapshot
* kind load docker-image spring-k8s/gs-spring-boot-k8s:snapshot
* kubectl apply -f ./k8s
* kubectl get all
* kubectl port-forward svc/gs-spring-boot-k8s 9090:80
* curl http://localhost:9090/actuator

#### Best Practices
* liveness
* probe
* graceful shutdown
* kubectl create configmap gs-spring-boot-k8s --from-file=./k8s/application.properties
* kubectl get configmap gs-spring-boot-k8s -o yaml
* volume
* kubectl apply -f ./k8s
* kubectl port-forward svc/gs-spring-boot-k8s 9090:80

#### Service Discovery and Load Balancing
* Deploy external service into cluster
<pre>
kustomize build "github.com/ryanjbaxter/k8s-spring-workshop/name-service/kustomize/multi-replica/" | kubectl apply -f -
</pre>
* kubectl get pods --selector app=k8s-workshop-name-service
<pre>
NAME                                         READY   STATUS    RESTARTS   AGE
k8s-workshop-name-service-56b986b664-6qt59   1/1     Running   0          7m26s
k8s-workshop-name-service-56b986b664-wjcr9   1/1     Running   0          7m26s
</pre>
* kubectl port-forward svc/k8s-workshop-name-service 9090:80
* curl http://localhost:9090
<pre>
HTTP/1.1 200
k8s-host: k8s-workshop-name-service-56b986b664-6qt59
Content-Type: text/plain;charset=UTF-8
Content-Length: 4
Date: Mon, 14 Sep 2020 15:37:51 GMT

Paul
</pre>
* mvnw clean spring-boot:build-image -Dspring-boot.build-image.imageName=spring-k8s/gs-spring-boot-k8s
* docker tag spring-k8s/gs-spring-boot-k8s:latest spring-k8s/gs-spring-boot-k8s:snapshot
* kind load docker-image spring-k8s/gs-spring-boot-k8s:snapshot
* To deploy new changes quickly, kubernetes will automatically create new pod
<pre>kubectl delete pod --selector app=gs-spring-boot-k8s</pre>
* kubectl port-forward svc/gs-spring-boot-k8s 9090:80
* curl http://localhost:9090
<pre>Hello Paul from k8s-workshop-name-service-56b986b664-wjcr9</pre>
* Watch command equivalent in windows
<pre>for /l %g in () do @(curl localhost:9090 & timeout /t 1)</pre>
* To quickly see request serve from another pod for external service 
<pre>kubectl delete pod k8s-workshop-name-service-56b986b664-wjcr9</pre>