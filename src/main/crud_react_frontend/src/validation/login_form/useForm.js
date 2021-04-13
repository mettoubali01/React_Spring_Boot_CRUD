import {useState} from 'react';

const useForm =  (validateForm) => {
    const [values, setValues] = useState({
        username: '',
        password: '',
    });

    const [errors, setErrors] = useState({});

    const handlePwdChange = event => {
        const {name, value} = event.target;
        setValues({
            ...values,
            [name] : value
        })
        values[name] = value;

        setErrors(
            validateForm(name , value)
        );

        console.log("pwd error ", errors.password);
    }

    const handleUsernameChange = event => {
        const {name, value} = event.target;
        setValues({
            ...values,
            [name] : value
        })
        values[name] = value;

        setErrors(
            validateForm(name, value)
        );
    }

    return {values, errors, handlePwdChange, handleUsernameChange}
}

export default useForm;