import React, { Component } from 'react';
import { Navbar, NavbarBrand, Button, Nav } from 'react-bootstrap';
import { connect } from 'react-redux';
import { login } from '../actions/auth-action'
import { bindActionCreators } from 'redux';
import { RootState } from '../reducers';
import { LoginState } from '../models/loginState';
import { isEmailValid } from '../util/Utils'
import { ProgressBar } from 'react-bootstrap';
import "../styles/login.css"

interface Prop {
    loginState: LoginState,
    history: any,
    login: (payload: any) => void
}

interface State {
    fields: { [key: string]: string; },
    errors: { [key: string]: string; },
}

class Login extends Component<Prop, State> {
    constructor(props: any) {
        super(props)
        this.state = {
            fields: {},
            errors: {},
        }
    }
    handleChange(field: string, e: React.ChangeEvent<HTMLInputElement>) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
        this.setState({ errors: {} });
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault()
        if (!this.isFormValid()) {
            return
        }
        const payload = {
            email: this.state.fields["email"],
            password: this.state.fields["password"]
        }
        this.props.login(payload)
    }
    isFormValid = () => {
        let fields = this.state.fields;
        let errors = this.state.errors;
        let formValid = true;
        if (!fields["email"] || !isEmailValid(fields["email"])) {
            errors["email"] = "Invalid email";
            formValid = false;
        } else if (!fields["password"]) {
            errors["password"] = "Password cannot be empty";
            formValid = false;
        }
        if (!formValid) {
            this.setState({ errors });
        } else {
            this.setState({ errors: {} });
        }
        return formValid;
    }
    render() {
        if(this.props.loginState.success) {
            this.props.history.replace('/')
        }
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <NavbarBrand href="/">CRED</NavbarBrand>
                    <Nav className="ml-auto">
                        <Button variant="outline-primary" href="/signup">Signup</Button>
                    </Nav>
                </Navbar>
                <form className="login-form">
                    <h4 className="login-form-heading">Sign In</h4>
                    <div className="form-group">
                        <label>Email address</label>
                        <input
                            type="email"
                            className="form-control"
                            placeholder="Enter email"
                            onChange={this.handleChange.bind(this, "email")} />
                        <span className="error-message">{this.state.errors["email"]}</span>
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            className="form-control"
                            placeholder="Enter password"
                            onChange={this.handleChange.bind(this, "password")} />
                        <span className="error-message">{this.state.errors["password"]}</span>
                    </div>
                    <div className="form-group">
                        <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                        </div>
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block"
                        disabled={this.props.loginState.inProgress}
                        onClick={this.handleSubmit.bind(this)}>
                        Submit
                    </button>
                    {this.props.loginState.inProgress ? <ProgressBar className="progressbar" animated now={100} /> : ""}
                    {this.props.loginState.error ? <span className="row d-flex justify-content-center error-message">{this.props.loginState.error}</span> : ""}
                </form>
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { loginState: state.loginReducer.loginState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ login }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Login);