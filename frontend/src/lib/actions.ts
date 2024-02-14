"use server"; //module level Server Actions defined by 'use server' React directive

import { z } from "zod";

const loginSchema = z.object({
	email: z.string().trim().email({ message: "Invalid email address!" }),
	password: z
		.string()
		.trim()
		.min(4, { message: "You've provided an invalid password!" })
});

export async function login(initialState: any, formData: FormData) {
	const fields = {
		email: (formData.get("email") as string) || "",
		password: (formData.get("password") as string) || ""
	};

	const validateFields = loginSchema.safeParse({
		email: fields.email,
		password: fields.password
	});

	if (validateFields.success === false) {
		return {
			errors: validateFields.error.issues,
			message: ""
		};
	}

	try {
		//make an API call to the server to login the user

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

	return {
		errors: null,
		message: "You've successfully logged in!"
	};
}
