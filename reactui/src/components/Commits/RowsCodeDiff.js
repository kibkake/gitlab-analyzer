import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import 'bootstrap/dist/css/bootstrap.min.css';
import './TableStyle.css'


export default function Row(props) {


    return (
        <React.Fragment >

        </React.Fragment>
    );
}

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '15pt',
            backgroundColor: 'white',
            background:'linear-gradient(rgb(3, 222, 167),transparent)'
        },
    },
});
