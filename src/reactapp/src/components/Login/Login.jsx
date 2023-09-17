import React, { useState } from 'react';
import './Login.css';
import { LOGIN, SIGNUP } from '../../Constants';
import { useDispatch } from 'react-redux';
import { setLoggedInUser } from '../../store/userSlice';

const Login = () => {
    const dispatch = useDispatch();
    const [loginSignupToggle, setLoginSignupToggle] = useState(true);
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
    });

    const login = () => {
        fetch(LOGIN, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: formData.email,
                password: formData.password,
            }),
        })
            .then((response) => {
                if (response.status == '200') {
                    return response.json();
                }
                return response;
            })
            .catch((err) => console.error(err))
            .then((data) => {
                console.log(data);
                setFormData({
                    name: '',
                    email: '',
                    password: '',
                });
                if (data !== null && typeof data === 'object') {
                    dispatch(setLoggedInUser(data));
                }
            });
    };

    const signup = () => {
        fetch(SIGNUP, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: formData.name,
                password: formData.password,
                email: formData.email,
            }),
        })
            .then((response) => {
                if (response.status == '200') {
                    return response.json();
                }
                return response;
            })
            .then((data) => {
                console.log(data);
                setFormData({
                    name: '',
                    email: '',
                    password: '',
                });
                if (data !== null && typeof data === 'object') {
                    dispatch(setLoggedInUser(data));
                }
            });
    };

    const formChangeHandler = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        setFormData({ ...formData, [name]: value });
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <div className="login-box-header">Login Form</div>
                <div
                    className="login-box-login-type-btn"
                    onClick={(e) => {
                        e.preventDefault();
                        const btn = e.target.textContent;
                        console.log(btn);
                        setLoginSignupToggle(btn === 'Login');
                    }}
                >
                    <div
                        className={
                            'login-type-item login-type-btn ' +
                            (loginSignupToggle ? 'active-btn' : '')
                        }
                    >
                        Login
                    </div>
                    <div
                        className={
                            'login-type-item singup-type-btn ' +
                            (!loginSignupToggle ? 'active-btn' : '')
                        }
                    >
                        Signup
                    </div>
                </div>
                <form className="login-box-form">
                    {loginSignupToggle ? (
                        <>
                            <input
                                type="text"
                                name="email"
                                className="login-form-field email-field"
                                placeholder="Email Address"
                                autoComplete="off"
                                value={formData.email}
                                onChange={formChangeHandler}
                            />
                            <input
                                type="password"
                                name="password"
                                className="login-form-field password-field"
                                placeholder="Password"
                                autoComplete="off"
                                value={formData.password}
                                onChange={formChangeHandler}
                            />
                            <div className="forgot-password">
                                Forgot password?
                            </div>
                            <div
                                className="login-btn"
                                onClick={() => {
                                    login();
                                }}
                            >
                                Login
                            </div>
                        </>
                    ) : (
                        <>
                            <input
                                type="text"
                                name="name"
                                className="login-form-field name-field"
                                placeholder="Name"
                                autoComplete="off"
                                value={formData.name}
                                onChange={formChangeHandler}
                            />
                            <input
                                type="text"
                                name="email"
                                className="login-form-field email-field"
                                placeholder="Email Address"
                                autoComplete="off"
                                value={formData.email}
                                onChange={formChangeHandler}
                            />
                            <input
                                type="password"
                                name="password"
                                className="login-form-field password-field"
                                placeholder="Password"
                                autoComplete="off"
                                value={formData.password}
                                onChange={formChangeHandler}
                            />
                            <div className="login-btn" onClick={() => signup()}>
                                Sign up
                            </div>
                        </>
                    )}
                </form>
                {loginSignupToggle && (
                    <div className="login-box-footer">
                        <span className="signup-pre-label">Not a member?</span>
                        <div
                            className="signup-footer-btn"
                            onClick={(e) => setLoginSignupToggle(off)}
                        >
                            Signup now
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Login;
