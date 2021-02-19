import React from 'react'
import { Bar } from 'react-chartjs-2'
// creating a barchart
const BarChart = () => {
    return <div>
        <Bar data={{
            //labels x for the axis
            labels: ['time', 'time', 'time', 'time', 'time'],
            //data displayed in graph
            datasets: [
                    {
                    label: 'code score',
                    data: [56, 34, 5, 50, 12],
                    backgroundColor:'orange',
                    borderColor:[
                        'rgba(255,99,132,0.2',
                        'rgba(255,99,132,0.2',
                        'rgba(255,99,132,0.2',
                        'rgba(255,99,132,0.2',
                        'rgba(255,99,132,0.2',
                    ],
                    borderWidth:5,
                    },
                {
                    label: 'word # of comments',
                    data:[5,6,25,12,4],
                    backgroundColor: 'cyan',
                    borderColor: 'black',
                }
                ]
            }}
             height={200}
             width={200}
             options ={{
                maintainAspectRatio: false,
                 scales:{
                    yAxes:[
                        {
                            ticks:{
                                beginAtZero: true,
                            }
                        }
                    ]
                 }
             }}
             />
    </div>
}

export default BarChart