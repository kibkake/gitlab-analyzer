import React, { useState, useEffect } from "react";
import axios from "axios";

import Chart1 from "./Chart1";

const SingleChart = () => {
    const [chart, setChart] = useState({});

    useEffect(() => {
        getData();
    }, []);

    const getData = async () => {
        try {
            var pathArray = window.location.pathname.split('/');
            var id = pathArray[1];
            var developer = pathArray[3];
            const res = await axios.get(
                // `https://corona.lmao.ninja/v2/historical/pakistan`
            "http://localhost:8080/api/v1/projects/" +id+ "/MRsAndCommitScoresPerDay/"+developer+"/2021-01-01/2021-02-10"
        );

            setChart({
                labels: Object.keys(res.data.date),
                datasets: [
                    {
                        label: "Covid-19",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(75,192,192,0.4)",
                        borderColor: "rgba(75,192,192,1)",
                        borderCapStyle: "butt",
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: "miter",
                        pointBorderColor: "rgba(75,192,192,1)",
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(75,192,192,1)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: Object.values(res.data.commitScore)
                    }
                ]
            });
        } catch (error) {
            console.log(error.response);
        }
    };

    return (
        <div>
            <Chart1 data={chart} />
        </div>
    );
};

export default SingleChart;

