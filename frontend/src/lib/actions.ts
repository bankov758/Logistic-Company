"use server"; //module level Server Actions defined by 'use server' React directive

import {z, ZodIssue} from "zod";
import {Session, signIn} from "@/lib/auth";
import {selectorItem} from "@/components/UI/DataSelectorWrapper";

export type FormState = {
    message: null | string | { username: string; roles: string[]; };
    errors: ZodIssue[] | null | string;
}

const loginSchema = z.object({
    username: z.string().trim().min(4, {message: "Username must be at least 4 characters!"}),
    password: z
        .string()
        .trim()
        .min(4, {message: "You've provided an invalid password!"})
});

export const login = async (initialState: FormState, formData: FormData) => {

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

        if (!response.ok) {
            throw new Error("Something went wrong! Sign up process was unsuccessfull!")
        }
        const data: { username: string; roles: string[]; } = await response.json();
        await signIn(data.username, data.roles)

        return {
            errors: '',
            message: data
        }

    } catch (error) {
        if (error instanceof Error) {
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
    username: z.string().trim().min(4, {message: "You should provide a username with at least 4 characters!"}),
    firstName: z.string(),
    lastName: z.string(),
    password: z
        .string()
        .trim()
        .min(4, {message: "You've provided an invalid password!"}),
    confirmPassword: z
        .string()
        .trim()
});

export const register = async (initialState: FormState, formData: FormData) => {

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

    if (password.trim() !== confirmPassword.trim()) {
        return {
            message: '',
            errors: "Passwords do not match!"
        }
    }

    const validateSchema = registerSchema.safeParse(fields);

    if (!validateSchema.success) {
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

        if (!response.ok) {
            throw new Error("Something happened! Registration process was unsuccessful!")
        }

        const data = await response.json();
        await signIn(data.username, data.roles);

        return {
            errors: '',
            message: data
        }

    } catch (error) {
        if (error instanceof Error) {
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

export const getCompanies = async (): Promise<selectorItem[] | null> => {
    try {
        const response = await fetch("http://localhost:8080/api/companies", {
            headers: {
                "Content-Type": "application/json",
                Accept: "*/*"
            }
        })

        if (!response.ok) {
            throw new Error("Something went wrong!");
        }

        const data = await response.json()

        if (data) {

            return data.map((company: any) => ({
                title: company.name,
                code: company.name,
                id: company.id,
            }));
        }
        return null;
    } catch (err) {
        if (err instanceof Error) {
            throw err;
        }
    }

    return null;

}

export const getCouriers = async (): Promise<selectorItem[] | null> => {
    try {
        const response = await fetch("http://localhost:8080/api/couriers", {
            headers: {
                "Content-Type": "application/json",
                Accept: "*/*"
            }
        })

        if (!response.ok) {
            throw new Error("Something went wrong!");
        }

        const data = await response.json()

        if (data) {

            return data.map((courier: any) => ({
                title: courier.username,
                username: courier.username,
                id: courier.id,
            }));
        }
        return null;
    } catch (err) {
        if (err instanceof Error) {
            throw err;
        }
    }

    return null;

}

export const getUsers = async (): Promise<selectorItem[] | null> => {
    try {
        const response = await fetch("http://localhost:8080/api/users", {
            headers: {
                "Content-Type": "application/json",
                Accept: "*/*"
            }
        })

        if (!response.ok) {
            throw new Error("Something went wrong!");
        }

        const data = await response.json()

        if (data) {

            return data.map((user: any) => ({
                title: user.username,
                id: user.id,
                username: user.username,
                firstName: user.firstName,
                lastName: user.lastName,
                roles: user.roles
            }));
        }
        return null;
    } catch (err) {
        if (err instanceof Error) {
            throw err;
        }
    }

    return null;
}

export const getUserId = async (userName: string | undefined, users: selectorItem[]) => {
    const foundUser = users.find(user => user.title === userName)
    if (foundUser) return foundUser.id;

    return null;
}

const createNewOrderSchema = z.object({
    departureAddress: z.string().trim(),
    arrivalAddress: z.string().trim(),
    sentDate: z.date(),
    weight: z.string().trim(),
});

export const createAnOrder = async (
    session: Session | null,
    senderId: string | number | undefined,
    receiverId: string | number | undefined,
    courierId: string | number | undefined,
    companyId: string | number | undefined,
    users: selectorItem[],
    initialState: FormState,
    formData: FormData,
) => {

    const departureAddress = formData.get('departureAddress');
    const arrivalAddress = formData.get('arrivalAddress');
    const sentDate = formData.get('sentDate');
    const weight = formData.get('weight');

    const employeeId = await getUserId(session?.username, users);


    const parsedSentDate = sentDate ? new Date(sentDate.toString()) : null;


    const fields = {
        departureAddress,
        arrivalAddress,
        weight,
        senderId,
        receiverId,
        employeeId,
        sentDate: parsedSentDate,
        courierId,
        companyId
    }
    const validateSchema = createNewOrderSchema.safeParse(fields);
    if (!validateSchema.success) {
        return {
            message: "",
            errors: validateSchema.error.issues
        }
    }

    try {
        const response = await fetch('http://localhost:8080/api/shipments', {
            method: "POST",
            body: JSON.stringify(fields),
            headers: {
                'Content-Type': "application/json",
                Accept: "*/*"
            }
        })

        if (!response.ok) {
            throw new Error("Something happened! Creating new order process was unsuccessful!")
        }

        const data = await response.json();
        return {
            errors: '',
            message: data
        }

    } catch (error) {
        console.error("Error creating new order:", error);

        if (error instanceof Error) {
            return {
                errors: error.message || "Something went wrong!",
                message: ""
            };
        } else if (error instanceof Response) {
            const responseText = await error.text();
            console.error("Server response:", responseText);

            // Parse the responseText and handle accordingly

            return {
                errors: "Something went wrong!",
                message: ""
            };
        }

        return {
            errors: "Something went wrong!",
            message: ""
        };
    }
}





