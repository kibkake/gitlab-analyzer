import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import  "./HBox.css"
import "../Projects/ProjectList.css";
import moment from "moment";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import '../MergeRequest/MergeListTable.css'


class CommitsPerDay extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime,
            date : ""
        };
        this.handler = this.handler.bind(this)
    }

    async handler(hash) {
        this.props.handler(hash)
    }

    async componentDidMount(){
        await this.getDataFromBackend(this.props.devName, this.props.startTime)
    }

    async getDataFromBackend(userName, date) {
        /*var str = window.location.pathname;
        var repNum = str.split("/")[2];

        let url2 = '/api/v1/projects/' + repNum + '/Commits/' + userName + '/' + date + "/" + date  + "/either"
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})*/

       // console.log(date)//2021-02-06


        var listOfDates = [];
        var tempDate = "";
        this.props.commits.map(function(item) {
            var time = moment(item.committed_date).format('L')//02/18/2021
            tempDate = moment(date).format('lll')
            const month = time.substring(0,2)
            const day = time.substring(3,5)
            const year = time.substring(6,10)

            const fulltime = year + '-' + month + '-' + day
            if(fulltime === date){
                listOfDates.push(item)
            }
        })
        await this.setState({data:listOfDates, date: tempDate})
    }

    async componentDidUpdate(prevProps){
        if(this.props.startTime !== prevProps.startTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime)
        }
    }

    render(){
        var output = []
        output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.committed_date
            };
        });
        console.log(output)

        return(
            <TableContainer
                style={{ overflowX: "scroll" , height: "1050px", width: "500px"}}
                component={Paper}
                display="flex"
                flexDirection="row"
                p={1}
                m={1}
                justifyContent="flex-start">

            </TableContainer>
        );
    }
}

export default CommitsPerDay;