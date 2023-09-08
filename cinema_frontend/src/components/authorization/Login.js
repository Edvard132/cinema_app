import React from 'react';
import { useState } from 'react';
import api from '../../api/axiosConfig';
import { setAuthToken, getAuthToken } from '../../api/axiosConfig';
import {useRef} from 'react';
import {useGlobalContext} from '../../context';
import "./modal.css"

const Login = () => {
    const {setAuthorized, closeModal, setUserEmail, setWatchListId} = useGlobalContext()
    const email = useRef();
    const password = useRef();
    const [errorMessage, setErrorMessage] = useState('');

    const getWatchListId = async (mail) => {
        try {
            const response = await api.get("/api/v1/user/getUserId", 
            {
                params: {
                    email: mail,
                }
            });
            console.log(" watchlist response : " + response.data);
            setWatchListId(response.data);
        } catch (error) {
            console.error('An error occurred:', error);
        }
    }   

    const handleLogin = async (e) => {
        e.preventDefault()
        try {
            const response = await api.post("/api/v1/user/login",
            {
                email: email.current.value,
                password: password.current.value
            }
            );
            console.log("response " + response.data);
            
            if (response.status === 200) {
                setAuthToken(response.data);
                setAuthorized(true);
                setUserEmail(email.current.value);
                closeModal(); 
                getWatchListId(email.current.value)
                console.log(getAuthToken());

            } 
            else if (response.data === "Invalid password") {
                setErrorMessage("Invalid password"); 
            }
            else {
                setErrorMessage("User does not exist"); 
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
                    <h2>Login</h2>
                    <hr/>
                </div>
                <div className="row">
                    <div className="col-sm-12">
        
                        <form>
                                <div className="">
                                    <label>Email</label>
                                    <input className="form-control" id="email" placeholder="Enter email"
                                    ref={email}
                                    />
                                    </div>
                                <div className="my-2">
                                    <label>Password</label>
                                    <input type="password"  className="form-control" id="password" placeholder="Enter password"
                                    ref={password}
                                    />
                                </div>
                            <button type="submit" className="btn btn-primary btn-lg mt-2" onClick={handleLogin} >Login</button>
                        </form>
                    </div>
                </div>
                {errorMessage && <p className='text-danger '>{errorMessage}</p> }
            </div>
        </div>
    );
};

export default Login;