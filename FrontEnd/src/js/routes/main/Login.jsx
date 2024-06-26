import React from "react"
import Axios from "axios"
import { useNavigate } from 'react-router-dom';
import { SessionContext } from "../../../utils/context";

function Login() {
    const { setSession } = React.useContext(SessionContext)

    const [username, setUsername] = React.useState("")
    const [password, setPassword] = React.useState("")
    const [error, setError] = React.useState("")
    const navigate = useNavigate()

    const onUsernameChange = event => {
        setUsername(event.target.value)
    }

    const onPasswordChange = event => {
        setPassword(event.target.value)
    }

    React.useEffect(() => {
        document.title = "Login"
    })

    const onSubmit = event => {
        event.preventDefault();
        Axios.post("http://localhost:3001/login", {
            username: username,
            password: password
        }, { withCredentials: true }).then(res => {
            if (res.data.success) {
                Axios.get("http://localhost:3001/login", { withCredentials: true }).then(res => {
                    setSession(res.data)
                    navigate(window.location, { replace: true })
                })
            }
            else setError(res.data.message)
        })
    }

    return (
        <div className="login-background">
            <div className="login-border">
                <div className="text-heading">Welcom to PhoCF Judge System</div>
                <form className="login-form" onSubmit={onSubmit}>
                    <input className="text-input login-input" placeholder="Username" type="text" onChange={onUsernameChange} required />
                    <input className="text-input login-input" placeholder="Password" type="password" onChange={onPasswordChange} autoComplete="on" required />
                    <div className="login-error">{error}</div>
                    <button type="submit" className="btn-submit">Log in</button>
                </form>

            </div>
        </div>
    )
}
export default Login