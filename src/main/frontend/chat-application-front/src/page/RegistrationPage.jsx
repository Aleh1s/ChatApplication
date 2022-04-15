import React, {useState, useEffect} from 'react';
import {doRegister} from "../Queries";
import { useNavigate } from "react-router-dom";
import {Button, Col, Container, Form, FormControl, InputGroup, Row} from "react-bootstrap";

const RegistrationPage = () => {

    const navigate = useNavigate()
    const [status, setStatus] = useState(false)
    const [data, setData] = useState({
        email: "",
        password: "",
        username: ""
    })

    useEffect(() => {
        if (status === true) {
            navigate('/login')
        }
    }, [status])

    return (
        <Container>
            <h1 className={'shadow-sm text-primary p-3 text-center rounded'}>Registration</h1>
            <Row className={'mt-5'}>
                <Col lg={5} md={6} sm={12} className={'p-5 m-auto shadow-sm rounded-lg'}>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" onChange={event => setData({...data, email: event.target.value})}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" onChange={event => setData({...data, password: event.target.value})}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" onChange={event => setData({...data, username: event.target.value})}/>
                        </Form.Group>
                        <Button variant="outline-primary col-12" onClick={() => doRegister(data, setStatus)}>
                            Submit
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default RegistrationPage;