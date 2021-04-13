import './App.css';
import Login from './components/login/Login';
import Nav from './components/nav/Nav';
import Main from './components/main/Main';
import {UserProvider} from './conf/UserContext';
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import { useState } from 'react';
import AlertMessage from './components/alert/AlertMessage';

function App() {
  const [messageFeedBack, setMessageFeedback] = useState("");
  const [messageFeedBackStyle, setMessageFeedbackStyle] = useState("");
  const [showAlert, setShowAlert] = useState(false);

  return (
    <div className="App">

      <UserProvider>
        <Router>
          <Nav />
          {showAlert && <AlertMessage setAlert={setShowAlert} variant={messageFeedBackStyle} children={messageFeedBack}/>}

          <Switch>
            <Route path={"/"} exact>
              <Login action={setShowAlert}
                     setMessage={setMessageFeedback} 
                     setMessageStyle={setMessageFeedbackStyle}/>
            </Route>
            <Route path={"/management"} exact>
              <Main action={setShowAlert}
                    setMessage={setMessageFeedback} 
                    setMessageStyle={setMessageFeedbackStyle} />
            </Route>
          </Switch>
        </Router>    
      </UserProvider>
    </div>
  );
}

export default App;
