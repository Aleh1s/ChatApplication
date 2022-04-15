import React, {useEffect, useState} from 'react';
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {doGetPagingSortedMessagePageByChatId} from "../Queries";
import {useNavigate} from "react-router-dom";
import {Button, Col, Container, Form, FormControl, InputGroup, ListGroup, Row} from "react-bootstrap";


let stompClient = null

const ChatRoom = () => {

    const navigate = useNavigate()
    const [chatId, setChatId] = useState(null)
    const [username, setUsername] = useState('')
    const [receiver, setReceiver] = useState('')
    const [textMessage, setTextMessage] = useState('')
    const [messages, setMessages] = useState([])

    const getData = async (chatId, receiver) => {
        await setChatId(chatId)
        await setReceiver(receiver)
    }

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
        <Container>
            <h1 className={'shadow-sm text-primary mt-1 p-3 text-center rounded'}>Chat with {receiver}</h1>
            <Row>
                <Col lg={5} md={6} sm={12} className={'p-1 m-auto shadow-sm rounded-lg'}>
                    <ListGroup variant={'flush'} className={'content-align-end'}>
                        {messages.map(message => message.sender === localStorage.getItem("username") ?
                            <ListGroup.Item
                                as="li"
                                className="d-flex justify-content-between align-item"
                                variant={'success'}
                            >
                                <div className="ms-2 me-auto">
                                    <div className="fw-bold">{message.sender}</div>
                                    {message.text}
                                </div>
                                <small>{message.createdAt}</small>
                            </ListGroup.Item>
                            : <ListGroup.Item
                                as="li"
                                className="d-flex justify-content-between align-items-start"
                                variant={'danger'}
                            >
                                <div className="ms-2 me-auto">
                                    <div className="fw-bold">{message.sender}</div>
                                    {message.text}
                                </div>
                                <small>{message.createdAt}</small>
                            </ListGroup.Item>)}
                    </ListGroup>
                    <Form>
                        <Form.Group className={'mb-3'}>

                        </Form.Group>
                        <InputGroup className="mb-3">
                            <FormControl
                                placeholder="Message"
                                aria-label="Recipient's username"
                                aria-describedby="basic-addon2"
                                onChange={event => setTextMessage(event.target.value)} value={textMessage}
                            />
                            <Button variant="outline-primary" id="button-addon2" onClick={() => send()}>
                                Send
                            </Button>
                        </InputGroup>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default ChatRoom;