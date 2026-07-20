import * as cdk from "aws-cdk-lib";
import * as s3 from "aws-cdk-lib/aws-s3"
import { Construct } from "constructs";

export class S3Stack extends cdk.Stack {
    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const assetsBucket = new s3.Bucket(this, "AssetsBucket", {
            bucketName: "warehouse-management-system-assets",
            blockPublicAccess: s3.BlockPublicAccess.BLOCK_ALL,
            objectOwnership: s3.ObjectOwnership.BUCKET_OWNER_ENFORCED,
            enforceSSL: true,
            versioned: true,
            encryption: s3.BucketEncryption.S3_MANAGED,
            lifecycleRules: [{
                noncurrentVersionExpiration: cdk.Duration.days(30),
                abortIncompleteMultipartUploadAfter: cdk.Duration.days(7)
            }],
            removalPolicy: cdk.RemovalPolicy.DESTROY,
            autoDeleteObjects: true
        });

        new cdk.CfnOutput(this, "AssetsBucketName", {
            value: assetsBucket.bucketName,
            description: "Assets S3 Bucket Name"
        });

        new cdk.CfnOutput(this, "AssetsBucketArn", {
            value: assetsBucket.bucketArn,
            description: "Assets S3 Bucket ARN"
        });
    }
}