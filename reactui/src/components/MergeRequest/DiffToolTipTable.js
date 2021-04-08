// import Paper from "@material-ui/core/Paper";
// import Table from "@material-ui/core/Table";
// import TableHead from "@material-ui/core/TableHead";
// import TableRow from "@material-ui/core/TableRow";
// import TableCell from "@material-ui/core/TableCell";
// import TableBody from "@material-ui/core/TableBody";
// import TableContainer from "@material-ui/core/TableContainer";
// import React from "react";
// import {ClickAwayListener} from "@material-ui/core";
// import Collapse from "@material-ui/core/Collapse";
// import Box from "@material-ui/core/Box";
// import Typography from "@material-ui/core/Typography";
// import moment from "moment";
//
// export default function DiffTable ({Diffs}) {
//     return (
//         <TableContainer component={Paper} display="flex" flexDirection="row" p={1} m={1} justifyContent="flex-start">
//             <Table aria-label="collapsible table">
//                 <TableHead className="tableCell">
//                     <TableRow>
//                         <TableCell>Path (+diff Score) </TableCell>
//                     </TableRow>
//                 </TableHead>
//                 <TableBody>
//                     <diffRow key={Diffs.diff} row={Diffs}/>
//                 </TableBody>
//             </Table>
//         </TableContainer>
//     )
// }
//
// export default function diffRow(props) {
//     const { row } = props;
//     const [open, setOpen] = React.useState(false);
//
//     return (
//         <React.Fragment>
//             <TableRow>
//                 <TableCell align="left" component="th" scope="row">
//                     {row.path} (+{row.diffScore})
//                 </TableCell>
//             </TableRow>
//             <TableRow>
//                 <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
//                     <Collapse in={open} timeout="auto" unmountOnExit>
//                         <Box margin={1}>
//                             <Table size="small" aria-label="commits">
//                                 <TableHead>
//                                     <TableRow>
//                                         <TableCell>Date</TableCell>
//                                         <TableCell>Commit Message</TableCell>
//                                         <TableCell align="right">Committer</TableCell>
//                                         <TableCell align="right">Score</TableCell>
//                                         <TableCell align="right">Code Diff</TableCell>
//                                     </TableRow>
//                                 </TableHead>
//                                 <TableBody>
//                                     {row.commits.map((commitsRow) => (
//                                         <TableRow key={commitsRow.commitDate}>
//                                             <TableCell component="th" scope="row">
//                                                 {moment(commitsRow.commitDate).format('LLL')}
//                                             </TableCell>
//                                             <TableCell>{commitsRow.message}</TableCell>
//
//
//
//
//
//                                         }}
//                                         }
