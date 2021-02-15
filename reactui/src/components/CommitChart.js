import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import Button from "react-bootstrap/Button";

class CommitChart extends Component {

    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }
    async componentDidMount() {
        for(var arr=[],dt=new Date("2021-01-11"); dt<=new Date("2021-02-22"); dt.setDate(dt.getDate()+1)){

            var dateStr = new Date(dt).toDateString();
            var month = dateStr.split(" ")[1]
            var day = dateStr.split(" ")[2]
            var year = dateStr.split(" ")[3]
            //http://localhost:8080/getuserstats/6/arahilin/2021/Jan/25
            let url3 = 'http://localhost:8080/getuserstats/6/arahilin/' + year + '/' + month + '/' + day
            const result = await fetch(url3, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
            const json = await result.json();
            var data = JSON.stringify(json);
            var DataArray = JSON.parse(data);
            await DataArray.map(item => { arr.push(item)})
            await DataArray.map(item => { console.log(item)})

        }
        this.setState({data:arr})
    }

    render() {
        return(
        <div>
            Hello
            </div>
        )
    }


}

export default CommitChart