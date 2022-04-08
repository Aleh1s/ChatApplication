import React from 'react';
import ChatBlock from "./ChatBlock";

const ChatsList = ({chats}) => {
    return (
        <div>
            {chats.map(chatData => <ChatBlock key={chatData.id} chatData={chatData}/>)}
        </div>
    );
};

export default ChatsList;