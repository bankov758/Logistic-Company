"use client";
import React, {Fragment, useEffect} from "react";
import { useFormState } from "react-dom";
import Link from "next/link";
import {useRouter} from "next/navigation";

import {register} from "@/lib/actions";

import Notification from "@/components/UI/Notification";
import SubmitButton from "@/components/UI/SubmitButton";

const RegistrationPage = () => {
    const [registerState, registerAction] = useFormState(register, { message: null, errors: '' })
    const router = useRouter();

    useEffect(() => {
        if(registerState.message?.username) {
            router.push("/", { scroll: false });
        }
    }, [registerState]);

    return (
        <Fragment>
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
                               className="input-style"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Lastname</label>
                        <input type="text" name="lastName"
                               className="input-style"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Password</label>
                        <input type="password" name="password"
                               className="input-style"></input>
                    </div>
                    <div className="input-group">
                        <label className="block">Password</label>
                        <input type="password" name="confirmPassword"
                               className="input-style"></input>
                    </div>
                    <div className="flex justify-center">
                        <SubmitButton formState={registerState}/>
                    </div>
                    <p>
                        Already a member?
                        <Link href="/login"> Sign in </Link>
                    </p>
                </form>
            </div>
        </Fragment>
    )
}

export default RegistrationPage;