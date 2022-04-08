import React from 'react';
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, Container, Navbar, Nav} from "react-bootstrap";

const NavBar = () => {
    return (
    <>
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand><Link className="nav-link active" to={"/"}>Home</Link></Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link><Link className="nav-link active" to={"/register"}>Register</Link></Nav.Link>
                    <Nav.Link><Link className="nav-link active" to={"/login"}>Login</Link></Nav.Link>
                    <Nav.Link><Link className="nav-link active" to={"/users"}>Users</Link></Nav.Link>
                    <Nav.Link><Link className="nav-link active" to={"/chats"}>Chats</Link></Nav.Link>
                    <Nav.Link><Link className="nav-link active" to={"/profile"}>{localStorage.getItem('username')}</Link></Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    < />
    );
};

export default NavBar;