import React, {Component} from 'react';
import axios from "axios";
import { Bar} from 'react-chartjs-2'
import BarChart from "./Chart";
//
//
// const margin = {top: 20, right: 20, bottom: 30, left: 40};
//
// class CommentChart extends Component {
//
//     getInitialState() {
//         return { width: 500 };
//     }
//
//     constructor() {
//         super();
//         this.state = {
//             commentScore: []
//         }
//     }
//
//     //response ref: http://localhost:8080/api/v1/projects/6/topTenUserNotes/arahilin/2021-01-01/2021-02-15
//     componentDidMount () {
//         var pathArray = window.location.pathname.split('/');
//         var id = pathArray[2];
//         var developer = pathArray[4];
//         axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+ developer +"/2021-01-01/2021-02-15")
//             .then(response => {
//                 const commentInfo = response.data;
//                 this.setState({commentScore: commentInfo})
//                 console.log(this.state.commentScore);
//
//             }).catch((error) => {
//             console.error(error);
//         });
//     }
//
//     render() {
//         var output = this.state.commentScore.map(function(item) {
//             return {
//                 text: item.created_at,
//                 value: item.wordCount
//             };
//         });
//         console.log(output);
//
//         return (
//             <div ref='root'>
//                 <div style={{width: '50%'}}>
//                     <BarChart ylabel='Score'
//                               width={200}
//                               height={200}
//                               margin={margin}
//                               data={output}
//
//                     />
//                 </div>
//             </div>
//         );
//     }
// }

// creating a stacked barchart for mr/commits
const CommentCharts = () => {
    return <div>
        <Bar data={{
            //labels x for the axis
            labels: '',
            //data displayed in graph
            datasets: [
                {
                    label: 'Comment Score',
                    data: [56, 34, 5, 50, 12],
                    backgroundColor:'lightGreen',
                    borderColor:'black'
                },
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
export default CommentCharts