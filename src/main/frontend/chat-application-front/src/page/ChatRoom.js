import React, {useEffect, useMemo} from 'react';
import { useState } from 'react'
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import MessageList from "./MessageList";
import {doGetChat, doGetMessages, doGetMessagesByChatId, doGetPagingSortedMessagePageByChatId} from "../Queries";
import {useNavigate} from "react-router-dom";


let stompClient = null

const ChatRoom = () => {

    const navigate = useNavigate()
    const [chatId, setChatId] = useState(null)
    const [username, setUsername] = useState('')
    const [receiver, setReceiver] = useState('')
    const [textMessage, setTextMessage] = useState('')
    const [messages, setMessages] = useState([])

    useEffect(() => {
        setChatId(localStorage.getItem('currentChatId'))
        setUsername(localStorage.getItem('username'))
        setReceiver(localStorage.getItem('receiver'))
        if (username !== '') {
            connect()
        }
    }, [chatId, username])

    useEffect(() => {
        if (chatId !== null) {
            fetchMessages()
        }
    }, [chatId])

    function connect() {
        let socket = new SockJS('http://localhost:8081/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnect)
    }

    const onConnect = (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/user/${username}/private`, onSubscribe)
    }

    const onSubscribe = (messages) => {
        setMessages(JSON.parse(messages.body).messages.content)
    }

    const fetchMessages = () => {
        doGetPagingSortedMessagePageByChatId(chatId)
            .then(response => setMessages(response.data.content))
            .catch(err => console.log(err))
    }

    function send() {
        if (stompClient) {
            let chatMessage = {
                chatId: chatId,
                text: textMessage,
                from: username,
                to: receiver
            }
            stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage))
            setTextMessage('')
        }
    }

    return (
        <div>
            <div>
                <input id={'my-mes-field'} type={'text'} placeholder={'Message'} onChange={event => setTextMessage(event.target.value)} value={textMessage}/>
                <button onClick={() => send()}>Send</button>
                <MessageList messages={messages}/>
            </div>
        </div>
    );
};

export default ChatRoom;