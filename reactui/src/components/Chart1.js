// and Here is Chart Component

import React from "react";
import { Line } from "react-chartjs-2";

const Chart1 = ({ data }) => {

    console.log(data);

    return <Line data={data} options={{ responsive: true, height: '600px', width: "600px" }} />;
};

export default Chart1;
