import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";
import React from "react";
import TableHead from "@material-ui/core/TableHead";
import DiffRow from "./DiffTableRows";

export default function DiffTable (props) {
    const {data} = props;
    const [expanded, setExpanded]  = React.useState(false);

    return (
        <TableContainer component={Paper}>
            <Table hover aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Path</TableCell>
                        <TableCell>Score</TableCell>
                        <TableCell>
                            <button style={{backgroundColor: 'lightblue', color: 'black', borderRadius: '0%'}}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        setExpanded(!expanded);
                                    }}>
                                Expand / Collapse All
                            </button>
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.diffs.map((item) => (
                        <DiffRow key={item.diff} expanded={expanded} diff={item}/>))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

