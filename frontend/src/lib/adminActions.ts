"use server";
import { getSession } from "@/lib/auth";

export interface Company {
    id: number;
    name: string;
    income: number;
}
export const createCompany = async (
    initialState: { message: string; errors: [] | string; },
    formData: FormData,
    session: { username: string; roles: string[]; } | null | undefined
) => {
    const newCompanyName = formData.get("company_name") as string;

    try {
        if (!newCompanyName.trim()) {
            return {
                message: null,
                errors: "Company name cannot be empty!"
            }
        }

        const requestData = {
            name: newCompanyName.trim(),
        };

        const response = await fetch('http://localhost:8080/api/companies', {
            method: 'POST',
            body: JSON.stringify(requestData),
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });

        if (response.ok) {
            return {
                message: "You've successfully create a company! "
            }
        } else {
            console.error('Failed to create company:', response.statusText);
        }
    } catch (error) {
        console.error('Error creating company:', error);
    }
};

export const deleteCompany = async (
    companyName: string,
    companyData: Company[] ,
    session: { username: string; roles: string[]; } | null | undefined
) => {

    const companyId = getCompanyId(companyName, companyData);
    try {
        const response = await fetch(`http://localhost:8080/api/companies/${companyId}`, {
            method: 'DELETE',
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete company")
        }else {
            console.log("Company successfully deleted!")

        }
    } catch (error) {
        console.error("Failed to delete the company", error);
    }
}
export const getCompanyId = (companyName: string, companyData: Company[]) => {
    const foundCompany = companyData.find(company => company.name === companyName);
    if (foundCompany) {
        return foundCompany.id;
    }

    return null;
}
export const editCompany = async (
    companyName: string,
    updatedCompanyName: string,
    companyData: Company[] , session: { username: string; roles: string[]; } | null | undefined
) => {

    const companyId = getCompanyId(companyName, companyData);
    const requestedData = {
        id: companyId,
        name: updatedCompanyName
    }
    try {
        const response = await fetch(`http://localhost:8080/api/companies`, {
            method: 'PUT',
            body: JSON.stringify(requestedData),
            headers: {
                Authorization: session?.username || '',

                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to update company")
        }else {
            console.log("Company successfully updated!")

        }
    } catch (error) {
        console.error("Failed to update the company", error);
    }
}

export const addOffice = async (
    companyName: string,
    officeName: string,
    companyData: Company[] ,
    session: { username: string; roles: string[]; } | null | undefined
) => {

    const companyId = getCompanyId(companyName, companyData);
    const requestedData = {
        address: officeName,
        companyId: companyId
    }

    try {
        const response = await fetch(`http://localhost:8080/api/offices`, {
            method: 'POST',
            body: JSON.stringify(requestedData),
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to add office!")
        }else {
            console.log("New office successfully added!")

        }
    } catch (error) {
        console.error("Failed to add a new office to the company!", error);
    }
}
export const deleteOffice = async (
    item: any,
    session: { username: string; roles: string[]; } | null | undefined
)=>{
    //console.log(item);
    //console.log(session);
    console.log(item.id);

    try {
        const response = await fetch(`http://localhost:8080/api/office/${item.id}`, {
            method: 'DELETE',
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete office!")
        }else {
            console.log("Office successfully deleted!")

        }
    } catch (error) {
        console.error("Failed to deletd the chosen office!", error);
    }
}
export const deleteEmployee = async (
    item: any,
    session: { username: string; roles: string[]; } | null | undefined
)=>{
    //console.log(item);
    //console.log(session);
    console.log(item.id);

    try {
        const response = await fetch(`http://localhost:8080/api/office-employees/${item.id}`, {
            method: 'DELETE',
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete employee!")
        }else {
            console.log("Employee successfully deleted!")

        }
    } catch (error) {
        console.error("Failed to deleted the chosen employee!", error);
    }
}
export const promoteUser = async (
    item: any,
    session: { username: string; roles: string[]; } | null | undefined
) => {

    const requestedData = {
        userId: item.id,
        role: "EMPLOYEE",
    }
    try {
        const response = await fetch(`http://localhost:8080/api/users/add-role`, {
            method: 'PUT',
            body: JSON.stringify(requestedData),
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to promote user!")
        }else {
            console.log("User successfully promoted!")

        }
    } catch (error) {
        console.error("Failed to promote the chosen user!", error);
    }
}
export const demoteEmployee = async (
    item: any,
    session: { username: string; roles: string[]; } | null | undefined
) => {

    const requestedData = {
        userId: item.id,
        role: "USER",
    }
    try {
        const response = await fetch(`http://localhost:8080/api/users/remove-role`, {
            method: 'PUT',
            body: JSON.stringify(requestedData),
            headers: {
                Authorization: session?.username || '',
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to demote employee!")
        }else {
            console.log("Employee successfully demoted!")

        }
    } catch (error) {
        console.error("Failed to demote the chosen employee!", error);
    }
}