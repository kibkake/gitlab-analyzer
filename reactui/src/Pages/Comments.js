import CommentTable from "../components/CommentTable";
import RadioButtons from "../components/RadioButton";
import CustomizedRadios from "../components/RadioButton";
import '../App.css';

function Comments() {
    return (
        <div>
            <h1 style={{textAlign:'center'}}>Comment For Each Note</h1>
            <h4 style={{textAlign:'center'}}>display only top 10 comments by word Count or something </h4>
            <br></br>
            <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
            <CustomizedRadios/>
            </div>
            <CommentTable/>
        </div>
    )
}

export default Comments