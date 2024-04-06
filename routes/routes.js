const express = require("express");
const router = express.Router();
const { getData, runProgram } = require("../controllers/controller");

router.get("/client", (req, res, next) => {
    getData(req, res, "database/client.txt");
});

router.get("/run", (req, res, next) => {
    runProgram();
    res.end("Running notepad");
});

module.exports = {
    router
}