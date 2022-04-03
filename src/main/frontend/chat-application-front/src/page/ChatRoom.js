import React, {useEffect, useMemo} from 'react';
import { useState } from 'react'
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import MessageList from "./MessageList";
import {doGetChat} from "../Queries";

let stompClient = null
const ChatRoom = () => {

    const [textMessage, setTextMessage] = useState('')
    const [logStatus, setLogStatus] = useState(JSON.parse(localStorage.getItem('logStatus')))
    const [username, setUsername] = useState(localStorage.getItem('username'))
    const [receiver, setReceiver] = useState(localStorage.getItem('receiver'))
    const [messages, setMessages] = useState([])

    useEffect(() => {
        fetchMessages()
        if (logStatus) {
            connect()
        }
    }, [])

    function connect() {
        let socket = new SockJS('http://localhost:8081/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnect)
    }

    const onConnect = (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/user/${username}/private`, onSubscribe)
    }

    const onSubscribe = () => {
        fetchMessages()
    }

    const fetchMessages = () => {
        doGetChat(username, receiver)
            .then(response => setMessages(response.data.messages))
            .catch(err => console.log(err))
    }

    const send = () => {
        if (stompClient) {
            let chatMessage = {
                text: textMessage,
                from: username,
                to: receiver
            }
            stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage))
            setTextMessage('')
            setTimeout(() => {
                fetchMessages()
            }, 400)
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