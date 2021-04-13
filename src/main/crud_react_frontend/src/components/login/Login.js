import {Form, Container, Button } from 'react-bootstrap';
import useForm from '../../validation/login_form/useForm';
import { useContext } from 'react';
import api from '../../api/Api';
import { UserContext } from '../../conf/UserContext';
import {useHistory} from 'react-router-dom';
import './login.css';
import validateForm from '../../validation/login_form/validateForm';
import Api from '../../api/Api';

//this method to take of the The 'Bearer' word from the token.
const splitToken = (token) => {
    let split = token.substr(token.indexOf(" "), token.length)

    return split;
} 

const Login = ({setMessage, setMessageStyle, action}) =>{
    const {handleUsernameChange, handlePwdChange, errors, values} = useForm(validateForm);
    const {credential, logged, user} = useContext(UserContext);
    const [token, setToken] = credential;
    const [isAuthenticated, setIsAuthenticated] = logged;
    const [username, setUsername] = user;
    const api = new Api();
    const history = useHistory();
    const successMessage = "Logged In";
    const successMessageStyle = "success";
    const failedMessageStyle = "danger";
    const failedMessage = "Username and/or password are incorrect";

    const setTheAlert = (message, messageStyle) =>{
        setMessage(message);
        setMessageStyle(messageStyle);
        action(true);
    }

    const saveGolbalAppData = (response, credentials) => {
        setToken(splitToken(response.headers.authorization));            
        setIsAuthenticated(true);
        setUsername(credentials.username);
    }

    const handleSubmit = event => {
        event.preventDefault();

        let credentials = values;
           
        api.login(credentials)
            .then(response => { 

                if(response.status === 200){
                    //this method save in the context api:
                        //username of the user
                        //state of the authentication
                        //the user token
                    saveGolbalAppData(response, credentials);

                    //this method shows a successful alert
                    setTheAlert(successMessage, successMessageStyle);

                    //we send it to the main page
                    history.push("/management");
                }
        }).catch(error => {
            
            //this method shows a failed alert
            setTheAlert(failedMessage, failedMessageStyle);

        });
    }

    return (
        <>
            <Container>
                <Container className="login__container" fluid> 
                        <Form onSubmit={handleSubmit} className="login__form w-100">
                            <legend className="form__title">Login</legend>
                            <div className="form_WN_legend">
                                <Form.Group controlId={errors.username && "error__border"}>
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control type="text" name="username" value ={values.username} onChange={handleUsernameChange}/>    
                                    {errors.username && <p className="error_msg">{errors.username}</p>}

                                </Form.Group>
                                <Form.Group controlId={errors.password && "error__border"}>
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" name="password" 
                                    value ={values.password} onChange={handlePwdChange}/>
                                    {errors.password && <p className="error_msg">{errors.password}</p>}
                                </Form.Group>
                               
                                <Button variant="primary" type="submit">Submit</Button>
                            </div>
                        </Form>
                </Container>
            </Container>
        </>
    )
}

export default Login;