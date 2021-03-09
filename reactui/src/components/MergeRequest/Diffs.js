import React from "react";
import './Diffs.css'
import Highlight from "react-highlight";

function Diffs(props){
    return (props.trigger) ? (
        <div>
                <button className="close-btn" onClick={()=> props.setTrigger(false)}>close</button>
                {props.children.map((item,index)=>{
                    return(
                        <ul key={index}>
                            <h5 className="filename">{item.path}</h5>
                            <li className="PopupCode"><Highlight className="highlighted-text">{item.diff} </Highlight></li>
                        </ul>
                        
                    )
                })}
        </div>
    ) : "";
}
export default Diffs;