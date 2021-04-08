// import React from 'react';
// import { makeStyles } from '@material-ui/core/styles';
// import Grid from '@material-ui/core/Grid';
// import Button from '@material-ui/core/Button';
// import Tooltip from '@material-ui/core/Tooltip';
// import {ClickAwayListener, withStyles} from "@material-ui/core";
// import Highlight from "react-highlight";
// import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
//
// const tooltipStyle = withStyles((theme) => ({
//     tooltip: {
//         backgroundColor: '#f5f5f9',
//         color: 'rgba(0, 0, 0, 0.87)',
//         maxWidth: 800,
//         fontSize: theme.typography.pxToRem(12),
//         border: '1px solid #dadde9',
//     },
// }))(Tooltip);
//
//
// export default function Tooltip (Diffs) {
//     const classes = tooltipStyle();
//
//     return (
//         <ClickAwayListener onClickAway={}
//         <HtmlTooltip
//             placement={"right-start"}
//             title={row.diffs.map(item => {
//                 return (
//                     <React.Fragment class="tooltip">
//                         <ul>
//                             <h5>{item.path}</h5>
//                             <h6><Highlight className="highlighted-text">{HighlightCodeDiffs(item.diff)}</Highlight></h6>
//                         </ul>
//                     </React.Fragment>
//                 )
//             })}
//         >
//
//             <button aria-label="expand row" size="small" onClick={() => setOpen(!open)}
//                     onClick={() => tooltipSetOpen(!tooltipOpen)}
//                     type="button" order={1} className="btn btn-secondary">View</button>
//             {/*    </Tooltip>*/}
//         </HtmlTooltip>
// </div>
//
// </ClickAwayListener>
//     );
// }