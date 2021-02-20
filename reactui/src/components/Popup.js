import React from "react";
import './Popup.css'

function Popup(props){
    return(props.trigger) ? (
        <div className="popup">
            <div className="popup-inner">
                <button className="close-btn" onClick={()=> props.setTrigger(false)}>close</button>
                {props.children.map((item,index)=>{
                    return(
                        <ul key={index}>
                            <h5 className="filename">{item.new_path}</h5>
                            <li className="PopupCode">{item.diff}</li>
                        </ul>
                        
                    )
                })}
            </div>
        </div>
    ) :"";
}
export default Popup