'use client';

import React from "react";
import { useFormState } from "react-dom";

import Input from "@/components/UI/Input";

import PersonIcon from "../../../public/icons/user.svg";
import MessageIcon from "../../../public/icons/message.svg";
import SubmitButton from "@/components/UI/SubmitButton";

import { login } from "@/lib/actions";

const initialState = {
	errors: null,
	message: ""
};

const LoginForm: React.FC = () => {
    const [formState, formAction] = useFormState(login, initialState)

    return (
			<form action={formAction} className='grid grid-cols-1 lg:grid-cols-2 gap-4'>
				<div className="flex flex-col justify-start items-start gap-y-[10px]">
					<label htmlFor="email" className="block text-sm font-bold">
						Email
					</label>
					<Input
						id="email"
						type="email"
						name="email"
						required
						iconSrc={PersonIcon}
						iconAlt="Person Icon"
					/>
				</div>
				<div className="flex flex-col justify-start items-start gap-y-[10px]">
					<label htmlFor="password" className="block text-sm font-bold">
						Password
					</label>
					<Input
						id="password"
						type="password"
						name="password"
						required
						iconSrc={MessageIcon}
						iconAlt="Message Icon"
					/>
				</div>
				<SubmitButton 
                    buttonStyle="
                        lg:col-start-2 lg:col-end-3 justify-self-end
                        bg-green-500 hover:bg-green-600
                    "
                />
			</form>
		);
};

export default LoginForm;