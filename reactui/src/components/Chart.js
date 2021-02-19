import React from 'react'
import { Bar} from 'react-chartjs-2'


// creating a stacked barchart for mr/commits
const BarChart = () => {
    return <div>
        <Bar data={{
            //labels x for the axis
            labels: ['time', 'time', 'time', 'time', 'time'],
            //data displayed in graph
            datasets: [
                    {
                    label: 'commits',
                    data: [5, 3, 7, 8, 3],
                    backgroundColor:'orange',
                    borderColor:'black'
                    },
                {
                    label: 'merge requests',
                    data:[7,5,9,10,6],
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
                     xAxes: [{
                         stacked: true
                     }],
                    yAxes:[
                        {
                            stacked:true,
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