import CommentTable from "../components/CommentTable";
import RadioButtons from "../components/RadioButton";
import CustomizedRadios from "../components/RadioButton";
function Comments() {
    return (
        <div classname='Comments'>
            <h1 style={{textAlign:'center'}}>Comment For Each Note</h1>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>display only top 10 comments by word Count or something </h4>
            <br>
            </br>
            <CustomizedRadios style = {{marginBottom: "0", align: 'center'}} />
            <CommentTable/>
        </div>
    )
}

export default Comments