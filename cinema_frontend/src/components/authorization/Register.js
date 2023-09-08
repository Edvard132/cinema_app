import React from 'react';
import { useState } from 'react';
import api from '../../api/axiosConfig';
import {useRef} from 'react';
import {useGlobalContext} from '../../context';
import "./modal.css"
import { setAuthToken } from '../../api/axiosConfig';


const Login = () => {
    const {setAuthorized, closeModal} = useGlobalContext()
    const name = useRef();
    const email = useRef();
    const password = useRef();
    const [errorMessage, setErrorMessage] = useState('');

    const nameregex = new RegExp( "[a-zA-Z0-9 ]*");
    const emailregex = new RegExp("[A-Za-z0-9._%]+@[A-Za-z0-9.%]+\.[a-z]{2,3}");
    const passwordregex = new RegExp("^(?=.*[A-Z])(?=.*\d).{6,}$");

    const handleSignup = async (e) => {
        e.preventDefault();
        if (nameregex.test(name)) {
            setErrorMessage('Invalid name. Please use only letters, numbers, and spaces.');
            return;
        }

        if (emailregex.test(email)) {
            setErrorMessage('Invalid email address format.');
            return;
        }

        if (passwordregex.test(password)) {
            setErrorMessage('Invalid password format. It should contain at least one uppercase letter, one digit, and be at least 6 characters long.');
            return;
        }
        console.log("tyra")
        
        try {
            const response = await api.post("/api/v1/user/signup",
            {   
                name: name.current.value, 
                email: email.current.value,
                password: password.current.value
            });
            console.log("response " + response.data);
            
            if (response.status === 201) {
                setAuthToken(response.data);
                setAuthorized(true);
                closeModal(); 
            } 
            else if (response.data === "User with this email already exists.") {
                setErrorMessage(response.data); 
            }
            else {
                setErrorMessage("something went wrong. Please"); 
            }
        } catch (error) {
            console.error('An error occurred:', error);
            setErrorMessage('An error occurred while processing your request.');
        }
    };

    return (
        <div className='m-1 p-2'>
            <div >
                <div>
                    <h2>Register</h2>
                    <hr/>
                </div>
                <div className="row">
                    <div className="col-sm-12">
        
                        <form>
                                <div className="">
                                    <label>Name</label>
                                    <input type="name"  className="form-control" id="name" placeholder="Enter Name"
                                    ref={name}
                                    />
                                </div>
                                <div className="mt-2">
                                    <label>Email</label>
                                    <input type="email"  className="form-control" id="email" placeholder="Enter email"
                                    ref={email}
                                    />
                                </div>
                                <div className="my-2">
                                    <label>Password</label>
                                    <input type="password"  className="form-control" id="password" placeholder="Enter password"
                                    ref={password}
                                    />
                                </div>
                            <button type="submit" className="btn btn-primary btn-lg mt-2" onClick={handleSignup} >Register</button>
                        </form>
                    </div>
                </div>
                {errorMessage && <p className='text-danger'>{errorMessage}</p> }
            </div>
        </div>
    );
};

export default Login;