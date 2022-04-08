import React from 'react';
import {useNavigate} from "react-router-dom";

const ChatBlock = ({chatData}) => {

    const {id} = chatData
    const navigate = useNavigate()
    const receiver = chatData.members
        .map(member => member.username)
        .filter(receiverUsername => receiverUsername !== localStorage.getItem('username'))[0]


    const openChatWith = () => {
        localStorage.setItem('currentChatId', id)
        localStorage.setItem('receiver', receiver)
        navigate('/chat-room')
    }

    return (
        <div>
            <div>
                {chatData.members.map(member => member.username !== localStorage.getItem('username') ?
                    <div key={member.id} align={'center'}>
                        <button onClick={() => openChatWith()}>{`Write to ${member.username}`}</button>
                    </div> : '')}
            </div>
        </div>
    );
};

export default ChatBlock;