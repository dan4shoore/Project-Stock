const { createReadStream } = require("fs");
const { IncomingMessage, ServerResponse } = require("http");
const { DatabaseParser } = require("../api/data-api");
const { spawn } = require("child_process");
const exec = require("child_process").execFile;
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
        res.write(Buffer.from("["));
        data.forEach((row, index) => {
            res.write(Buffer.from("{\"row\":\""));
            res.write(row);
            res.write(Buffer.from("\"}"));
            if (index == (data.length - 1)) {return;}
            res.write(Buffer.from(","));
        });
        res.write(Buffer.from("]"));
        res.end();
        parser.reset();
    })
}

// const program = "java -cp C:\\Users\\David\\AppData\\Roaming\\Code\\User\\workspaceStorage\\bf9804c9c3604e2df6374e60e404046e\\redhat.java\\jdt_ws\\cop-3530-assignment-1-Kazemanezak_464ddcfe\\bin RuntimeAnalyzer"
// const program = "Start-Process -FilePath \"C:\\Users\\David\\Documents\\COP Project\\html5up-stellar\\controllers\\run.bat\"";
const program = "C:\\Users\\David\\Documents\\COP Project\\html5up-stellar\\controllers\\run.bat"
function runProgram() {
    const bat = spawn(program);
    bat.stdout.on("data", (data) => {
        console.log(data.toString());
    });
    bat.stderr.on("data", (data) => {
        console.error(data.toString());
    });
    bat.on('exit', (code) => {
        console.log(`Child exited with code ${code}`);
   });
    // exec(program, (err, data) => {
    //     console.log(err)
    //     console.log(data.toString());
    // });
}

module.exports = {
    getData,
    runProgram
}