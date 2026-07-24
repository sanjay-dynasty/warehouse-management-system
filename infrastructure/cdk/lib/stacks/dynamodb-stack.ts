import * as cdk from "aws-cdk-lib";
import * as dynamodb from "aws-cdk-lib/aws-dynamodb"
import { Construct } from "constructs";

export class DynamoDbStack extends cdk.Stack {

    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const productTable = new dynamodb.Table(this, "ProductTable", {
            tableName: "wms-products",
            partitionKey: {
                name: "productId",
                type: dynamodb.AttributeType.STRING
            },
            pointInTimeRecoverySpecification: {
                pointInTimeRecoveryEnabled: true
            },
            tableClass: dynamodb.TableClass.STANDARD,
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        productTable.addGlobalSecondaryIndex({
            indexName: "category-createdAt-index",
            partitionKey: {
                name: "category",
                type: dynamodb.AttributeType.STRING
            },
            sortKey: {
                name: "createdAt",
                type: dynamodb.AttributeType.STRING
            },
            projectionType: dynamodb.ProjectionType.ALL
        });

        new cdk.CfnOutput(this, "ProductTableName", {
            value: productTable.tableName,
            description: "Product Table Name"
        });

        new cdk.CfnOutput(this, "ProductTableArn", {
            value: productTable.tableArn,
            description: "Product Table ARN"
        });

        const warehouseTable = new dynamodb.Table(this, "WarehouseTable", {
            tableName: "wms-warehouses",
            partitionKey: {
                name: "warehouseId",
                type: dynamodb.AttributeType.STRING
            },
            pointInTimeRecoverySpecification: {
                pointInTimeRecoveryEnabled: true
            },
            tableClass: dynamodb.TableClass.STANDARD,
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        warehouseTable.addGlobalSecondaryIndex({
            indexName: "city-status-index",
            partitionKey: {
                name: "city",
                type: dynamodb.AttributeType.STRING
            },
            sortKey: {
                name: "status",
                type: dynamodb.AttributeType.STRING
            },
            projectionType: dynamodb.ProjectionType.ALL
        });

        new cdk.CfnOutput(this, "WarehouseTableName", {
            value: warehouseTable.tableName,
            description: "Warehouse Table Name"
        });

        new cdk.CfnOutput(this, "WarehouseTableArn", {
            value: warehouseTable.tableArn,
            description: "Warehouse Table ARN"
        });

        const inventoryTable = new dynamodb.Table(this, "InventoryTable", {
            tableName: "wms-inventory",
            partitionKey: {
                name: "inventoryId",
                type: dynamodb.AttributeType.STRING
            },
            pointInTimeRecoverySpecification: {
                pointInTimeRecoveryEnabled: true
            },
            tableClass: dynamodb.TableClass.STANDARD,
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        inventoryTable.addGlobalSecondaryIndex({
            indexName: "product-warehouse-index",
            partitionKey: {
                name: "productId",
                type: dynamodb.AttributeType.STRING
            },
            sortKey: {
                name: "warehouseId",
                type: dynamodb.AttributeType.STRING
            },
            projectionType: dynamodb.ProjectionType.ALL
        });

        new cdk.CfnOutput(this, "InventoryTableName", {
            value: inventoryTable.tableName,
            description: "Inventory Table Name"
        });

        new cdk.CfnOutput(this, "IventoryTableArn", {
            value: inventoryTable.tableArn,
            description: "Inventory Table ARN"
        });

        const orderTable = new dynamodb.Table(this, "OrderTable", {
            tableName: "wms-orders",
            partitionKey: {
                name: "orderId",
                type: dynamodb.AttributeType.STRING
            },
            pointInTimeRecoverySpecification: {
                pointInTimeRecoveryEnabled: true
            },
            tableClass: dynamodb.TableClass.STANDARD,
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
            removalPolicy: cdk.RemovalPolicy.DESTROY
        });

        orderTable.addGlobalSecondaryIndex({
            indexName: "customer-createdAt-index",
            partitionKey: {
                name: "customerId",
                type: dynamodb.AttributeType.STRING
            },
            sortKey: {
                name: "createdAt",
                type: dynamodb.AttributeType.STRING
            },
            projectionType: dynamodb.ProjectionType.ALL
        });

        orderTable.addGlobalSecondaryIndex({
            indexName: "warehouse-status-index",
            partitionKey: {
                name: "warehouseId",
                type: dynamodb.AttributeType.STRING
            },
            sortKey: {
                name: "status",
                type: dynamodb.AttributeType.STRING
            },
            projectionType: dynamodb.ProjectionType.ALL
        });

        new cdk.CfnOutput(this, "OrderTableName", {
            value: orderTable.tableName,
            description: "Order Table Name"
        });

        new cdk.CfnOutput(this, "OrderTableArn", {
            value: orderTable.tableArn,
            description: "Order Table ARN"
        });
    }
}