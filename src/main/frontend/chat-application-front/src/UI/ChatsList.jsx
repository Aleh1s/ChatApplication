import React, {useState} from 'react';
import {Container, ListGroup} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

const ChatsList = ({chats}) => {

    const navigate = useNavigate()

    const openChatWith = (chatData) => {
        localStorage.setItem('currentChatId', chatData.id)
        localStorage.setItem('receiver', chatData.members
            .map(member => member.username)
            .filter(username => username !== localStorage.getItem('username'))[0])
        navigate('/chat-room')
    }

    return (
        <Container>
            <ListGroup numbered variant={'flush'}>
                {chats.map(chatData =>
                    <ListGroup.Item
                        key={chatData.id}
                        as="li"
                        className="d-flex justify-content-between align-items-start"
                        variant={'primary'}
                        onClick={() => openChatWith(chatData)}
                    >
                        <div className="ms-2 me-auto">
                            <div className="fw-bold">{chatData.members.map(member => member.username)
                                .filter(username => username !== localStorage.getItem('username'))[0]}</div>
                            {`Last activity: ${chatData.lastActivity}`}
                        </div>
                    </ListGroup.Item>
                )}
            </ListGroup>
        </Container>
    );
};

export default ChatsList;