import React from "react";

// This function highlights the diffs in an appropriate color
// Took off from Abtin's work in the SingeCommitDiffs.js to reuse in other part of the code
export default function HighlightCodeDiffs(par) {
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

            } else if(par[index] === '-'){
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
