import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import  "./HBox.css"

class CommitsPerDay extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime
        };
    }

    async componentDidMount(){
        await this.getDataFromBackend(this.props.devName, this.props.startTime)
    }

    async getDataFromBackend(userName, date) {
        var str = window.location.pathname;
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
        await this.setState({data:resp})
    }

    async componentDidUpdate(prevProps){
        console.log(this.props.devName)
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime)
        }
    }

    render(){

        var output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.committed_date
            };
        });
        //console.log(output)

        return(
            <ul style={{ overflow: "scroll", height: "1050px", width: "1000px"}}>
            {this.state.data.map(item => {
                return <li >
                    <a href= {"Developers/" + item }target= "_blank">
                        <Button className="Footer2"to={item.id}
                                type="button"
                                onClick={(e) => {
                                    e.preventDefault();
                                    {this.props.handler(item.id)}

                                }}>
                            <span >{item.id}          commited at:      {item.committed_date.substring(11,19)}</span>
                        </Button>
                    </a>
                </li>;
            })}
        </ul>
        );
    }
}

export default CommitsPerDay;