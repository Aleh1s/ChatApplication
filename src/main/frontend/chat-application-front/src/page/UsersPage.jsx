import React, {useState} from 'react';
import {doGetReceiver} from "../Queries";
import User from "../UI/User";

const UsersPage = () => {
    const [query, setQuery] = useState('')
    const [receiver, setReceiver] = useState({
        email: '',
        username: ''
    })

    const find = () => {
        doGetReceiver(query)
            .then(response => setReceiver({
                username: response.data.username,
                email: response.data.email
            }))
            .catch(err => console.log(err.message()))
    }

    return (
        <div>
            <div align={'center'}>
                <input id={'users-search'} type={'text'} placeholder={'Username'} onChange={event => setQuery(event.target.value)}/>
                <button onClick={() => find()}>Find</button>
                {receiver.username.length !== 0 ? <User username={receiver.username}/> : <h1>Not founded</h1> }
            </div>
        </div>
    );
};

export default UsersPage;