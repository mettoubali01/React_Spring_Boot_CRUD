    import {Modal, Button, Form} from 'react-bootstrap';
    import {useState, useEffect, useContext} from 'react';
    import useCMovieForm from '../../validation/Movie/useCMovieForm';
    import validateCMovieForm from '../../validation/Movie/validateCMovieForm';
    import {UserContext} from '../../conf/UserContext';
    import Api from '../../api/Api';

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

        if(actorsData.indexOf(',') != -1){
            let splitter = actorsData.split(',');
            for (let i = 0; i < splitter.length; i++) {
                actors.push({name:  splitter[i]});
            }
        }else{
            actors.push({ name : actorsData});
        }

        return actors;
    }

    const CreateModal = props => {
        const {values, errors, handleChange, handleSubmit, hideModal} = useCMovieForm(validateCMovieForm);
        const {fetchMovies, show, handleClose, setMessage, setMessageStyle, action} = props;    
        const {user, credential} = useContext(UserContext);
        const [token, setToken] = credential;
        const api = new Api(token);
        const [username, setUsername] = user;
        const successMessage = "The movie is created succesufully";
        const successMessageStyle = "success";
        const failedMessageStyle = "danger";
        const failedMessage = "The movie couldn't be created";

        const createNewMovie = (values, username) =>{
            let newMovie = treatData(values, username);

            //make request to create a new movie
            api.create(newMovie, username)
            .then(response =>{
                if (response.status == 200){
                    setMessage(successMessage);
                    setMessageStyle(successMessageStyle);

                    //when the request is success we fetch the movies again to see newest ones.
                    fetchMovies()
                }
                action(true);
            }).catch(err => {
                
                setMessage(`${failedMessage} ` + err);
                setMessageStyle(failedMessageStyle);
                
            });
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
                                            <option value="Action" selected>Action</option>
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

                            <Button variant="danger" type="submit" onClick ={ _ =>{
                                if(hideModal){
                                    createNewMovie(values, username);
                                    handleClose();
                                }
                            }}>
                                Save
                            </Button>
                        </Modal.Footer>
                    </Form>
                </Modal>
            </>
        )
    }

    export default CreateModal;