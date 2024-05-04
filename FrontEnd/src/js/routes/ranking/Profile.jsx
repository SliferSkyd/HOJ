import Content from "../../components/contents/Content"
import Header from "../../components/headers/Header"
import LeftSide from "../../components/contents/LeftSide"
import LeftSideComponent from "../../components/contents/LeftSideComponent"
import RightSide from "../../components/contents/RightSide"
import RightSideComponent from "../../components/contents/RightSideComponent"
import React from "react"
import CustomTable from "../../components/utils/Table"
import TableRow from "../../components/utils/TableRow"
import TableCellHead from "../../components/utils/TableCellHead"
import TableCell from "../../components/utils/TableCell"
import Axios from "axios"
import { userRoute } from "../../../utils/APIRoutes"
import Popup from 'reactjs-popup'
import 'reactjs-popup/dist/index.css'



function Profile({user, setUser}) {
    
    React.useEffect(() => {
        
        console.log(user)
    }, [user])

    return (
        <React.Fragment>
            <Popup open={user !== null} onClose={() => setUser(null)}>
                <div className="popup-div">
                    <div className="text-heading" style={{fontSize: 24, color: "red"}}> {user ? user.name : ""}</div>
            
                    <div style={{display: "flex", columnGap: 20}}>
                        <div className="text-content" style={{fontSize: 16}}>
                            <p>Username: {user ? user.username : ""}</p>
                            <p>School: {user ? user.school : ""}</p>
                            <p>Email: {user ? user.email : ""}</p>
                            <p>Number of accepted problems: {user ? user.numAcceptedProblems : ""}</p>
                            <p>Number of submissions: {user ? user.numSubmissions : ""}</p>
                        </div>
                        <img src="https://vapa.vn/wp-content/uploads/2022/12/avatar-doremon-cute-001.jpg" alt="" style={{height: 200}} />
                        
                    </div>
                            
                </div>
            </Popup>
        </React.Fragment>
        
    )
}
export default Profile