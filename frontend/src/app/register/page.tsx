"use client";
import {Fragment} from "react";

import Link from "next/link";
import { useFormState } from "react-dom";

import {register} from "@/lib/actions";

const RegistrationPage = () => {
    const [formState, registerAction] = useFormState(register, { message: null, errors: '' })
    console.log(formState)
    return (
        <Fragment>
            {formState.errors && typeof formState.errors === 'string' &&
                <div>{formState.errors}</div>
            }
            {formState.errors && typeof formState.errors === 'object' &&
                <div>
                    {
                        formState.errors
                            .map((error, index)=>
                                <p key={index}>{error.message}</p>
                            )
                    }
                </div>
            }
            <div className="bg-white p-40 rounded-xl shadow-md text-center">
                <div className="mb-5">
                    <h2>Register</h2>
                </div>

                <form action={registerAction}>
                    <div className="input-group">
                        <label className="block">Username</label>
                        <input type="text" name="username"
                               className="block p-3 w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Firstname</label>
                        <input type="text" name="firstName"
                               className="block p-3 w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Lastname</label>
                        <input type="text" name="lastName"
                               className="block p-3 w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Password</label>
                        <input type="password" name="password"
                               className="block p-3 w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Password</label>
                        <input type="password" name="confirmPassword"
                               className="block p-3 w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                    </div>
                    <div className="flex justify-center">
                        <button
                            type="submit"
                            className="block px-3 py-1.5 mt-1  relative rounded-xl bg-green-400 text-white border-none cursor-pointer"
                        >
                            Register
                        </button>
                    </div>
                    <p>
                        Already a member? <Link href="/login"> Sign in </Link>
                    </p>
                </form>
            </div>
        </Fragment>
    )
}

export default RegistrationPage;