const express = require("express");
const router = express.Router();
const { getData, runProgram, loadData } = require("../controllers/controller");

const databaseFile = process.env.DATABASE_FILE || "database-file";

router.get("/client", (req, res, next) => {
    getData(req, res, databaseFile);
});

router.post("/load", (req, res, next) => {
    loadData(req, res);
});

router.get("/run", (req, res, next) => {
    runProgram(res);
});

module.exports = {
    router
}