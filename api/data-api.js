function DatabaseParser() {
    /**
     * @type {Buffer}
     */
    this.remainChunk = Buffer.from("");

    this.reset = function () {
        this.remainChunk = Buffer.from("");
    }
}

/**
 * @type {Parser}
 */
DatabaseParser.prototype.getRows = function (chunk) {
    let data = chunk || Buffer.from("\n");
    let rows = [];
    let cursor = 0;
    let start = 0;
    let section;

    // Separates the data into rows
    while (start < data.length - 1) {
        cursor = data.indexOf(Buffer.from("\r\n"), start);
        if (cursor == -1) {
            break;
        }

        section = data.subarray(start, cursor);
        rows.push(section);
        start = cursor + 2;
    }

    if (rows.length > 0) {
        rows[0] = Buffer.concat([this.remainChunk, rows[0]]);
    }

    if (rows.length == 0) {
        rows.push(this.remainChunk);
    }

    this.remainChunk = data.subarray(start);

    return rows;
}

module.exports = {
    DatabaseParser
}