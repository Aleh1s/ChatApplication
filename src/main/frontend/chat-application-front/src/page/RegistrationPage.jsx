import React, {useState, useEffect} from 'react';
import {doRegister} from "../Queries";
import { useNavigate } from "react-router-dom";

const RegistrationPage = () => {

    const navigate = useNavigate()
    const [status, setStatus] = useState(false)
    const [data, setData] = useState({
        email: "",
        password: "",
        username: ""
    })

    useEffect(() => {
        if (status === true) {
            navigate('/login')
        }
    }, [status])

    return (
        <div>
            <div align={'center'}>
                <h1>Register</h1>
                <input id={'reg-email'} type={'email'} placeholder={'Email'} onChange={event => setData({...data, email: event.target.value})}/>
                <br/>
                <input id={'reg-password'} type={'password'} placeholder={'Password'} onChange={event => setData({...data, password: event.target.value})}/>
                <br/>
                <input id={'reg-username'} type={'text'} placeholder={'Username'} onChange={event => setData({...data, username: event.target.value})}/>
                <br/>
                <button onClick={() => doRegister(data, setStatus)}>Register</button>
            </div>
        </div>
    );
};

export default RegistrationPage;