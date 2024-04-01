"use client";

import React, {Fragment, useEffect} from "react";
import Link from "next/link";
import {useFormState} from "react-dom";
import {useRouter} from "next/navigation";
import {login} from "@/lib/actions";

import SubmitButton from "@/components/UI/SubmitButton";
import InputSA from "@/components/UI/Input-SA";

// Icons
import UserIcon from '../../../public/icons/user.svg';
import LockIcon from '../../../public/icons/lock.svg';

const LoginPage: React.FC = () => {
    const [loginState, loginAction] = useFormState(login, { message: '', errors: '' });
    const router = useRouter();

    useEffect(() => {
        if (loginState.message && typeof loginState.message === 'object' && loginState.message?.username) {
            router.push("/", { scroll: false });
        }
    }, [loginState, router]);

    return (
        <Fragment>
            <div>
                <div className="bg-white p-40 rounded-xl shadow-md text-center">
                    <div className="mb-5">
                        <h2>Login</h2>
                    </div>

                    <form action={loginAction}>

                        <div className="input-group">
                            <label className="block" htmlFor='username'>Username</label>
                            <InputSA id="username" name="username" required placeholder="Username" iconSrc={UserIcon} />
                        </div>

                        <div className="input-group">
                            <label className="block" htmlFor='password'>Password</label>
                            <InputSA id="password" type="password" name="password" required placeholder="Password" iconSrc={LockIcon} />
                        </div>

                        <div className="flex justify-center items-center">
                            <SubmitButton formState={loginState} />
                        </div>

                        <p>Not a member yet?</p>
                        <Link href="/register" className='hover:font-bold'>Sign up</Link>
                    </form>
                </div>
            </div>
        </Fragment>
    )
}
export default LoginPage;
