var AWS = require("aws-sdk");

module.exports.getExpense = async (event, context, callback) => {

    AWS.config.update({
        region: "us-west-2",
        endpoint: "http://localhost:8000"
    });

    var docClient = new AWS.DynamoDB.DocumentClient();

    const expense = await docClient.get({
        TableName: "local-expenses",
        Key: {
            expenseId: 1
        }
    }).promise();

    return {
        statusCode: 200,
        body: JSON.stringify({
            "id": expense.Item.expenseId,
            "description": expense.Item.description
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    };

    // callback(null, {
    //     statusCode: 200,
    //     body: JSON.stringify({
    //         "id": expense.id,
    //         "description": expense.description
    //     }),
    //     headers: {
    //         'Content-Type': 'application/json'
    //     }
    // });
};


// Create example
// var params = {
//     TableName: "local-expenses",
//     Item:{
//         "expenseId": 1,
//         "amount": 12,
//         "description": "test",
//         "user": 1,
//         "created": (new Date()).toISOString()
//     }
// };
//
// docClient.put(params, function(err, data) {
//     if (err) {
//         console.error("Unable to add item. Error JSON:", JSON.stringify(err, null, 2));
//     } else {
//         console.log("Added item:", JSON.stringify(data, null, 2));
//     }
// });