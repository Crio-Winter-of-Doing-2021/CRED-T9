import React, { Component } from 'react';
import { Navbar, NavbarBrand, Button, Nav } from 'react-bootstrap';
import "../styles/signup.css"

class Login extends Component {
    render() {
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <NavbarBrand href="/">CRED</NavbarBrand>
                </Navbar>
                <form className="signup-form">
                    <h4 className="signup-form-heading">Sign up</h4>
                    <div className="form-group">
                        <label>First name</label>
                        <input type="text" className="form-control" placeholder="First name" />
                    </div>
                    <div className="form-group">
                        <label>Last name</label>
                        <input type="text" className="form-control" placeholder="Last name" />
                    </div>
                    <div className="form-group">
                        <label>Email address</label>
                        <input type="email" className="form-control" placeholder="Enter email" />
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input type="password" className="form-control" placeholder="Enter password" />
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                    <p className="forgot-password text-right">
                        Already registered? <a href="/login">sign in</a>
                    </p>
                </form>
            </div>
        )
    }
}

export default Login