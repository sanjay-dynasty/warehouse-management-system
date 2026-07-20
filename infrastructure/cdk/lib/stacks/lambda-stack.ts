import * as cdk from "aws-cdk-lib";
import * as lambda from "aws-cdk-lib/aws-lambda"
import * as iam from "aws-cdk-lib/aws-iam"
import { Construct } from "constructs";

export class LambdaStack extends cdk.Stack {
    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const lambdaRole = new iam.Role(this, "WarehouseLambdaRole", {
            assumedBy: new iam.ServicePrincipal("lambda.amazonaws.com"),
            managedPolicies: [
                iam.ManagedPolicy.fromAwsManagedPolicyName(
                    "service-role/AWSLambdaBasicExecutionRole"
                ),
                iam.ManagedPolicy.fromAwsManagedPolicyName(
                    "AmazonDynamoDBFullAccess"
                ),
                iam.ManagedPolicy.fromAwsManagedPolicyName(
                    "AmazonS3FullAccess"
                ),
                iam.ManagedPolicy.fromAwsManagedPolicyName(
                    "AmazonSNSFullAccess"
                )
            ]
        });

        const imageUploadLambda = new lambda.Function(this, "ImageUploadLambda", {
            runtime: lambda.Runtime.NODEJS_22_X,
            handler: "index.handler",
            code: lambda.Code.fromAsset("lambda/image-upload"),
            role: lambdaRole,
            timeout: cdk.Duration.seconds(30),
            memorySize: 512,
            environment: {
                BUCKET_NAME: "warehouse-management-system-assets"
            }
        });

        // Order Notification Lambda

        const orderNotificationLambda = new lambda.Function(this, "OrderNotificationLambda", {
            runtime: lambda.Runtime.NODEJS_22_X,
            handler: "index.handler",
            code: lambda.Code.fromAsset("lambda/order-notification"),
            role: lambdaRole,
            timeout: cdk.Duration.seconds(30),
            memorySize: 512
        });

        // Low Stock Alert Lambda

        const lowStockAlertLambda = new lambda.Function(this, "LowStockAlertLambda", {
            runtime: lambda.Runtime.NODEJS_22_X,
            handler: "index.handler",
            code: lambda.Code.fromAsset("lambda/low-stock-alert"),
            role: lambdaRole,
            timeout: cdk.Duration.seconds(30),
            memorySize: 512
        });

        // Outputs
        new cdk.CfnOutput(this, "ImageUploadLambdaArn", {
            value: imageUploadLambda.functionArn
        });

        new cdk.CfnOutput(this, "OrderNotificationLambdaArn", {
            value: orderNotificationLambda.functionArn
        });

        new cdk.CfnOutput(this, "LowStockAlertLambdaArn", {
            value: lowStockAlertLambda.functionArn
        });
    }
}