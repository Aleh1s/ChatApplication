import React, {useContext} from 'react';
import { useNavigate } from "react-router-dom";

const User = ({username}) => {

    const navigate = useNavigate()

    return (
        <div>
            <div>
                <h2>{username}</h2>
                <div>
                    <button onClick={() => {
                        localStorage.setItem('receiver', username)
                        navigate('/chat-room')
                    }}>Do chat</button>
                </div>
            </div>
        </div>
    );
};

export default User;