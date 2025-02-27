require('dotenv').config();
const mysql = require('mysql2');
const dbUrl = process.env.JAWSDB_URL;
const connection = mysql.createConnection(dbUrl);

connection.connect((err) => {
    if(err) {
        console.error(err.stack);
        return;
    }
    console.log('Database connected ' + connection.threadId);
});

module.exports = connection;