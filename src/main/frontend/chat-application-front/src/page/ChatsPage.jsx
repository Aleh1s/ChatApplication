import React, {useEffect, useState} from 'react';
import {doGetChatsByUsername} from "../Queries";
import ChatsList from "../UI/ChatsList";

const ChatsPage = () => {


    const [chats, setChats] = useState([])

    useEffect(() => {
        fetchChats()
    }, [])

    const fetchChats = () => {
        doGetChatsByUsername(localStorage.getItem("username"))
            .then(response => setChats(response.data))
            .catch(err => console.log(err))
    }

    return (
        <div>
            {chats.length > 0 ?
                <ChatsList chats={chats}/>
                : <h1>Chats do not exist</h1>
            }
        </div>
    );
};

export default ChatsPage;