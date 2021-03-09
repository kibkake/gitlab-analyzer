import React, {Component, useEffect, useRef, useState} from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import axios from "axios";
import * as classes from "postcss";

//[https://material-ui.com/components/tables/]
const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});


function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    {row.merged_at}
                </TableCell>
                <TableCell align="right">{row.title}</TableCell>
                <TableCell align="right">{row.mrScore}</TableCell>
                <TableCell align="right">{row.diffs}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                                Commits
                            </Typography>
                            <Table size="small" aria-label="commits">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Date</TableCell>
                                        <TableCell>Committer</TableCell>
                                        <TableCell align="right">Score</TableCell>
                                        <TableCell align="right">Commit Message</TableCell>
                                        <TableCell align="right">Code Diff</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {row.commits.map((commitsRow) => (
                                        <TableRow key={commitsRow.created_at}>
                                            <TableCell component="th" scope="row">
                                                {commitsRow.created_at}
                                            </TableCell>
                                            <TableCell>{commitsRow.author_email}</TableCell>
                                            <TableCell>{commitsRow.commitScore}</TableCell>
                                            <TableCell align="right">{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.diffs}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}


// const rows = [
//     createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99),
//     createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99),
//     createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
//     createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
//     createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
// ];

function createData(id, date, score, title, fullDiff, commits) {
    return {
        date,
        score,
        title,
        fullDiff,
        commits // [
        //     { created_at: null, author_email: null, commitScore: 1, message: null, diffs : [{diff:null}] }]
    };
}

//         {merged_at: '',
//             mrScore: '',
//             title: '',
//             diffs: '',
//             commits:[
//                 {created_at: '',
//                     author_email: '',
//                     commitScore: '',
//                     message: '',
//                     diffs : [
//                         {diff:''}
//                     ]
//                 }
//                 ]
// }

export default function MergeListTable ({devName}) { //extends Component
    const [merges, getMerges] = useState({});
        // merged_at: '',
        // mrScore: '',
        // title: '',
        // diffs: '',
        // commits:[
        //     {created_at: '',
        //         author_email: '',
        //         commitScore: '',
        //         message: '',
        //         diffs : [
        //             {diff:''}
        //         ]
        //     }]});

    const mounted = useRef();

    useEffect(()=>{
        if (!mounted.current) {
            getDataFromBackend(devName)
            mounted.current = true;
        } else {
            getDataFromBackend(devName)}
    }, merges);


    function getDataFromBackend (username) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
        .then(res => {
            getMerges(res.data);
            console.log(merges);
            // let commitData = res.commits;
            // let diff = res.diffs;
            // getMerges ({merged_at: res.merged_at,
            //     mrScore: res.mrScore,
            //     title: res.title,
            //     diffs: diff,
            //     commits:[
            //     {created_at: commitData.created_at,
            //         author_email: commitData.author_email,
            //         commitScore: commitData.commitScore,
            //         message: commitData.message,
            //         diffs : [
            //             {diff:commitData.diffs.diff}
            //         ]
            //     }]})
        }).catch((error) => {
        console.error(error);
    });}

    // componentDidMount(){
    //     var pathArray = window.location.pathname.split('/');
    //     var id = pathArray[2];
    //     console.log(id);
    //     var username = pathArray[4];
    //     console.log(username);
    //
    //     axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
    //         .then(response => {
    //             const result = response.data
    //             this.setState({merges: result})
    //             //https://spin.atomicobject.com/2018/08/20/objects-not-valid-react-child/
    //             //https://www.akashmittal.com/react-error-objects-not-valid-react-child/
    //                 // {merges : {merged_at: result.merged_at, mrScore: result.mrScore, title: result.title,
    //                 //         diffs: result.diffs, commits: result.commits(commit => {commit.created_at: result.commits.created_at, commit.author_email,
    //                 //             commit.commitScore, commit.diffs})}})
    //             console.log(this.state.merges)
    //         }).catch((error) => {
    //         console.error(error);
    //     });
    // }


    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         merges:[],
    //         parentdata: this.props.devName
    //     }
    // }
    //
    // componentDidMount() {
    //     const {parentdata} = this.state;
    //     this.getDataFromBackend(parentdata)
    // }
    //
    // getDataFromBackend (username) {
    //     const pathArray = window.location.pathname.split('/');
    //     const id = pathArray[2];
    //     const developer = pathArray[4];
    //
    //     //empty request ref: http://localhost:8090/api/v1/projects/6/mergeRequests/user2/2021-01-01/2021-02-23
    //     axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-02-23")
    //         .then(response => {
    //             const result = response.data
    //             this.setState({merges: [result]})
    //             console.log(this.state.merges);
    //         }).catch((error) => {
    //         console.error(error);
    //     });
    // }
    //
    // componentDidUpdate(prevProps) {
    //     if (this.props.devName !== prevProps.devName) {
    //         this.setState({parentdata: this.props.devName});
    //         this.getDataFromBackend(this.props.devName)
    //     }
    // }



    // render () {
        return (
            <TableContainer component={Paper}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell>Date</TableCell>
                            <TableCell align="right">Merge Title</TableCell>
                            <TableCell align="right">Score</TableCell>
                            <TableCell align="right">Full Diff</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {merges.map((merge) => (
                            <TableRow>
                                {/*key={merge.merged_at}>*/}
                        <TableCell>
                                    {merges.merged_at}
                                </TableCell>
                                <TableCell align="right">{merges.title}</TableCell>
                                <TableCell align="right">{merges.mrScore}</TableCell>
                                <TableCell align="right">{merges.diffs}</TableCell>
                            </TableRow>
                        ))}

                    </TableBody>
                </Table>
            </TableContainer>
        );
}


Row.propTypes = {
    row: PropTypes.shape({
        merged_at: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        mrScore: PropTypes.number.isRequired,
        commits: PropTypes.arrayOf(
            PropTypes.shape({
                created_at: PropTypes.string.isRequired,
                author_email: PropTypes.string.isRequired,
                commitScore: PropTypes.number.isRequired,
                message: PropTypes.string.isRequired,
                diffs: PropTypes.string.isRequired,
            }),
        ).isRequired,
        diffs: PropTypes.string.isRequired,
    }).isRequired,
};
// getDataFromBackend (username) {
//     var pathArray = window.location.pathname.split('/');
//     var id = pathArray[2];
//     var name = username;
//     for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers')).length; i++) {
//         if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers'))[i])) {
//             name = JSON.parse(sessionStorage.getItem('DeveloperNames'))[i]//use name to retrieve data
//         }
//     }
//
//     axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
//         .then(response => {
//             const result = response.data
//             this.setState({merges: result})
//             console.log(this.state.merges)
//         }).catch((error) => {
//         console.error(error);
//     });
// }
//
// componentDidUpdate(prevProps){
//     if(this.props.devName !== prevProps.devName){
//         this.setState({parentdata: this.props.devName});
//         this.getDataFromBackend(this.props.devName)
//     }
// }
// const [commits, getCommits] = useState([]);
// const [merges, getMerges] = useState(false);
// var newArr;
// const mounted = useRef();
// useEffect(()=>{
//     if (!mounted.current) {
//         getDataFromBackend(devName)
//         mounted.current = true;
//     } else {
//         getDataFromBackend(devName)}
// }, [merges]);
//
// function getDataFromBackend (username) {
//     var pathArray = window.location.pathname.split('/');
//     var id = pathArray[2];
//
//     axios.get("/api/v1/projects/" + id + "/mergeRequests/" + devName + "/2021-01-01/2021-05-09")
//         .then(response => {
//             const result = response.data
//             newArr = Object.keys(result)
//             getMerges(newArr)
//             console.log(newArr)
//             console.log(merges)
//         }).catch((error) => {
//         console.error(error);
//     });
// }
// axios.get("/api/v1/projects/" + id + "/Commits/" + devName + "/2021-01-01/2021-05-09")
//     .then(response => {
//         getCommits(response.data)
//     }).catch((error) => {
//     console.error(error);
// });}