import * as cdk from "aws-cdk-lib";
import * as logs from "aws-cdk-lib/aws-logs";
import { Construct } from "constructs";

export class CloudWatchStack extends cdk.Stack {

    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const applicationLogGroup = new logs.LogGroup(this, "ApplicationLogGroup", {
            logGroupName: "/warehouse/application",
            retention: logs.RetentionDays.ONE_MONTH,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        const lambdaLogGroup = new logs.LogGroup(this, "LambdaLogGroup", {
            logGroupName: "/warehouse/lambda",
            retention: logs.RetentionDays.ONE_MONTH,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        new cdk.CfnOutput(this, "ApplicationLogGroupName", {
            value: applicationLogGroup.logGroupName,
            description: "Application Log Group"
        });

        new cdk.CfnOutput(this, "LambdaLogGroupName", {
            value: lambdaLogGroup.logGroupName,
            description: "Lambda Log Group"
        });
    }
}