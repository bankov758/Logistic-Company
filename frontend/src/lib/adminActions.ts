"use server";
import {getSession, Session} from "@/lib/auth";
import {FormState} from "@/lib/actions";
import {selectorItem} from "@/components/UI/DataSelectorWrapper";

export const createCompany = async (
    session: Session | null,
    initialState: FormState,
    formData: FormData,
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });

        if (response.ok) {
            return {
                message: "You've successfully create a company! ",
                errors: ""
            }
        } else {
            return {
                message: '',
                errors: 'Failed to create company:'+ response.statusText }
        }

    } catch (error) {
        console.error('Error creating company:', error);
        return {
            message: '',
            errors: 'Error creating company! '
        };
    }
};

export const deleteCompany = async (
    companyName: string | undefined,
    companyData: selectorItem[] ,
    session: { username: string; roles: string[]; } | null | undefined
) => {

    const companyId = getCompanyId(companyName, companyData);
    try {
        const response = await fetch(`http://localhost:8080/api/companies/${companyId}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(response.ok){
            console.log("Company successfully deleted!")
            return {
                message: "Company was successfully deleted!",
                errors: ""
            }
        }else {
            console.log("Failed to delete company")
            return {
                message: '',
                errors: 'Failed to delete company '
            };

        }
    } catch (error) {
        console.error("Failed to delete the company!", error);
        return {
            message: '',
            errors: 'Failed to delete the company!'
        };
    }
}

export const getCompanyId = (companyName: string | undefined, companyData: selectorItem[]) => {
    const foundCompany = companyData.find(company => company.title === companyName);
    if (foundCompany) {
        return foundCompany.id;
    }

    return null;
}

export const editCompany = async (
    companyName: string | undefined,
    updatedCompanyName: string,
    companyData: selectorItem[] , session: { username: string; roles: string[]; } | null | undefined
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to update company")
            return {
                message: '',
                errors: 'Failed to update company'
            };
        }else {
            console.log("Company was successfully updated!")
            return {
                message: "Company was successfully updated! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to update the company", error);
        return {
            message: '',
            errors: 'Failed to update the company'
        };
    }
}

export const addOffice = async (
    companyName: string | undefined,
    officeName: string,
    companyData: selectorItem[] ,
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to add office!")
            return {
                message: '',
                errors: 'Failed to add office!'
            };
        }else {
            console.log("New office was successfully added!")
            return {
                message: "New office was successfully added! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to add a new office to the company!", error);
        return {
            message: '',
            errors: 'Failed to add a new office to the company!'
        };
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete office!")
            return {
                message: '',
                errors: 'Failed to delete office!'
            };
        }else {
            console.log("Office was successfully deleted!")
            return {
                message: "Office was successfully deleted! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to delete the chosen office!", error);
        return {
            message: '',
            errors: 'Failed to delete the chosen office!'
        };
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to delete employee!")
            return {
                message: '',
                errors: 'Failed to delete employee!'
            };
        }else {
            console.log("Employee was successfully deleted!")
            return {
                message: "Employee was successfully deleted! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to deleted the chosen employee!", error);
        return {
            message: '',
            errors: 'Failed to deleted the chosen employee!'
        };
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to promote user!")
            return {
                message: '',
                errors: 'Failed to promote user!'
            };
        }else {
            console.log("User was successfully promoted!")
            return {
                message: "User was successfully promoted! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to promote the chosen user!", error);
        return {
            message: '',
            errors: 'Failed to promote the chosen user!'
        };
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
                "Content-Type": "application/json",
                Accept: "*/*"
            },

        });
        if(!response.ok){
            console.log("Failed to demote employee!")
            return {
                message: '',
                errors: 'Failed to demote employee!!'
            };
        }else {
            console.log("Employee was successfully demoted!")
            return {
                message: "Employee was successfully demoted! ",
                errors: ""
            }
        }
    } catch (error) {
        console.error("Failed to demote the chosen employee!", error);
        return {
            message: '',
            errors: 'Failed to demote the chosen employee!'
        };
    }
}