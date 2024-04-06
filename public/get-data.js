/**
 * @typedef {(data: any) => void} GetCallback
 */

/**
 * 
 * @param {GetCallback} callback 
 */
function getClient(callback) {
    fetch("/client").then((data) => {
        data.json().then((val) => {
            callback(val);
        })
    });
}

function main() {
    getClient((data) => {
        const p = document.getElementById("titulo");
        // console.log(data);
        // data = data.map((line) => {
        //     let values = line.row.split(",");
        //     let result = {};
        //     values.forEach((val, index) => {
        //         result[index] = val;
        //     });
        //     return result;
        // });
        // console.log(data);
        // p.innerHTML = data[0][0] + " " + data[1][1] + " " + data[3][0] + "?";
        p.innerHTML = data[3].row;
        console.log(data[3].row);
        console.log(data);
        // console.log(data[0][1]);
    })
}

main();