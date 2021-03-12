import React, {Component} from 'react'
import "./Projects/ProjectList.css";
import {Table} from "react-bootstrap";




class SingleCommitDiff extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }

    componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var userName = str.split("/")[4];
        var date = str.split("/")[6];
        var hash = str.split("/")[7];



        let url2 = '/api/v1/projects/' + repNum + '/Commit/' + hash;
        fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp})
            })
        })

    }



    splitToLines = (par) => {
        var lineBegin = 0;
        var strArray = [];
        var index = 0;
        while (par[index] !== '\n') {
            index++;
        }
        if(par[lineBegin] === '+'){
            const line1 = par.substring(lineBegin, index);
            strArray.push(<span className="highlight"> {line1}</span>);
            strArray.push("\n");
        }else if(par[lineBegin] === '-'){
            const line1 = par.substring(lineBegin, index);
            strArray.push(<span className="highlight2"> {line1}</span>);
            strArray.push("\n");
        }else {
            const line1 = par.substring(lineBegin, index);
            strArray.push(<span> {line1}</span>);
            strArray.push("\n");
        }
        index++;

        for (index; index < par.length-1; index++) {
            lineBegin = index;
            if(par[index] === '+'){
                while(par[index] !== '\n' && index < par.length-1){
                    index++;
                }
                const line1 = par.substring(lineBegin, index);
                strArray.push(<span className="highlight"> {line1}</span>);
                strArray.push("\n");

            }else if(par[index] === '-'){
                while(par[index] !== '\n' && index < par.length-1){
                    index++;
                }
                const line2 = par.substring(lineBegin, index);
                strArray.push(<span className="highlight2"> {line2}</span>);
                strArray.push("\n");

            }else{
                while(par[index] !== '\n' && index < par.length-1){
                    index++;
                }
                const line3 = par.substring(lineBegin, index);
                strArray.push(<span> {line3}</span>);
                strArray.push("\n");

            }
        }
        return strArray;
    }

    render() {

        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)



        return (
    <div>
        <div>
            { DataArray.map(projects =>
                <span>
                    <td className="commitscore">Commit Score = {projects.commitScore}</td>
                </span>
            )}
        </div>
            <div className="CodeDiffTable">

                <Table striped bordered hover>
                    <tbody>
                        <tr>
                            <td>File Name</td>
                            <td>Diff</td>
                        </tr>

                        {DataArray.map((item) =>
                        item.diffs.map((item2, index) =>
                            <tr key ={index}>
                                <td>{item2.new_path}</td>
                                <td>{this.splitToLines(item2.diff)}</td>

                            </tr>


                        ))}
                    </tbody>
                </Table>

            </div>
                </div>
        );
    }
}

export default SingleCommitDiff;