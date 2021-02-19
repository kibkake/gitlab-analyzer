import React from 'react'
import { Bar} from 'react-chartjs-2'


// creating a barchart
const BarChart = () => {
    return <div>
        <Bar data={{
            //labels x for the axis
            labels: ['time', 'time', 'time', 'time', 'time'],
            //data displayed in graph
            datasets: [
                    {
                    label: 'commits',
                    data: [56, 34, 5, 50, 12],
                    backgroundColor:'orange',
                    borderColor:'black'
                    },
                {
                    label: 'comments',
                    data:[5,6,25,12,4],
                    backgroundColor: 'red',
                    borderColor: 'black',
                },
                {
                    label: 'merge requests',
                    data:[12,1,20,7,10],
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
                            stacked:true
                            /*ticks:{
                                beginAtZero: true,
                            }*/
                        }
                    ]
                 }
             }}
             />
    </div>
}

export default BarChart