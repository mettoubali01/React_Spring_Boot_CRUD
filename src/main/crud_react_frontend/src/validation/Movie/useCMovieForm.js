import {useEffect, useState} from 'react';

const useCMovieForm = (validateCMovieForm) => {
    const [errors, setErrors] = useState({});
    const [values, setValues] = useState({
        title: '',
        type: '',
        actors: ''
    });
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [hideModal, setHideModal] = useState(false);

    const handleChange = event => {
        const {name, value} = event.target;
        setValues({
            ...values,
            [name] : value
        })

        setErrors(
            validateCMovieForm(values)
        );
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        setErrors(validateCMovieForm(values));
        setIsSubmitting(true)
    }

    useEffect(_ => {
        if ( Object.keys(errors).length === 0 && isSubmitting){
            setIsSubmitting(false);
            setHideModal(true);

            //return here the data because sometimes the modal is not getting the 
            //updated hideModal value
            return {values, errors, handleChange, handleSubmit, hideModal}

        }else{
            setHideModal(false);
        }

    }, [errors])

    return {values, setValues, errors, handleChange, handleSubmit, hideModal}
}

export default useCMovieForm;