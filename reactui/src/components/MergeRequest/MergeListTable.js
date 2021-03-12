import React, {PureComponent} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from "axios";
import Row from "./Rows";
import './MergeListTable.css'

export default class MergeListTable  extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            merges:[],
            parentData: this.props.devName
        }
    }

    componentDidMount(){
        const {parentData: parentData} = this.state;
        this.getDataFromBackend(parentData)
    }

    getDataFromBackend (username, startTm, endTm) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        var name = username;
        if(sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
            for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
                if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
                    name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
                }
            }
        }

        const response = axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
            .then(res => {
                        this.setState({merges : res.data, parentData: username});
                    }).catch((error) => {
                    console.error(error);})

        console.log(this.state.merges)
    }

    async componentDidUpdate(prevProps){
        if (this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
    }


    render () {
        const output = this.state.merges.map(function(item) {
            return {
                id: item.id,
                date: item.merged_at,
                title: item.title,
                score: item.mrScore,
                mrUrl: item.web_url,
                diffs: item.diffs.map(function (diffs) {
                    return {
                        path: diffs.new_path,
                        diff: diffs.diff
                    };
                }),
                commits: item.commits.map(function (commit) {
                    return {
                        commitDate: commit.date,
                        message: commit.message,
                        score: commit.commitScore,
                        author: commit.author_email,
                        commitDiffs: commit.diffs.map(function (diffs) {
                            return {
                                path: diffs.new_path,
                                diff: diffs.diff
                            };
                        })
                    };
                })
            };
        });
        console.log(output);

        return (

            <div class="d-flex justify-content-start">
                <TableContainer component={Paper} class="p-2">
                    <Table aria-label="collapsible table">
                        <TableHead className="tableCell">
                            <TableRow>
                                <TableCell align="left" className="tableCell"> Date </TableCell>
                                <TableCell>Merge Title</TableCell>
                                <TableCell align="right">Score</TableCell>
                                <TableCell align="right">Commits </TableCell>
                                <TableCell align="right">Full Diff</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {output.map((merge) => (
                                <Row key={merge.date} row={merge}/>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

            </div>

        );
    }
}

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '15pt',
            backgroundColor: 'lightgrey',

        },
    },
    tableCell: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '20pt',
            fontWeight: 'bold',
        },
    }
});

//({devName}) {

// const [merges, getMerges] = useState([]);
// // const nameRef = useRef();
// const mounted = useRef();
//
// useEffect(() => {
//     if (!mounted.current) {
//         mounted.current = true;
//     }
//     getDataFromBackend(devName);
// }, [merges]);
//
//
// function getDataFromBackend (username) {
//     var pathArray = window.location.pathname.split('/');
//     var id = pathArray[2];
//
//     axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
//     .then(res => {
//         getMerges(res.data);
//     }).catch((error) => {
//     console.error(error);
// });}