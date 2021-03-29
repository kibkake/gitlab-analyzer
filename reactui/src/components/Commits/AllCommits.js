import React, {Component} from 'react'
import  "./HBox.css"
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import {Table} from "react-bootstrap";
import moment from "moment";

class AllCommits extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime

        };
        this.handler = this.handler.bind(this)
    }

    async handler(hash) {
        this.props.handler(hash)
    }

    async componentDidMount(){
        await this.getDataFromBackend(this.props.devName, this.props.startTime, this.props.endTime)
    }

    async getDataFromBackend(userName, startDate, endDate) {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];

        let url2 = '/api/v1/projects/' + repNum + '/Commits/' + userName + '/' + startDate + "/" + endDate  + "/either"
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})
        await this.props.handler3(this.state.data)
    }

    render(){

        var output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.committed_date
            };
        });
        console.log(output)

        return(
            <TableContainer style={{ overflowX: "scroll" , height: "1050px", width: "500px"}}
            component={Paper}
            display="flex"
            flexDirection="row"
            p={1}
            m={1}
            justifyContent="flex-start">
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen'}}> All Commits {" "}
                    {moment(this.props.startTime).format('lll').substring(0,12)} -
                    {moment(this.props.endTime).format('lll').substring(0,12)}</div>
                <Table >
                </Table>
            </TableContainer>
        );
    }
}

export default AllCommits;