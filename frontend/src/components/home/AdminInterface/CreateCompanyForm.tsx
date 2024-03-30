"use client";

import React, {useEffect} from "react";
import {useFormState} from "react-dom";
import {createCompany} from "@/lib/adminActions";
import SubmitButton from "@/components/UI/SubmitButton";

const CreateCompanyForm: React.FC<{ onSuccess: () => void }> = ({ onSuccess }) => {
    const [createCompanyState, createCompanyAction] = useFormState(createCompany, { message: "", errors: "" })

    useEffect(() => {
        console.log(createCompanyState)

        if( createCompanyState.message ) {
            onSuccess();
        }
    }, [createCompanyState, onSuccess]);

    return (
        <form
            action={createCompanyAction}
            className="flex items-center justify-center gap-x-3 pb-3"
        >
            <label className="block text-gray-500" htmlFor='company_name'>Company name</label>
            <input
                type="text"
                id="company_name"
                className="input-info-dialog"
                name='company_name'
                placeholder="Speedy"
            />
            <br/>
            <div className='flex gap-x-2 px-5 py-3 text-gray-500'>
                <SubmitButton formState={createCompanyState} />
            </div>
        </form>
    )
};

export default CreateCompanyForm;