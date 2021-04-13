import {Modal, Button, Form} from 'react-bootstrap';
import {useEffect, useContext} from 'react';
import {UserContext} from '../../conf/UserContext';
import Api from '../../api/Api';
import useCMovieForm from '../../validation/Movie/useCMovieForm';
import validateCMovieForm from '../../validation/Movie/validateCMovieForm';


const treatData = (data, username) => { 
    let movie = null;

    if (data) {
        movie = {
            "title": data.title,
            "type": data.type,
            "person": {
                "name": username
            },
            "actors": treatActors(data.actors)
        }
    }

    return movie;         
}

const treatActors = actorsData => {
    let actors = []

    if(actorsData.indexOf(',') !== -1){
        let splitter = actorsData.split(',');
        for (let i = 0; i < splitter.length; i++) {
            actors.push({name:  splitter[i]});
        }
    }else{
        actors.push({ name : actorsData});
    }

    return actors;
}

const EditModal = props => {
    const {values, setValues, errors, handleChange, handleSubmit, hideModal} = useCMovieForm(validateCMovieForm);
    const {currentMovie, show, handleClose, action, setMessage, setMessageStyle, fetchMovies} = props;
    const {user, credential} = useContext(UserContext);
    const [token, setToken] = credential;
    const api = new Api(token);
    const [username, setUsername] = user;
    const successMessage = "The movie is updated succesufully";
    const successMessageStyle = "success";
    const failedMessageStyle = "danger";
    const failedMessage = "The movie couldn't be updated";

    useEffect(()=>{

        let actorsName = '';

        currentMovie.actors.map(actor => {
            if(currentMovie.actors.length > 1){
                actorsName +=  ',' + actor.name
            }if(currentMovie.actors.length === 1){
                actorsName = actor.name
            }
        });

        actorsName = actorsName.indexOf(',') ? actorsName : actorsName.replace(/^\,/, "")

        setValues({
            title: currentMovie.name,
            type: currentMovie.type,
            actors: actorsName
        })
    }, []);

    const updateTheMovie = (values, username, id) =>{
        let newMovie = treatData(values, username);
        api.update(newMovie, id)
        .then(response =>{
            if (response.status === 200){
                setMessage(successMessage);
                setMessageStyle(successMessageStyle);

                //show movies to see refressh the main movies list
                fetchMovies();
            }
        }).catch(err => {
            
            setMessage(`${failedMessage} ` + err);
            setMessageStyle(failedMessageStyle);
            
        });
        action(true);

    }
    
    return(
        <> 
            <Modal
                show={show}
                
                onHide={ _ => {
                    handleClose()
                }}>
                <Modal.Header closeButton>
                    <Modal.Title>Add new Movie</Modal.Title>
                </Modal.Header>
                    <Form className=" w-100" onSubmit={handleSubmit}>
                    <Modal.Body>

                        <div className="global_error_section">

                        </div>
                        <div className="">
                            <Form.Group controlId={errors.title && "error__border"}>
                                <Form.Label>Title</Form.Label>
                                <Form.Control type="text" name="title" value={values.title} onChange={handleChange}/>    
                                {errors.title && <p className={'error_msg'}>{errors.title}</p>}

                            </Form.Group>
                            <Form.Group controlId={errors.type && "error__border"}>
                                <Form.Label>Movie Type</Form.Label>
                                <Form.Control as="select" name="type" onChange={handleChange}>
                                    <option value="Action">Action</option>
                                    <option value="Science">SCience</option>
                                    <option value="Adventure">Adventure</option>
                                    <option value="Drama">Drama</option>
                                </Form.Control> 

                                {errors.type && <p className={'error_msg'}>{errors.type}</p>}
                            </Form.Group>
                            <Form.Group controlId={errors.actors && "error__border"}>
                                <Form.Label>Actors :
                                    <p><b>(If you add more than 1 actor, SEPARATE them with comma ',')</b> i.e : Jhon,Lauren Smith,Hannah</p>
                                </Form.Label>
                                <Form.Control type="text" name="actors" value={values.actors} onChange={handleChange}/> 
                                {errors.actors && <p className={'error_msg'}>{errors.actors}</p>}
                            </Form.Group>
                        </div>
                   
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={_ => {handleClose()}}>
                        Close
                    </Button>

                    <Button variant="warning" type="submit" onClick ={ _ =>{
                        if(hideModal){
                            updateTheMovie(values, username, currentMovie.id);
                            handleClose();
                        }
                    }}>
                        Update
                    </Button>
                </Modal.Footer>
                <div>
                    {
                        JSON.stringify(treatData(values, username), null, 4)
                    }
                </div>
                <p></p>
                    <p>
                    </p>
                </Form>
            </Modal>
        </>
    )
}

export default EditModal;