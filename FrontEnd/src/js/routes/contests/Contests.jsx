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
import { SessionContext } from "../../../utils/context"
import { userRoute } from "../../../utils/APIRoutes"

function Contests() {
    const [users, setUsers] = React.useState([])
    const [user, setUser] = React.useState(null)

    React.useEffect(() => {
        document.title = "Contests"
    })
    React.useEffect(() => {
        setUsers([
            {
                username: "user1",
                name: "User 1",
                numAcceptedProblems: 10,
                numSubmissions: 20
            },
            {
                username: "user2",
                name: "User 2",
                numAcceptedProblems: 5,
                numSubmissions: 10
            },
            {
                username: "user3",
                name: "User 3",
                numAcceptedProblems: 3,
                numSubmissions: 5
            },
            {
                username: "user4",
                name: "User 4",
                numAcceptedProblems: 1,
                numSubmissions: 2
            }
        ])
    }, [])

    return (
        <React.Fragment>
            <Header />
            <Content>
                <LeftSide width="75%">
                    <LeftSideComponent>
                        <div className="text-heading">Users</div>
                        <CustomTable>
                            <TableRow>
                                <TableCellHead title="#" />
                                <TableCellHead title="Username" />
                                <TableCellHead title="Name" />
                                <TableCellHead title="Accepted Problems" />
                                <TableCellHead title="Total Submissions" />
                            </TableRow>
                            {
                                users.map((value, key) => (
                                    <React.Fragment key={key}>
                                        <TableRow>
                                            <TableCell padding="10px" title={key + 1} />
                                            <TableCell padding="10px">
                                                <a href='/#' onClick={(e) => { e.preventDefault(); setUser(value) }} style={{ textDecoration: "underline", color: 'blue' }}>{value.username}</a>
                                            </TableCell>
                                            <TableCell padding="10px" title={value.name} />
                                            <TableCell padding="10px" title={value.numAcceptedProblems} />
                                            <TableCell padding="10px" title={value.numSubmissions} />
                                        </TableRow>
                                    </React.Fragment>
                                ))
                            }
                        </CustomTable>
                    </LeftSideComponent>
                </LeftSide>                
            </Content>
        </React.Fragment>

    )
}
export default Contests