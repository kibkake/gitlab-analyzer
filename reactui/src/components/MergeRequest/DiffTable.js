import {Popover, Table} from "react-bootstrap";
import Highlight from "react-highlight";
import HighlightCodeDiffs from "../HighlightCodeDiffs";
import React from "react";

export default function DiffTable ({Diffs})  {
    return (
        <Table striped bordered hover>
            {Diffs.map(item =>
                <tr>
                    <th><h3>{item.path}</h3></th>
                </tr>,
            <tbody>
                <tr>
                    <td><Highlight className="highlighted-text"> {HighlightCodeDiffs(item.diff)} </Highlight></td>
                </tr>
            </tbody>
            )}
        </Table>
        )
}



            //     <Popover id="popover-basic" placement='right' class="justify-content-end" >
            // {Diffs.map((item => {
            //     return(
            //     <ul>
            //     <Popover.Title as="h3">{item.path}</Popover.Title>
            //     <Popover.Content><Highlight className="highlighted-text"> {HighlightCodeDiffs(item.diff)} </Highlight>
            //     </Popover.Content>
            //     </ul>
            //     )
            // }))}
            //     </Popover>
            //     )