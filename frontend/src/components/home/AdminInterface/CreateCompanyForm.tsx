"use client";

import React from "react";
import {useFormState} from "react-dom";
import {createCompany} from "@/lib/adminActions";
import {Session} from "@/lib/auth";
import SubmitButton from "@/components/UI/SubmitButton";

const CreateCompanyForm: React.FC<{ session: Session | null }> = ({ session }) => {
    const [createCompanyState, createCompanyAction] = useFormState(createCompany.bind(null, session), { message: "", errors: "" })
    //TODO: Add the expected props
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