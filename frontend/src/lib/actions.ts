"use server"; //module level Server Actions defined by 'use server' React directive

import { z } from "zod";
import {signIn} from "@/lib/auth";
import {redirect} from "next/navigation";

const loginSchema = z.object({
	username: z.string().trim().min(4, { message: "Username must be at least 4 characters!" }),
	password: z
		.string()
		.trim()
		.min(4, { message: "You've provided an invalid password!" })
});

export const login = async (initialState: any, formData: FormData) => {

	const fields = {
		username: (formData.get("username") as string) || "",
		password: (formData.get("password") as string) || ""
	};

	const validateFields = loginSchema.safeParse({
		username: fields.username,
		password: fields.password
	});

	if (!validateFields.success) {
		return {
			errors: validateFields.error.issues,
			message: ""
		};
	}

	try {
		//make an API call to the server to login the user
		const response = await fetch('http://localhost:8080/api/auth/login', {
			method: "POST",
			body: JSON.stringify(fields),
			headers: {
				'Content-Type': "application/json",
			}
		})

		if( !response.ok ) {
			throw new Error("Something went wrong! Sign up process was unsuccessfull!")
		}
		const data = await response.json();
		await signIn(data.username, data.roles)

		return {
			errors: '',
			message: data
		}

	} catch (error) {
		if( error instanceof Error ) {
			return {
				errors: error.message || "Something went wrong!",
				message: ""
			};
		}

		return {
			errors: "Something went wrong!",
			message: ""
		};
	}
}

const registerSchema = z.object({
	username: z.string().trim().min(4, { message: "You should provide a username with at least 4 characters!" }),
	firstName: z.string(),
	lastName: z.string(),
	password: z
		.string()
		.trim()
		.min(4, { message: "You've provided an invalid password!" }),
	confirmPassword: z
		.string()
		.trim()
});

export const register = async (initialState: any, formData: FormData) => {
	const username = formData.get('username') as string;
	const firstName = formData.get('firstName') as string;
	const lastName = formData.get('lastName') as string;
	const password = formData.get('password')! as string;
	const confirmPassword = formData.get('confirmPassword')! as string;

	const fields = {
		username,
		firstName,
		lastName,
		password,
		confirmPassword
	}

	if( password.trim() !== confirmPassword.trim() ) {
		return {
			message: '',
			errors: "Passwords do not match!"
		}
	}

	const validateSchema = registerSchema.safeParse(fields);

	if (!validateSchema.success ) {
		return {
			message: "",
			errors: validateSchema.error.issues
		}
	}

	try {
		const response = await fetch('http://localhost:8080/api/auth/signup', {
			method: "POST",
			body: JSON.stringify(fields),
			headers: {
				'Content-Type': "application/json",
				Accept: "*/*"
			}
		})

		if( !response.ok ) {
			throw new Error("Something happened! Registration process was unsuccessful!")
		}

		const data = await response.json();
		await signIn(data.username, data.roles);

		return {
			errors: '',
			message: data
		}

	} catch ( error ) {
		if( error instanceof Error ) {
			return {
				errors: error.message || "Something went wrong!",
				message: ""
			};
		}

		return {
			errors: "Something went wrong!",
			message: ""
		};
	}
}