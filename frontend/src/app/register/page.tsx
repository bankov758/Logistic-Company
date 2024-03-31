"use client";
import React, {Fragment, useEffect} from "react";
import { useFormState } from "react-dom";
import Link from "next/link";
import {useRouter} from "next/navigation";

import {register} from "@/lib/actions";

import SubmitButton from "@/components/UI/SubmitButton";
import UserIcon from "../../../public/icons/user.svg";
import InputSA from "@/components/UI/Input-SA";

const RegistrationPage = () => {
    const [registerState, registerAction] = useFormState(register, { message: null, errors: '' })
    const router = useRouter();

    useEffect(() => {
        if(registerState.message?.username) {
            router.push("/", { scroll: false });
        }
    }, [router, registerState]);

    return (
        <Fragment>
            <div className="bg-white p-40 rounded-xl shadow-md text-center">
                <div className="mb-5">
                    <h2>Register</h2>
                </div>

                <form action={registerAction}>

                    <div className="input-group">
                        <label className="block" htmlFor="username">Username</label>
                        <InputSA id="username" name="username" required placeholder="Username" iconSrc={UserIcon} />
                    </div>

                    <div className="input-group">
                        <label className="block" htmlFor='firstName'>Firstname</label>
                        <InputSA id="firstName" name="firstName" required placeholder="First Name" iconSrc={UserIcon} />
                    </div>

                    <div className="input-group">
                        <label className="block" htmlFor="lastName">Lastname</label>
                        <InputSA id="lastName" name="lastName" required placeholder="Last Name" iconSrc={UserIcon} />
                    </div>

                    <div className="input-group">
                        <label className="block" htmlFor="password">Password</label>
                        <InputSA id="password" type="password" name="password" required placeholder="Password" iconSrc={UserIcon} />
                    </div>

                    <div className="input-group">
                        <label className="block" htmlFor="confirmPassword">Confirm Password</label>
                        <InputSA id="confirmPassword" type="password" name="confirmPassword" required placeholder="Confirm Password" iconSrc={UserIcon} />
                    </div>

                    <div className="flex justify-center items-center">
                        <SubmitButton formState={registerState}/>
                    </div>

                    <p>Already a member?</p>
                    <Link href="/login" className='hover:font-bold'>Sign in</Link>
                </form>
            </div>
        </Fragment>
    )
}

export default RegistrationPage;