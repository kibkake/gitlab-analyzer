import React from 'react'
import { Bar } from 'react-chartjs-2'
var stackedBarChart ={
    labels:["dates","dates"],
    datasets:[{
        label: 'comments',
        backgroundColor:'red',
        data: [1, 2]
    }, {
        label: 'commits',
        backgroundColor: 'green',
        data: [5, 6]
    },{
        label: 'merge requests',
        backgroundColor: 'blue',
        data: [10,8]
    }]
};

var chart = document.getElementById('canvas').getContext('2d');


