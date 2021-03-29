import React, {Component} from 'react'
import  "./HBox.css"
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";

class AllCommits extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
        };
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
    }

    render(){

        return(
            <TableContainer style={{ overflowX: "scroll" , height: "1050px", width: "500px"}}
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

export default AllCommits;