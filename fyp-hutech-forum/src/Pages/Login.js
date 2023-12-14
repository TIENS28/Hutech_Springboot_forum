import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom'
import './Login.css';
import axios from 'axios';

function Login({ setIsNavbarVisible }) {
  
  const navigate = useNavigate();
  setIsNavbarVisible(false);
  return (
    <div className="login-flex-container">
      <div className='welcome-hutech-forum'>
        <h1 className='HUTECH'>HUTECH</h1>
        <h2 className='welcome'>Greetings and welcome to HUTECH Forum, a place where you can debate and exchange information with others
        users while also getting the most recent information about HUTECH</h2>
      </div>

      
      <div className='form-login'>
        <form action='/login' id = 'formLogin' method = 'post'>
          <div>
            <input className='input-login'
                  type="text" 
                  placeholder="Enter username ..."/>
          </div>

          <div>
            <input className='input-login'
                  type="password" 
                  placeholder="Enter password ..."/>
          </div>

          <div>
          <button
                  className="bt-login" 
                  type="submit" 
                  value="login" >login</button>
          </div>

          <div>
            <Link className='forgot-password' to='/forgotpassword'> Forgot Password ? </Link>
          </div>

          <div>
            <hr className='hr-login'/>
          </div>

          <div>
          <button onClick={()=>{navigate('/createaccount', {replace:true})}}
          className="bt-logins" 
                  type="submit" 
                  value="account" >Don't have an account yet?</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;