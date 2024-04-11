const { createReadStream, createWriteStream } = require("fs");
const { IncomingMessage, ServerResponse } = require("http");
const { DatabaseParser } = require("../api/data-api");
const { spawn } = require("child_process");
const { join } = require("path");
/**
 * @typedef {(chunk: Buffer) => Buffer[]} Parser
 */

/**
 * 
 * @param {IncomingMessage} req 
 * @param {ServerResponse<IncomingMessage> & {req: IncomingMessage}} res
 * @param {string} [source]
 * @returns 
 */
function getData(req, res, source = "") {

    const rs = createReadStream(source);

    rs.on("error", (err) => {
        res.statusCode = 404;
        res.setHeader("content-type", "text/plain");
        res.write(`Error ${res.statusCode}:\n\n${err.message}`);
        res.end()
    });

    rs.once("data", () => {
        res.statusCode = 200;
        res.setHeader("content-type", "text/plain");
    });

    const parser = new DatabaseParser()
    let data = [];

    rs.on("data", (chunk) => {
        data.push(parser.getRows(chunk));
    });

    rs.on("end", () => {
        data.push(parser.getRows());
        
        data = data.flat();

        data.forEach((row, index) => {
            res.write(row);
            if (index == (data.length - 1)) {return;}
            res.write(Buffer.from("\r\n"));
        });
        res.end();
        parser.reset();
    })
}


const program = "C:\\Users\\David\\Documents\\COP Project\\html5up-stellar\\controllers\\run.bat";

/**
 * 
 * @param {import("express").Response} res
 */
function runProgram(res) {
    const bat = spawn(program);
    bat.stdout.on("data", (data) => {
        console.log(data.toString());
    });
    bat.stderr.on("data", (data) => {
        console.error(data.toString());
    });
    bat.on('exit', (code) => {
        console.log(`Child exited with code ${code}`);
        res.redirect("/client");  
   });
}

// /**
//  * 
//  * @param {IncomingMessage} req 
//  * @param {ServerResponse<IncomingMessage> & {req: IncomingMessage}} res
//  * @param {string} destination
//  * @param {string} [redirectToUrl]
//  * @returns 
//  */
// function sendData(req, res, destination, redirectToUrl = "/") {
//     const ws = createWriteStream(destination, {flags: "w"});
//     if (req.headers["content-type"] == "text/plain") {
//         ws.write(req.body);
//     } else {
//         ws.write(JSON.stringify(req.body));  
//     }
//     console.log(req.body);

//     ws.on("end", () => {
//         res.end("sup");
//     });
//     ws.end();
// }

/**
 * 
 * @param {IncomingMessage} req 
 * @param {ServerResponse<IncomingMessage> & {req: IncomingMessage}} res
 * @returns 
 */
function loadData(req, res) {
    new Promise((resolve, reject) => {
        let chunks = [];
        let data;
        req.on("data", (chunk) => {
            chunks.push(chunk);
        });
        req.on("end", () => {
            data = Buffer.concat(chunks);
            let start = 0;
            let end = 0;
            start = data.indexOf("ID");
            end = data.indexOf("------", start);

            let file1 = data.subarray(start, end);

            start = data.indexOf("ID", end);
            end = data.indexOf("------", start);

            let file2 = data.subarray(start, end);

            resolve([file1, file2]);
        });
    }).then((data) => {
        const ws1 = createWriteStream(join(__dirname, "..", process.env.INPUT_1), {flags: "w"});
        const ws2 = createWriteStream(join(__dirname, "..", process.env.INPUT_2), {flags: "w"});

        ws1.write(data[0]);
        ws2.write(data[1]);

        res.redirect("/output.html");
    }).catch((err) => {
        console.log(err);
        res.end("error");
    });
}

module.exports = {
    getData,
    runProgram,
    loadData
}