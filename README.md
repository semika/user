Building the module
=========================

Navigate the project root directory.

> CD user
> mvn clean install

The above commands will create 'user-0.0.1-SNAPSHOT.jar' file inside the 'target' folder. This sprig boot application can be accessed
through the 9005 port. 

In order to run the .jar file, execute the following command.

> java -jar target/user-0.0.1-SNAPSHOT.jar

This spring boot application will start to run within a embedded Tomcat server which accessible via 9005 port. In order to access the user list, 
access the following URL.

http://localhost:9005/user/list



Building Docker Image
===========================

Run the following command inside the root folder.

> docker build -t user:1.0 .

The above command will create user image with tag 1.0. In order to see the docker images, run the following command.

> docker image ls


When running the docker image, need to map container's 9005 port to any port on the host machine. Run the following command
to run the docker container.

> docker run --name user -p 9005:9005 user:1.0

The above command will start the docker container. 

> docker ps -a

The above command will list of all the containers running on the host machine.


Again, in order to access the user service, access the following URL through the browser. 

http://localhost:9005/user/list


Deploying application into K8s cluster
============================================

Run the following command from there folder where 'user.yml' is,

> kubectl apply -f user.yml


The user service has been exposed to the out side via 30005 port.

Service can be access via the following URL

http://localhost:30005/user/list


################################# Cloud Setup From Scratch #######################################

(1) Create VPC stack
    
   > aws cloudformation create-stack --stack-name IitAseVpcStack --template-body file://aws-cf-vpc-with-three-subnet.yml --capabilities CAPABILITY_NAMED_IAM

(2) Create DB stack

   > aws cloudformation create-stack --stack-name IitAseDBStack --template-body file://aws-cf-db.yml
   > mysql -u root -h asecw.cxlnkgitspbn.ap-southeast-1.rds.amazonaws.com  -p asecw < db.sql
   

(3) Create EKS Cluster stack

   > aws cloudformation create-stack --stack-name IitAseEKSStack --capabilities CAPABILITY_NAMED_IAM --template-body file://aws-cf-eks.yml

(4) Configure EKS cluster in order to access from local computer

   > aws eks --region ap-southeast-1 update-kubeconfig --name IIT-ASE-EKS-Cluster

(6) Update EKS cluster config map in order allow access to different roles and users. 

   For example, IIT-ASE-CW-EKSAccessToCodeBuildRole is the role who is going to access EKS from code build, need to add that role into 'aws-auth' config map.

   For example, 'semika' is an IAM user, in order to allow access to 'semika', Admin need to allow access for that particular IAM user. Need to add that user into 'aws-auth' config map.

   So, just after creating a new cluster, admin of the cluster needs to give enough permisstion by updating 'aws-auth' config map.

   > kubectl apply -f aws-auth.yml

(0) Setup AWS ALB

(6) Create a OIDC provider for the cluster

   Go to the console and delete OIDC provider

   To check cluster has OIDC provider
   
     > aws eks describe-cluster --name IIT-ASE-EKS-Cluster --query "cluster.identity.oidc.issuer" --output text

     Output: https://oidc.eks.us-west-2.amazonaws.com/id/EXAMPLED539D4633E53DE1B716D3041E

   Then run
     > aws iam list-open-id-connect-providers | grep <EXAMPLED539D4633E53DE1B716D3041E>

   If you have output like '"Arn": "arn:aws:iam::111122223333:oidc-provider/oidc.eks.us-west-2.amazonaws.com/id/EXAMPLED539D4633E53DE1B716D3041E"', which means OIDC is attached with cluster

   If no output, run the following command to create new OIDC provider and attach it to cluster

   > eksctl utils associate-iam-oidc-provider --region ap-southeast-1 --cluster IIT-ASE-EKS-Cluster --approve

(7) Create an IAM Policy Document - If already exist, no need to create again
    TODO -> Move this to CF template

   > aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam-policy.json

   To delete the policy

   > aws iam delete-policy --policy-name AWSLoadBalancerControllerIAMPolicy

(7) Update IIT-ASE-CW-AWSLoadBalancerControllerIAMRole for trust relationships.

    Since, with new cluster, we need to create new OIDC provider, new ARN will get.
    Need to update turst relationshipt or assume policy of this role with new OIDC arn.

    Latest: This creates the 'IIT-ASE-CW-AWSLoadBalancerControllerIAMRole' by assigning the newly created OIDC provide url as a trust relationship

    > aws cloudformation create-stack --stack-name IitAseEksOidcRoleStack --template-body file://aws-cf-create-alb-controller-role-with-eks-oidc.yml --parameters ParameterKey=EKSClusterName,ParameterValue=IIT-ASE-EKS-Cluster --capabilities CAPABILITY_NAMED_IAM --region ap-southeast-1



(8) Create an IAM role with above policy. This step is no need.
    Doc reference: https://docs.aws.amazon.com/eks/latest/userguide/cni-iam-role.html

   > eksctl create iamserviceaccount \
          --cluster=IIT-ASE-EKS-Cluster \
          --namespace=kube-system \
          --name=aws-load-balancer-controller \
          --attach-policy-arn=arn:aws:iam::762002331286:policy/AWSLoadBalancerControllerIAMPolicy \
          --approve \
          --override-existing-serviceaccounts

    To delete service accounts

    > eksctl delete iamserviceaccount --cluster IIT-ASE-EKS-Cluster --name aws-load-balancer-controller

(9) Create certificates - Clean up explicitly before deleting EKS cluster

  > kubectl apply --validate=false -f https://github.com/jetstack/cert-manager/releases/download/v1.0.2/cert-manager.yaml

   To delete certificate manager

  > kubectl delete -f https://github.com/jetstack/cert-manager/releases/download/v1.0.2/cert-manager.yaml

(10) Create AWS ALB Ingress Controller - Clean up explicitly before deleting EKS cluster

  > kubectl apply -f aws-load-balancer-controller.yaml

  To delete controller

  > kubectl delete -f aws-load-balancer-controller.yaml

(11) Deploy applications into EKS cluster - Clean up explicitly before deleting EKS cluster

   > kubectl apply -f user-deployment.yml
   > kubectl apply -f customer-deployment.yml
   > kubectl apply -f checkout-deployment.yml
   > kubectl apply -f product-deployment.yml
   > kubectl apply -f order-deployment.yml
 
(12) Deploy the AWS ALB Ingress in to EKS

   > kubectl apply -f alb/iitcw-ingress.yml
   > kubectl apply -f alb/iitcw-ingress-prod.yaml
   > kubectl apply -f alb/iitcw-ingress-qa.yaml
   > kubectl apply -f alb/iitcw-ingress-staging.yaml

   To see the logs

   > kubectl logs -n kube-system $(kubectl get po -n kube-system | egrep -o 'aws-load-balancer-controller[a-zA-Z0-9-]+')


   > kubectl logs -n dev pod/customer-deployment-cbb7cbd44-j4whp
   > kubectl get ingress/iitcw -n dev
   > kubectl describe ingress ingress-resource-backend
   
   
   To delete

   > kubectl delete -f iitcw-ingress.yml



################### Install MySQL Client At EC2 Instance ====================

> sudo yum install mysql
> mysql -u root -h asecw.cxlnkgitspbn.ap-southeast-1.rds.amazonaws.com -P 3306 -p


Note: When connecting to a EC2 instances spined up by Auto scaling grouup, I had add more inbound and outbout roles.
Screen shot was created and saved inside CAA folder
