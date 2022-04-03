import React from 'react';
import MyMessage from "../UI/MyMessage";
import UserMessage from "../UI/UserMessage";

const MessageList = ({messages}) => {
    return (
        <div>
            {messages.map(message => message.sender === localStorage.getItem("username") ?
                <MyMessage message={message} /> : <UserMessage message={message} />)}
        </div>
    );
};

export default MessageList;