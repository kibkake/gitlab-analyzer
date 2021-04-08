// import Highlight from "react-highlight";
// import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
// import React from "react";
// import async from "asynckit";
//
// class DiffWindow extends Component {
//
//     constructor(props) {
//         super(props);
//         this.state ={
//             Diffs: props,
//         };
//     }
//
//     async handler() {
//         this.props.handler(hash)
//     }
//
//     return (
//         <div>
//             {/*// <Popover id="popover-basic" placement='right' class="justify-content-end" >*/}
//             {Diffs.map((item, index) => {
//                 return(
//                     <ul key={index}>
//                         <h5 className="filename">{item.path}</h5>
//                         <li><Highlight className="highlighted-text">{HighlightCodeDiffs(item.diff)} </Highlight></li>
//                         {/*<Popover.Title as="h3">{item.new_path}</Popover.Title>*/}
//                         {/*<Popover.Content><Highlight className="highlighted-text">  </Highlight>*/}
//                         {/*</Popover.Content>*/}
//                     </ul>
//                 )
//             })}
//         </div>
//     )
// }
//
