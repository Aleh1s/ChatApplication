import React, {useEffect, useState} from 'react';
import {doLogin} from "../Queries";
import {useNavigate} from "react-router-dom";

const LoginPage = () => {
    const navigate = useNavigate()
    const [username, setUsername] = useState('')
    const [status, setStatus] = useState(false)
    const [data, setData] = useState({
        email: '',
        password: ''
    })

    useEffect(() => {
        localStorage.setItem('logStatus', JSON.stringify(status))
        if (status) {
            localStorage.setItem('username', username)
            navigate('/users')
        }
    }, [status])

    return (
        <div>
            <div align={'center'}>
                <h1>Login</h1>
                <input id={'log-email'} type={'email'} placeholder={'Email'} onChange={event => setData({...data, email: event.target.value})}/>
                <br/>
                <input id={'log-password'} type={'password'} placeholder={'Password'} onChange={event => setData({...data, password: event.target.value})}/>
                <br/>
                <button onClick={() => doLogin(data, setStatus, setUsername)}>Login</button>
            </div>
        </div>
    );
};

export default LoginPage;