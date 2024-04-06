const express = require("express");
const { router } = require("./routes/routes");
require("dotenv/config")

const app = express();

const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.static("public"));
app.use("/", router);

app.listen(PORT, () => {
    console.log(`Listening to port: ${PORT}`);
});