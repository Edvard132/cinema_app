import React from 'react'
import { FaTimes } from 'react-icons/fa'
import {useGlobalContext} from '../../context';
import "./modal.css"
import Login from './Login';
import Register from './Register';

const AuthorizationModal = () => {
  const {isModalOpen, closeModal, isLogin, setIsLogin} = useGlobalContext();


  return <div className= {isModalOpen ? "modal_window active" : "modal_window"}>
    <div className={isModalOpen ? "modal_contents active" : "modal_contents"}>
        <button className='close-btn' onClick={closeModal}>
            <FaTimes/>
        </button>
        <button className="btn" onClick={()=>setIsLogin(true)}>Login</button>
        <button className="btn" onClick={()=>setIsLogin(false)}>Register</button>

      {isLogin ? 
      <Login/> 
      : 
      <Register/>}
    </div>
      
  </div>
}

export default AuthorizationModal