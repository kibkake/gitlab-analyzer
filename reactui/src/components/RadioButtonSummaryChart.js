import React, {Component} from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';
import CommitMRScoreChart from "./CommitMRScoreChart";
import CommitMRNumChart from "./CommitMRNumChart";
import CommentChart from "./CommentChart";
import "./RadioSummaryCharts.css"
//[https://material-ui.com/components/radio-buttons/]

function Chart(props){
    if (props.value == "score") {
        return (<div>
            <h4 style={{textAlign:'center'}}>Score of Commits/Merge Per Day</h4>
            <CommitMRScoreChart devName = {sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
    else if (props.value == "number") {
        return (<div>
            <h4 style={{textAlign: 'center'}}>Number of Commits/Merge Per Day</h4>
            <CommitMRNumChart devName={sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
    if (props.value == "words") {
        return (<div>
            <h4 style={{textAlign: 'center'}}>Number of Words Per Day</h4>
            <CommentChart devName={sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
}

export default class SummaryChartRadios extends Component {
    constructor(props) {
        super(props);
        this.state = {value: "score"};
        this.handleRadioChange = this.handleRadioChange.bind(this);
    }

    handleRadioChange(event) {
        // set the new value of checked radion button to state using setState function which is async funtion
        this.setState({
            value: event.target.value
        });
    }


    render() {
        return (
            <div onChange={this.setState.bind(this)} >
            <FormControl component="fieldset" style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                <RadioGroup defaultValue="score" aria-label="comment" name="customized-radios" >
                    <FormControlLabel value="score" control={ <StyledRadio checked={this.state.value === "score"}
                                                                           onChange={this.handleRadioChange}/>} label="Score of Commit/Merge"/>
                    <FormControlLabel value="number" control={<StyledRadio checked={this.state.value === "number"}
                                                                           onChange={this.handleRadioChange}/>} label="Number of Commit/Merge"/>
                    <FormControlLabel value="words" control={<StyledRadio  checked={this.state.value === "words"}
                                                                           onChange={this.handleRadioChange}/>} label="Words of Comments"/>
                </RadioGroup>
            </FormControl>
                <Chart value = {this.state.value}/>
            </div>

    );
    }
}

// Inspired by blueprintjs
function StyledRadio(props) {
    const classes = useStyles();

    return (
        <Radio
            className={classes.root}
            disableRipple
            color="default"
            checkedIcon={<span className={clsx(classes.icon, classes.checkedIcon)} />}
            icon={<span className={classes.icon} />}
            {...props}
        />
    );
}

const useStyles = makeStyles({
    root: {
        '&:hover': {
            backgroundColor: 'transparent',
        },
    },
    icon: {
        borderRadius: '50%',
        width: 16,
        height: 16,
        boxShadow: 'inset 0 0 0 1px rgba(16,22,26,.2), inset 0 -1px 0 rgba(16,22,26,.1)',
        backgroundColor: '#f5f8fa',
        backgroundImage: 'linear-gradient(180deg,hsla(0,0%,100%,.8),hsla(0,0%,100%,0))',
        '$root.Mui-focusVisible &': {
            outline: '2px auto rgba(19,124,189,.6)',
            outlineOffset: 2,
        },
        'input:hover ~ &': {
            backgroundColor: '#ebf1f5',
        },
        'input:disabled ~ &': {
            boxShadow: 'none',
            background: 'rgba(206,217,224,.5)',
        },
    },
    checkedIcon: {
        backgroundColor: '#137cbd',
        backgroundImage: 'linear-gradient(180deg,hsla(0,0%,100%,.1),hsla(0,0%,100%,0))',
        '&:before': {
            display: 'block',
            width: 16,
            height: 16,
            backgroundImage: 'radial-gradient(#fff,#fff 28%,transparent 32%)',
            content: '""',
        },
        'input:hover ~ &': {
            backgroundColor: '#106ba3',
        },
    },
});
